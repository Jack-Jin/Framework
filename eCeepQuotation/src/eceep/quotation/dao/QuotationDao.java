package eceep.quotation.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eceep.milestone.Milestone;
import eceep.milestone.Step;
import eceep.mysql.JdbcUtils;
import eceep.quotation.Product;
import eceep.quotation.domain.QuotationHeaderDetail;
import eceep.quotation.domain.QuotationItemDetail;

public class QuotationDao {
	public boolean initial(String jdbcDriver, String url, String userName, String password) {
		JdbcUtils config = new JdbcUtils();
		config.setJdbcDriver(jdbcDriver);
		config.setUrl(url);
		config.setUserName(userName);
		config.setPassword(password);

		JdbcUtils.initial(config);

		return true;
	}

	public String generateQuoteNumber(int length) throws SQLException {
		// GetQuotationNumber
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;

		String quotationNumber = "";
		try {
			conn = JdbcUtils.getConnection();

			cs = conn.prepareCall("{ CALL GetQuotationNumber() }");
			rs = cs.executeQuery();
			
			if(rs.next()) {
				quotationNumber = "" + rs.getInt("NewQuoteNumber");

				for (int i = 0; i < length - quotationNumber.length(); i++) {
					quotationNumber = "0" + quotationNumber;
				}
			}
		} finally {
			JdbcUtils.free(rs, cs, conn);
		}

		return quotationNumber;
	}

	public boolean saveQuotation(int byUserID, QuotationHeaderDetail quotationHeader, Milestone<Step> milestone,
			List<QuotationItemDetail> quotationItems, String quotationItemsCurrentID) throws SQLException, IOException {
		if (quotationHeader == null || milestone == null) {
			return false;
		}

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		// Quotation header serialize
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(stream);
		out.writeObject(quotationHeader);
		out.close();
		stream.close();
		byte[] binary_QuotationHeader = stream.toByteArray();

		// Milestone serialize
		byte[] binary_Milestone = milestone.serialize();

		try {
			conn = JdbcUtils.getConnection();

			// Update or Insert quotation header.
			String sql = "";
			if (quotationHeader.getId() > 0) {
				sql = "UPDATE Quotation SET";
				sql += " QuotationNo=?,QuotationProjectName=?,QuotationReference=?,QuotationNote=?,QuotationLocation=?";
				sql += ",UnitID=?,CurrencyID=?,QuotationBinary=?,MilestoneBinary=?,QuotationItemsCurrentID=?";
				sql += ",Cost=?,Price=?,Status=?,Type=?,SalesType=?";
				sql += ",ModifiedByID=?,ModifiedByName=(SELECT UserName FROM Users WHERE ID=?)";
				sql += " WHERE IsDeleted=FALSE AND ID=?";
			} else {
				sql = "INSERT INTO Quotation";
				sql += "(QuotationNo,QuotationProjectName,QuotationReference,QuotationNote,QuotationLocation";
				sql += ",UnitID,CurrencyID,QuotationBinary,MilestoneBinary,QuotationItemsCurrentID";
				sql += ",Cost,Price,Status,Type,SalesType,CreatedByID,CreatedByName,ModifiedByID,ModifiedByName)";
				sql += " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,(SELECT UserName FROM Users WHERE ID=?),?,(SELECT UserName FROM Users WHERE ID=?))";
			}
			ps = conn.prepareStatement(sql);
			ps.setString(1, quotationHeader.getQuotationNo());
			ps.setString(2, quotationHeader.getQuotationProjectName());
			ps.setString(3, quotationHeader.getQuotationReference());
			ps.setString(4, quotationHeader.getQuotationNote());
			ps.setString(5, quotationHeader.getQuotationLocation());
			ps.setInt(6, quotationHeader.getUnitID());
			ps.setInt(7, quotationHeader.getCurrencyID());
			ps.setBytes(8, binary_QuotationHeader);
			ps.setBytes(9, binary_Milestone);
			ps.setString(10, quotationItemsCurrentID);
			ps.setBigDecimal(11, quotationHeader.getCost());
			ps.setBigDecimal(12, quotationHeader.getPrice());
			ps.setInt(13, quotationHeader.getStatus());
			ps.setString(14, quotationHeader.getType());
			ps.setString(15, quotationHeader.getSalesType());
			if (quotationHeader.getId() > 0) {
				ps.setInt(16, byUserID);
				ps.setInt(17, byUserID);
				ps.setInt(18, quotationHeader.getId());
			} else {
				ps.setInt(16, byUserID);
				ps.setInt(17, byUserID);
				ps.setInt(18, byUserID);
				ps.setInt(19, byUserID);
			}

			ps.executeUpdate();

			if (quotationHeader.getId() <= 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next())
					quotationHeader.setId(rs.getInt(1));
			}

			ps.close();

			// Delete quotation items.
			sql = "DELETE FROM QuotationItem WHERE QuotationID=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, quotationHeader.getId());

			ps.executeUpdate();

			// Insert quotation items
			for (QuotationItemDetail item : quotationItems) {
				// Product serialize.
				Product product = item.getProduct();
				if (product != null)
					product.saveBeforeSerialized();

				stream = new ByteArrayOutputStream();
				out = new ObjectOutputStream(stream);
				out.writeObject(item);
				out.close();
				stream.close();
				byte[] binary_item = stream.toByteArray();

				sql = "INSERT INTO QuotationItem";
				sql += "(ID,QuotationID,Sequence,ItemName,ItemRevision";
				sql += ",UnitID,CurrencyID,ProductTypeID,IndustryTypeID,ProductApplicationTypeID";
				sql += ",Cost,Price,ItemBinaryType,ItemBinary";
				sql += ",CreatedByID,CreatedByName,ModifiedByID,ModifiedByName)";
				sql += " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?";
				sql += ",?,(SELECT UserName FROM Users WHERE ID=?),?,(SELECT UserName FROM Users WHERE ID=?))";

				ps = conn.prepareStatement(sql);
				ps.setString(1, item.getId());
				ps.setInt(2, quotationHeader.getId());
				ps.setInt(3, item.getSequence());
				ps.setString(4, item.getItemName());
				ps.setString(5, item.getItemRevision());
				ps.setInt(6, item.getUnitID());
				ps.setInt(7, item.getCurrencyID());
				ps.setInt(8, item.getProductType().getId());
				ps.setInt(9, item.getIndustryType().getId());
				ps.setInt(10, item.getProductApplicationType().getId());
				ps.setBigDecimal(11, item.getCost());
				ps.setBigDecimal(12, item.getPrice());
				ps.setString(13, item.getProduct().getClass().getTypeName());
				ps.setBytes(14, binary_item);
				ps.setInt(15, byUserID);
				ps.setInt(16, byUserID);
				ps.setInt(17, byUserID);
				ps.setInt(18, byUserID);

				ps.executeUpdate();
			}
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return true;
	}

	public Object[] restoreQuotation(int quotationID) throws SQLException, IOException, ClassNotFoundException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		ByteArrayInputStream stream = null;
		ObjectInputStream in = null;

		// 0: quotationHeader; 1: milestone; 2: List<QuotationItemDetail>; 3: quotationItemsCurrentID;
		Object[] result = new Object[4];		

		try {
			conn = JdbcUtils.getConnection();
			
			// Quotation Header
			String sql = "SELECT QuotationBinary,MilestoneBinary,QuotationItemsCurrentID FROM Quotation WHERE IsDeleted=FALSE AND ID=?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, quotationID);

			rs = ps.executeQuery();

			if (rs.next()) {
				byte[] binary_QuotationHeader = rs.getBytes("QuotationBinary");
				byte[] binary_Milestone = rs.getBytes("MilestoneBinary");
				String quotationItemsCurrentID = rs.getString("QuotationItemsCurrentID");

				// Quotation header deserialize
				stream = new ByteArrayInputStream(binary_QuotationHeader);
				in = new ObjectInputStream(stream);
				QuotationHeaderDetail header = (QuotationHeaderDetail) in.readObject();
				in.close();
				stream.close();

				header.setId(quotationID);
				result[0] = header;

				// Milestone deserialize
				stream = new ByteArrayInputStream(binary_Milestone);
				in = new ObjectInputStream(stream);
				Milestone<Step> milestone = (Milestone<Step>) in.readObject();
				in.close();
				stream.close();

				result[1] = milestone;

				result[3] = quotationItemsCurrentID;
			}

			rs.close();
			ps.close();

			// Quotation Items
			List<QuotationItemDetail> quotationItems = new ArrayList<QuotationItemDetail>();
			sql = "SELECT ItemBinaryType,ItemBinary FROM QuotationItem WHERE QuotationID=?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, quotationID);

			rs = ps.executeQuery();

			while (rs.next()) {
				byte[] binary_Item = rs.getBytes("ItemBinary");

				// Quotation item descrialize
				stream = new ByteArrayInputStream(binary_Item);
				in = new ObjectInputStream(stream);
				QuotationItemDetail quotationItem = (QuotationItemDetail) in.readObject();
				in.close();
				stream.close();

				quotationItem.getProduct().restoreAfterDeserialized();
				
				quotationItems.add(quotationItem);
			}

			result[2] = quotationItems;
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return result;
	}

	public boolean removeQuotation(int quotationID, int byUserID) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = JdbcUtils.getConnection();
			
			String sql = "UPDATE Quotation SET IsDeleted=TRUE,DeletedByID=?,DeletedByName=(SELECT UserName FROM Users WHERE ID=?),DetetedTime=NOW() WHERE ID=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, byUserID);
			ps.setInt(2, byUserID);
			ps.setInt(3, quotationID);
			
			ps.executeUpdate();
			
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
		
		return true;
	}

}
