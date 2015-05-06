package eceep.customer.test;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eceep.customer.Customer;
import eceep.customer.domain.CustomerContact;
import eceep.customer.domain.CustomerDetail;
import eceep.customer.impl.CustomerFactoryImpl;

public class CustomerTest {
	private Customer customer;
	
	@Before
	public void prepare(){
		customer = CustomerFactoryImpl.getInstance();
		
		boolean result = customer.initial("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/eCeepFramework", "root",
				"123");
		Assert.assertTrue(result);
		
		customer.setPolicy_CustomersByUserID(-1);
	}
	
	@Test
	public void getCustomers() throws SQLException{
		List<CustomerDetail> customers = customer.getCustomers();
		
		for(CustomerDetail eachCustomerDetail : customers){
			System.out.println("======================================================================================================");
			System.out.println("ID: " + eachCustomerDetail.getId() +
					           ";\tCustomer Name:" + eachCustomerDetail.getCustomerName() + 
					           ";\tPrimary contact ID: " + eachCustomerDetail.getCustomerPrimaryContact().getId() + 
					           ";\tPrimary contact: " + eachCustomerDetail.getCustomerPrimaryContact().getContactName());

			System.out.println("----------------------------------------------------------------------------------------------------");
		
			List<CustomerContact> customerContacts = eachCustomerDetail.getCustomerContacts();
			for(CustomerContact eachCustomerContact :  customerContacts ){
				System.out.println("Contact ID: " + eachCustomerContact.getId() + 
						           ";\tContact Name" + eachCustomerContact.getContactName());
			}
		}
		System.out.println("======================================================================================================");
	}
}
