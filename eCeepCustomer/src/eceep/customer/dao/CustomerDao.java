package eceep.customer.dao;

import java.sql.SQLException;
import java.util.List;

import eceep.customer.domain.CustomerContact;
import eceep.customer.domain.CustomerDetail;

public interface CustomerDao {
	boolean initial(String jdbcDriver, String url, String userName, String password);
	
	List<CustomerDetail> getCustomers(int userID) throws SQLException;
	
	int newCustomer(int byUserID) throws SQLException;
	
	void removeCustomer(int customerID, int byUserID) throws SQLException;
	
	boolean updateCustomer(CustomerDetail customerDetail, int byUserID) throws SQLException;
	
	//CustomerDetail getCustomerDetail(int customerID) throws SQLException;
	
	//List<CustomerContact> getCustomerContacts(int customerID) throws SQLException;
}
