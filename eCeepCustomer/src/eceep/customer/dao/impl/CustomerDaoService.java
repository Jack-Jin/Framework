package eceep.customer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import eceep.customer.dao.CustomerDao;
import eceep.customer.domain.CustomerActivity;
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
	public CustomerDetail getCustomer(int byCustomerID) throws SQLException, InstantiationException,
			IllegalAccessException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		CustomerDetail customerDetail = null;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "SELECT ID,CustomerName,Street,City,State,Country,PostalCode,PhoneNo,FaxNo,Notes,ParentID,AgentID";
			sql += ",CreatedByID,CreatedByName,CreatedTime,ModifiedByID,ModifiedByName,ModifiedTime";
			sql += " FROM Customers WHERE IsDeleted=FALSE AND ID=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, byCustomerID);

			rs = ps.executeQuery();

			if (rs.next()) {
				customerDetail = JdbcUtils.ResultSet2Object(rs, CustomerDetail.class);
			}
			rs.close();
			ps.close();

			// Get All contact list, All activity list.
			List<CustomerContact> cusContacts = new ArrayList<CustomerContact>();
			List<CustomerActivity> cusActivities = new ArrayList<CustomerActivity>();
			if (customerDetail != null) {
				// Contact List
				sql = "SELECT ID,CustomerID,CustomerName,ContactName,IsPrimaryContact,ContactTitle,DirectPhoneNo,DirectFaxNo,EmailAddress,Note";
				sql += ",CreatedByID,CreatedByName,CreatedTime";
				sql += " FROM CustomerContacts WHERE IsDeleted=FALSE AND CustomerID=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, byCustomerID);

				rs = ps.executeQuery();

				while (rs.next()) {
					CustomerContact cusContact = JdbcUtils.ResultSet2Object(rs, CustomerContact.class);

					cusContacts.add(cusContact);
				}
				rs.close();
				ps.close();
				customerDetail.setCustomerContacts(cusContacts);
				
				// Activity List
				sql = "SELECT ID,CustomerID,CustomerName,Activity,ActivityTypeID,ActivityType,Detail";
				sql += ",StartTime,EndTime,ClosedByID,ClosedByName,ClosedTime,CreatedByID,CreatedByName,CreatedTime";
				sql += " FROM CustomerActivities WHERE IsDeleted=FALSE";
				ps = conn.prepareStatement(sql);

				rs = ps.executeQuery();

				while (rs.next()) {
					CustomerActivity cusActivity = JdbcUtils.ResultSet2Object(rs, CustomerActivity.class);

					cusActivities.add(cusActivity);
				}				
				rs.close();
				ps.close();
				customerDetail.setCustomerActivities(cusActivities);
			}
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return customerDetail;
	}

	@Override
	public List<CustomerDetail> getCustomers(int userID, String byCustomerName) throws SQLException {
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
				sql += " FROM Customers WHERE IsDeleted=FALSE AND CustomerName LIKE '%" + byCustomerName + "%'";
				ps = conn.prepareStatement(sql);
			} else {
				sql += " FROM Customers WHERE IsDeleted=FALSE AND CreatedByID=? AND CustomerName LIKE '%"
						+ byCustomerName + "%'";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, userID);
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				CustomerDetail cusDetail = JdbcUtils.ResultSet2Object(rs, CustomerDetail.class);

				customers.add(cusDetail);
			}

			// Get All contact list, All activity list.
			List<CustomerContact> cusContacts = new ArrayList<CustomerContact>();
			List<CustomerActivity> cusActivities = new ArrayList<CustomerActivity>();
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

				// SELECT ID,CustomerID,CustomerName,Activity,ActivityTypeID,ActivityType,Detail
				// ,StartTime,EndTime,ClosedByID,ClosedByName,ClosedTime,CreatedByID,CreatedByName,CreatedTime
				// FROM CustomerActivities;
				sql = "SELECT ID,CustomerID,CustomerName,Activity,ActivityTypeID,ActivityType,Detail";
				sql += ",StartTime,EndTime,ClosedByID,ClosedByName,ClosedTime,CreatedByID,CreatedByName,CreatedTime";
				sql += " FROM CustomerActivities WHERE IsDeleted=FALSE";
				ps = conn.prepareStatement(sql);

				rs = ps.executeQuery();

				while (rs.next()) {
					CustomerActivity cusActivity = JdbcUtils.ResultSet2Object(rs, CustomerActivity.class);

					cusActivities.add(cusActivity);
				}
			}

			// Combine contact list into customer list.
			if (customers.size() > 0 && cusContacts.size() > 0) {
				for (CustomerDetail eachCustomerDetail : customers) {
					// Contacts
					List<CustomerContact> tmpContacts = cusContacts.stream()
							.filter(A -> A.getCustomerID() == eachCustomerDetail.getId()).collect(Collectors.toList());

					for (CustomerContact eachCustomerContact : tmpContacts) {
						eachCustomerDetail.getCustomerContacts().add(eachCustomerContact);
					}

					eachCustomerDetail.setCustomerContactID(-1);
					if (eachCustomerDetail.getCustomerContacts().size() > 0
							&& eachCustomerDetail.getCustomerPrimaryContact() != null) {
						eachCustomerDetail.setCustomerContactID(eachCustomerDetail.getCustomerPrimaryContact().getId());
					}

					// Activities
					List<CustomerActivity> tmpActivities = cusActivities.stream()
							.filter(A -> A.getCustomerID() == eachCustomerDetail.getId()).collect(Collectors.toList());

					for (CustomerActivity eachCustomerActivity : tmpActivities) {
						eachCustomerDetail.getCustomerActivities().add(eachCustomerActivity);
					}

					eachCustomerDetail.setCustomerActivityID(-1);
				}
			}

		} catch (InstantiationException | IllegalAccessException e) {
			customers = new ArrayList<CustomerDetail>();
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return customers;
	}

	@Override
	public int newCustomer(int byUserID) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int customerID = -1;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "INSERT INTO Customers(CustomerName,CreatedByID,CreatedByName,CreatedTime,ModifiedByID,ModifiedByName,ModifiedTime)";
			sql += " SELECT 'New Customer' AS 'CustomerName'";
			sql += ", ? AS 'CreatedByID', UserName AS 'CreatedByName', NOW() AS 'CreatedTime'";
			sql += ", ? AS 'ModifiedByID', UserName AS 'ModifiedByName', NOW() AS 'ModifiedTime'";
			sql += " FROM Users WHERE ID=?";
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, byUserID);
			ps.setInt(2, byUserID);
			ps.setInt(3, byUserID);

			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next())
				customerID = rs.getInt(1);

		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return customerID;
	}

	@Override
	public void removeCustomer(int customerID, int byUserID) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = JdbcUtils.getConnection();

			// Customers table
			String sql = "UPDATE Customers SET IsDeleted=TRUE,DetetedTime=NOW(),DeletedByID=?";
			sql += ",DeletedByName=(SELECT UserName FROM Users WHERE ID=?) WHERE ID=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, byUserID);
			ps.setInt(2, byUserID);
			ps.setInt(3, customerID);

			ps.executeUpdate();

			// Customer contacts table
			sql = " UPDATE CustomerContacts SET IsDeleted=TRUE,DetetedTime=NOW(),DeletedByID=?";
			sql += ",DeletedByName=(SELECT UserName FROM Users WHERE ID=?) WHERE CustomerID=?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, byUserID);
			ps.setInt(2, byUserID);
			ps.setInt(3, customerID);

			ps.executeUpdate();

			// Customer activities
			sql = " UPDATE CustomerActivities SET IsDeleted=TRUE,DetetedTime=NOW(),DeletedByID=?";
			sql += ",DeletedByName=(SELECT UserName FROM Users WHERE ID=?) WHERE CustomerID=?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, byUserID);
			ps.setInt(2, byUserID);
			ps.setInt(3, customerID);

			ps.executeUpdate();

		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

	@Override
	public boolean updateCustomer(CustomerDetail customerDetail, int byUserID) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		boolean result = false;
		try {
			conn = JdbcUtils.getConnection();

			// Customers table
			String sql = "UPDATE Customers SET CustomerName=?, Street=?, City=?, State=?, Country=?, PostalCode=?, PhoneNo=?, FaxNo=?, Notes=?";
			sql += ",ModifiedByID=?, ModifiedByName=(SELECT UserName FROM Users WHERE ID=?), ModifiedTime=NOW()";
			sql += " WHERE ID=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, customerDetail.getCustomerName());
			ps.setString(2, customerDetail.getStreet());
			ps.setString(3, customerDetail.getCity());
			ps.setString(4, customerDetail.getState());
			ps.setString(5, customerDetail.getCountry());
			ps.setString(6, customerDetail.getPostalCode());
			ps.setString(7, customerDetail.getPhoneNo());
			ps.setString(8, customerDetail.getFaxNo());
			ps.setString(9, customerDetail.getNotes());
			ps.setInt(10, byUserID);
			ps.setInt(11, byUserID);
			ps.setInt(12, customerDetail.getId());

			int updateCount = ps.executeUpdate();

			if (updateCount > 0)
				result = true;
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return result;
	}

	@Override
	public boolean updateContact(CustomerContact contact) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		boolean result = false;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "UPDATE CustomerContacts SET";
			sql += " ContactName=?,IsPrimaryContact=?,ContactTitle=?,DirectPhoneNo=?,DirectFaxNo=?,EmailAddress=?,Note=?";
			sql += " WHERE ID=? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, contact.getContactName());
			ps.setBoolean(2, contact.isIsPrimaryContact());
			ps.setString(3, contact.getContactTitle());
			ps.setString(4, contact.getDirectPhoneNo());
			ps.setString(5, contact.getDirectFaxNo());
			ps.setString(6, contact.getEmailAddress());
			ps.setString(7, contact.getNote());
			ps.setInt(8, contact.getId());

			int updateCount = ps.executeUpdate();
			if (updateCount > 0)
				result = true;

		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return result;
	}

	@Override
	public int newContact(int customerID, String customerName, int byUserID) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int contactID = -1;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "INSERT INTO CustomerContacts(ContactName,CustomerID,CustomerName,CreatedByID,CreatedByName)";
			sql += " SELECT 'New Contact' AS 'ContactName',? ,? ,ID AS 'CreatedByID', UserName AS 'CreatedByName' FROM Users WHERE ID=? ";
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, customerID);
			ps.setString(2, customerName);
			ps.setInt(3, byUserID);

			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next())
				contactID = rs.getInt(1);

		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return contactID;
	}

	@Override
	public void removeContact(int contactID, int byUserID) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "UPDATE CustomerContacts SET IsDeleted=TRUE, DeletedByID=?, DeletedByName=(SELECT UserName FROM Users WHERE ID=?) ";
			sql += "WHERE ID=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, byUserID);
			ps.setInt(2, byUserID);
			ps.setInt(3, contactID);

			ps.executeUpdate();

		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

	@Override
	public boolean updateActivity(CustomerActivity activity) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		boolean result = false;
		try {
			conn = JdbcUtils.getConnection();

			// SELECT ID,CustomerID,CustomerName,Activity,ActivityTypeID,ActivityType,Detail
			// ,StartTime,EndTime,ClosedByID,ClosedByName,ClosedTime,CreatedByID,CreatedByName,CreatedTime
			// FROM CustomerActivities;
			String sql = "UPDATE CustomerActivities SET Activity=?, ActivityTypeID=?, ActivityType=(SELECT ActivityType FROM L_ActivityType WHERE ID=?), Detail=?";
			sql += " WHERE ID=? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, activity.getActivity());
			ps.setInt(2, activity.getActivityTypeID());
			ps.setInt(3, activity.getActivityTypeID());
			ps.setString(4, activity.getDetail());
			ps.setInt(5, activity.getId());

			int updateCount = ps.executeUpdate();
			if (updateCount > 0)
				result = true;

		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return result;
	}

	@Override
	public int newActivity(int customerID, String customerName, int byUserID) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int contactID = -1;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "INSERT INTO CustomerActivities(CustomerID,CustomerName,Activity,ActivityTypeID,ActivityType,CreatedByID,CreatedByName)";
			sql += " SELECT ?, ? ,'New Activity' AS 'Activity' ";
			sql += ",1 AS 'ActivityTypeID' ,'Visit Customer' AS 'ActivityType' ";
			sql += ",ID AS 'CreatedByID' , UserName AS 'CreatedByName' FROM Users WHERE ID=? ";
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, customerID);
			ps.setString(2, customerName);
			ps.setInt(3, byUserID);

			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next())
				contactID = rs.getInt(1);

		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return contactID;
	}

	@Override
	public void removeActivity(int activityID, int byUserID) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "UPDATE CustomerActivities SET IsDeleted=TRUE, DeletedByID=?, DeletedByName=(SELECT UserName FROM Users WHERE ID=?) ";
			sql += "WHERE ID=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, byUserID);
			ps.setInt(2, byUserID);
			ps.setInt(3, activityID);

			ps.executeUpdate();

		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
	}

	@Override
	public Map<Integer, String> getActivityTypeList() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Map<Integer, String> activityTypeList = new HashMap<Integer, String>();
		try {
			conn = JdbcUtils.getConnection();

			String sql = "SELECT ID,ActivityType FROM L_ActivityType";
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				activityTypeList.put(rs.getInt("ID"), rs.getString("ActivityType"));
			}

		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return activityTypeList;
	}

	// @Override
	// public CustomerDetail getCustomerDetail(int customerID) throws
	// SQLException {
	// Connection conn = null;
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	//
	// CustomerDetail cusDetail = null;
	// try {
	// conn = JdbcUtils.getConnection();
	//
	// // ,IsDeleted,DeletedByID,DeletedByName,DetetedTime
	// String sql =
	// "SELECT ID,CompanyName,Street,City,State,Country,PostalCode,PhoneNo,FaxNo,Notes,ParentID,AgentID";
	// sql +=
	// ",CreatedByID,CreatedByName,CreatedTime,ModifiedByID,ModifiedByName,ModifiedTime";
	// sql += " FROM Customers WHERE ID=? AND IsDeleted=FALSE";
	// ps = conn.prepareStatement(sql);
	// ps.setInt(1, customerID);
	//
	// rs = ps.executeQuery();
	//
	// if (rs.next())
	// cusDetail = JdbcUtils.ResultSet2Object(rs, CustomerDetail.class);
	// } catch (InstantiationException | IllegalAccessException e) {
	// cusDetail = null;
	// } finally {
	// JdbcUtils.free(rs, ps, conn);
	// }
	//
	// return cusDetail;
	// }

	// @Override
	// public List<CustomerContact> getCustomerContacts(int customerID) throws
	// SQLException {
	// Connection conn = null;
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	//
	// List<CustomerContact> cusContacts = new ArrayList<CustomerContact>();
	// try {
	// conn = JdbcUtils.getConnection();
	//
	// //
	// CreatedByID,CreatedByName,CreatedTime,IsDeleted,DeletedByID,DeletedByName,DetetedTime
	// String sql =
	// "SELECT ID,CustomerID,ContactName,IsPrimaryContact,ContactTitle,DirectPhoneNo,DirectFaxNo,EmailAddress,Note ";
	// sql += "FROM CustomerContacts WHERE CustomerID=? AND IsDeleted=FALSE";
	// ps = conn.prepareStatement(sql);
	// ps.setInt(1, customerID);
	//
	// rs = ps.executeQuery();
	//
	// while (rs.next()) {
	// CustomerContact cusContact = JdbcUtils.ResultSet2Object(rs,
	// CustomerContact.class);
	//
	// cusContacts.add(cusContact);
	// }
	//
	// } catch (InstantiationException | IllegalAccessException e) {
	// cusContacts = new ArrayList<CustomerContact>();
	// } finally {
	// JdbcUtils.free(rs, ps, conn);
	// }
	//
	// return cusContacts;
	// }
}
