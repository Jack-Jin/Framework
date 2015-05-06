package eceep.customer;

import java.sql.SQLException;
import java.util.List;

import eceep.customer.domain.CustomerContact;
import eceep.customer.domain.CustomerDetail;

public interface Customer {
	boolean initial(String jdbcDriver, String url, String userName, String password);
	
	void setPolicy_CustomersByUserID(int userID);
	
	int getPolicy_CustomersByUserID();
	
	// ** For customer generic *****************************************
	List<CustomerDetail> getCustomers() throws SQLException;
}
