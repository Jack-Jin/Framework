package eceep.customer.impl;

import eceep.customer.Customer;
import eceep.customer.CustomerFactory;

public class CustomerFactoryImpl implements CustomerFactory {
	private static CustomerFactory customerFactory;

	public static Customer getInstance() {
		if (customerFactory == null)
			customerFactory = new CustomerFactoryImpl();

		return customerFactory.getCustomer();
	}

	@Override
	public Customer getCustomer() {
		return new CustomerService();
	}
}
