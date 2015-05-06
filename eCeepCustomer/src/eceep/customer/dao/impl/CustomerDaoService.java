package eceep.customer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import eceep.customer.dao.CustomerDao;
import eceep.customer.domain.CustomerContact;
import eceep.customer.domain.CustomerDetail;
import eceep.mysql.JdbcUtils;

public class CustomerDaoService implements CustomerDao {
	@Override
	public boolean initial(String jdbcDriver, String url, String userName, String password) {
		JdbcUtils config = new JdbcUtils();
		config.setJdbcDriver(jdbcDriver);
		config.setUrl(url);
		config.setUserName(userName);
		config.setPassword(password);

		JdbcUtils.initial(config);

		return true;
	}

	@Override
	public List<CustomerDetail> getCustomers(int userID) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<CustomerDetail> customers = new ArrayList<CustomerDetail>();
		try {
			conn = JdbcUtils.getConnection();

			// Get customer list.
			// ,IsDeleted,DeletedByID,DeletedByName,DetetedTime
			String sql = "SELECT ID,CustomerName,Street,City,State,Country,PostalCode,PhoneNo,FaxNo,Notes,ParentID,AgentID";
			sql += ",CreatedByID,CreatedByName,CreatedTime,ModifiedByID,ModifiedByName,ModifiedTime";
			if (userID < 0) {
				sql += " FROM Customers WHERE IsDeleted=FALSE";
				ps = conn.prepareStatement(sql);
			} else {
				sql += " FROM Customers WHERE CreatedByID=? AND IsDeleted=FALSE";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, userID);
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				CustomerDetail cusDetail = JdbcUtils.ResultSet2Object(rs, CustomerDetail.class);

				customers.add(cusDetail);
			}

			// Get All contact list.
			List<CustomerContact> cusContacts = new ArrayList<CustomerContact>();
			if (customers.size() > 0) {
				// CreatedByID,CreatedByName,CreatedTime,IsDeleted,DeletedByID,DeletedByName,DetetedTime
				sql = "SELECT ID,CustomerID,CustomerName,ContactName,IsPrimaryContact,ContactTitle,DirectPhoneNo,DirectFaxNo,EmailAddress,Note";
				sql += ",CreatedByID,CreatedByName,CreatedTime";
				sql += " FROM CustomerContacts WHERE IsDeleted=FALSE";
				ps = conn.prepareStatement(sql);

				rs = ps.executeQuery();

				while (rs.next()) {
					CustomerContact cusContact = JdbcUtils.ResultSet2Object(rs, CustomerContact.class);

					cusContacts.add(cusContact);
				}
			}

			// Combine contact list into customer list.
			if (customers.size() > 0 && cusContacts.size() > 0) {
				for (CustomerDetail eachCustomerDetail : customers) {

					List<CustomerContact> tmpContacts = cusContacts.stream()
							.filter(A -> A.getCustomerID() == eachCustomerDetail.getId()).collect(Collectors.toList());

					for (CustomerContact eachCustomerContact : tmpContacts) {
						eachCustomerDetail.getCustomerContacts().add(eachCustomerContact);
					}

					eachCustomerDetail.setCustomerContactID(-1);
					if (eachCustomerDetail.getCustomerContacts().size() > 0) {
						eachCustomerDetail.setCustomerContactID(eachCustomerDetail.getCustomerPrimaryContact().getId());
					}
				}
			}

		} catch (InstantiationException | IllegalAccessException e) {
			customers = new ArrayList<CustomerDetail>();
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return customers;
	}

//	@Override
//	public CustomerDetail getCustomerDetail(int customerID) throws SQLException {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//
//		CustomerDetail cusDetail = null;
//		try {
//			conn = JdbcUtils.getConnection();
//
//			// ,IsDeleted,DeletedByID,DeletedByName,DetetedTime
//			String sql = "SELECT ID,CompanyName,Street,City,State,Country,PostalCode,PhoneNo,FaxNo,Notes,ParentID,AgentID";
//			sql += ",CreatedByID,CreatedByName,CreatedTime,ModifiedByID,ModifiedByName,ModifiedTime";
//			sql += " FROM Customers WHERE ID=? AND IsDeleted=FALSE";
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, customerID);
//
//			rs = ps.executeQuery();
//
//			if (rs.next())
//				cusDetail = JdbcUtils.ResultSet2Object(rs, CustomerDetail.class);
//		} catch (InstantiationException | IllegalAccessException e) {
//			cusDetail = null;
//		} finally {
//			JdbcUtils.free(rs, ps, conn);
//		}
//
//		return cusDetail;
//	}

//	@Override
//	public List<CustomerContact> getCustomerContacts(int customerID) throws SQLException {
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//
//		List<CustomerContact> cusContacts = new ArrayList<CustomerContact>();
//		try {
//			conn = JdbcUtils.getConnection();
//
//			// CreatedByID,CreatedByName,CreatedTime,IsDeleted,DeletedByID,DeletedByName,DetetedTime
//			String sql = "SELECT ID,CustomerID,ContactName,IsPrimaryContact,ContactTitle,DirectPhoneNo,DirectFaxNo,EmailAddress,Note ";
//			sql += "FROM CustomerContacts WHERE CustomerID=? AND IsDeleted=FALSE";
//			ps = conn.prepareStatement(sql);
//			ps.setInt(1, customerID);
//
//			rs = ps.executeQuery();
//
//			while (rs.next()) {
//				CustomerContact cusContact = JdbcUtils.ResultSet2Object(rs, CustomerContact.class);
//
//				cusContacts.add(cusContact);
//			}
//
//		} catch (InstantiationException | IllegalAccessException e) {
//			cusContacts = new ArrayList<CustomerContact>();
//		} finally {
//			JdbcUtils.free(rs, ps, conn);
//		}
//
//		return cusContacts;
//	}
}
