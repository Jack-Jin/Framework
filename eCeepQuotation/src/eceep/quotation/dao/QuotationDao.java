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

			String sql = "SELECT COUNT(*) AS 'FoundCount' FROM Quotation WHERE ID=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, quotationHeader.getId());
			rs = ps.executeQuery();
			boolean quotationFound = rs.getInt("FoundCount") > 0;

			if (quotationFound) {
				sql = "UPDATE Quotation SET";
				sql += " QuotationNo=?,QuotationProjectName=?,QuotationReference=?,QuotationNote=?,QuotationLocation=?";
				sql += ",UnitID=?,CurrencyID=?,QuotationBinary=?,MilestoneBinary=?,QuotationItemsCurrentD=?";
				sql += ",Cost=?,Price=?,Status=?,Type=?,SalesType=?";
			} else {
				sql = "INSERT INTO Quotation";
				sql += "(QuotationNo,QuotationProjectName,QuotationReference,QuotationNote,QuotationLocation";
				sql += ",UnitID,CurrencyID,QuotationBinary,MilestoneBinary,QuotationItemsCurrentD";
				sql += ",Cost,Price,Status,Type,SalesType)";
				sql += " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
