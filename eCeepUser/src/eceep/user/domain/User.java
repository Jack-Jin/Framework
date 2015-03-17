package eceep.user.domain;

import java.util.Date;

public class User {
	private int id;
	private String userName;
	private String firstName;
	private String lastName;
	private String title;
	private String address;
	private String address1;
	private String city;
	private String state;
	private String country;
	private String postalCode;
	private String telephone;
	private String fax;
	private String email;
	private String www;
	private String note;
	
	private UserCompany company;			// Company
	private ApplicationPolicy policy;		// Policy
	
	private int defaultCurrencyID;
	private int defaultUnitID;
	private int defaultLanguageID;
	
	private boolean isAdministrator;
	private int createBy;
	private Date createDate;
	
	private Date loginTime;
	private Date logoutTime;
}
