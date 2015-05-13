package eceep.customer.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import eceep.customer.Customer;
import eceep.customer.dao.CustomerDao;
import eceep.customer.dao.impl.CustomerDaoFactoryMySql;
import eceep.customer.domain.CustomerActivity;
import eceep.customer.domain.CustomerContact;
import eceep.customer.domain.CustomerDetail;

public class CustomerService implements Customer {
	private CustomerDao customerDao;

	private int policy_CustomersByUserID;

	public CustomerService() {
		this.policy_CustomersByUserID = -1; // if -1, no policy.

		this.customerDao = CustomerDaoFactoryMySql.getInstance();
	}

	@Override
	public boolean initial(String jdbcDriver, String url, String userName, String password) {
		boolean result = this.customerDao.initial(jdbcDriver, url, userName, password);

		return result;
	}

	@Override
	public void setPolicy_CustomersByUserID(int userID) {
		this.policy_CustomersByUserID = userID;
	}

	@Override
	public int getPolicy_CustomersByUserID() {
		return this.policy_CustomersByUserID;
	}

	@Override
	public List<CustomerDetail> getCustomers(String byCustomerName) throws SQLException {
		return this.customerDao.getCustomers(this.policy_CustomersByUserID, byCustomerName);
	}

	@Override
	public int newCustomer(int byUserID) throws SQLException {
		return this.customerDao.newCustomer(byUserID);
	}

	@Override
	public void removeCustomer(int customerID, int byUserID) throws SQLException {
		this.customerDao.removeCustomer(customerID, byUserID);
	}

	@Override
	public boolean updateCustomer(CustomerDetail customerDetail, int byUserID) throws SQLException {
		return this.customerDao.updateCustomer(customerDetail, byUserID);
	}

	@Override
	public boolean updateContact(CustomerContact contact) throws SQLException {
		return this.customerDao.updateContact(contact);
	}

	@Override
	public int newContact(int customerID, String customerName, int byUserID) throws SQLException {
		return this.customerDao.newContact(customerID, customerName, byUserID);
	}

	@Override
	public void removeContact(int contactID, int byUserID) throws SQLException {
		this.customerDao.removeContact(contactID, byUserID);
	}

	@Override
	public boolean updateActivity(CustomerActivity activity) throws SQLException {
		return this.customerDao.updateActivity(activity);
	}

	@Override
	public int newActivity(int customerID, String customerName, int byUserID) throws SQLException {
		return this.customerDao.newActivity(customerID, customerName, byUserID);
	}

	@Override
	public void removeActivity(int activityID, int byUserID) throws SQLException {
		this.customerDao.removeActivity(activityID, byUserID);
	}
	
	@Override
	public Map<Integer,String> getActivityTypeList() throws SQLException {
		return this.customerDao.getActivityTypeList();
	}
}
