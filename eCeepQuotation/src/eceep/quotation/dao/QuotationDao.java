package eceep.quotation.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		//GetQuotationNumber
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;

		String quotationNumber = "";
		try {
			conn = JdbcUtils.getConnection();

			cs = conn.prepareCall("GetQuotationNumber");
			boolean hadResult = cs.execute();

			if (hadResult) {
				rs = cs.getResultSet();

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

	public boolean saveQuotation(QuotationHeaderDetail quotationHeader, Milestone<Step> milestone,
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

		// Quotation items serialize
		for (QuotationItemDetail item : quotationItems) {
			Product product = item.getProduct();
			if (product == null)
				continue;

			product.saveBeforeSerialized();

			stream = new ByteArrayOutputStream();
			out = new ObjectOutputStream(stream);
			out.writeObject(product);
			out.close();
			stream.close();
			byte[] binary_product = stream.toByteArray();
		}

		try {
			conn = JdbcUtils.getConnection();

			//String sql = "SELECT COUNT(*) AS 'FoundCount' FROM Quotation WHERE ID=?";
			//ps = conn.prepareStatement(sql);
			//ps.setInt(1, quotationHeader.getId());
			//rs = ps.executeQuery();
			//boolean quotationFound = rs.getInt("FoundCount") > 0;

			// Update or Insert quotation header.
			String sql = "";
			if (quotationHeader.getId()>0) {
				sql = "UPDATE Quotation SET";
				sql += " QuotationNo=?,QuotationProjectName=?,QuotationReference=?,QuotationNote=?,QuotationLocation=?";
				sql += ",UnitID=?,CurrencyID=?,QuotationBinary=?,MilestoneBinary=?,QuotationItemsCurrentID=?";
				sql += ",Cost=?,Price=?,Status=?,Type=?,SalesType=?";
				sql += " WHERE ID=?";
			} else {
				sql = "INSERT INTO Quotation";
				sql += "(QuotationNo,QuotationProjectName,QuotationReference,QuotationNote,QuotationLocation";
				sql += ",UnitID,CurrencyID,QuotationBinary,MilestoneBinary,QuotationItemsCurrentID";
				sql += ",Cost,Price,Status,Type,SalesType)";
				sql += " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
			if (quotationHeader.getId()>0) {
				ps.setInt(16, quotationHeader.getId());
			}

			ps.executeUpdate();

			if (quotationHeader.getId()<=0) {
				rs = ps.getGeneratedKeys();
				if (rs.next())
					quotationHeader.setId(rs.getInt(1));
			}

		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return false;
	}

	public QuotationHeaderDetail getQuotationHeader(int quotationID) {
		// TODO Auto-generated method stub
		return null;
	}

	public Milestone<Step> getMilestone(int quotationID) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<QuotationItemDetail> getQuotationItems(int quotationID) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getQuotationItemsCurrentID(int quotationID) {
		// TODO Auto-generated method stub
		return null;
	}

}
