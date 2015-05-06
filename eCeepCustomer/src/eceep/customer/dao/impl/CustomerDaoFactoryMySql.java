package eceep.customer.dao.impl;

import eceep.customer.dao.CustomerDao;
import eceep.customer.dao.CustomerDaoFactory;


public class CustomerDaoFactoryMySql implements CustomerDaoFactory {
	private static CustomerDaoFactoryMySql customerDaoFactory;
	
	public static CustomerDao getInstance(){
		if(customerDaoFactory==null){
			customerDaoFactory = new CustomerDaoFactoryMySql();
		}
		
		return customerDaoFactory.getCustomerDao();
	}
	@Override
	public CustomerDao getCustomerDao() {
		return new CustomerDaoService();
	}

}
