package eceep.customer.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import eceep.customer.domain.CustomerActivity;
import eceep.customer.domain.CustomerContact;
import eceep.customer.domain.CustomerDetail;

public interface CustomerDao {
	boolean initial(String jdbcDriver, String url, String userName, String password);
	
	// Customer
	CustomerDetail getCustomer(int byCustomerID) throws SQLException, InstantiationException, IllegalAccessException;
	
	List<CustomerDetail> getCustomers(int userID, String byCustomerName) throws SQLException;
	
	int newCustomer(int byUserID) throws SQLException;
	
	void removeCustomer(int customerID, int byUserID) throws SQLException;
	
	boolean updateCustomer(CustomerDetail customerDetail, int byUserID) throws SQLException;

	// Contact
	boolean updateContact(CustomerContact contact) throws SQLException;

	int newContact(int customerID, String customerName, int byUserID) throws SQLException;
	
	void removeContact(int contactID, int byUserID) throws SQLException;

	// Activity
	boolean updateActivity(CustomerActivity activity) throws SQLException;

	int newActivity(int customerID, String customerName, int byUserID) throws SQLException;
	
	void removeActivity(int activityID, int byUserID) throws SQLException;

	Map<Integer,String> getActivityTypeList() throws SQLException;
	
	//CustomerDetail getCustomerDetail(int customerID) throws SQLException;
	
	//List<CustomerContact> getCustomerContacts(int customerID) throws SQLException;
}
