package eceep.customer.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerDetail {
	private int id;
	private String customerName;
	private String street;
	private String city;
	private String state;
	private String country;
	private String postalCode;
	private String phoneNo;
	private String faxNo;
	private String notes;

	private int createdByID;
	private String createdByName;
	private Date createdTime;

	private int modifiedByID;
	private String modifiedByName;
	private Date modifiedTime;

	private List<CustomerContact> customerContacts;
	private int customerContactID;

	private List<CustomerActivity> customerActivities;
	private int customerActivityID;

	private String parentID;
	private String agentID;

	public CustomerDetail() {
		customerContacts = new ArrayList<CustomerContact>();
		customerContactID = -1;

		customerActivities = new ArrayList<CustomerActivity>();
		customerActivityID = -1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getAgentID() {
		return agentID;
	}

	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}

	public int getCreatedByID() {
		return createdByID;
	}

	public void setCreatedByID(int createdByID) {
		this.createdByID = createdByID;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public int getModifiedByID() {
		return modifiedByID;
	}

	public void setModifiedByID(int modifiedByID) {
		this.modifiedByID = modifiedByID;
	}

	public String getModifiedByName() {
		return modifiedByName;
	}

	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public List<CustomerContact> getCustomerContacts() {
		return this.customerContacts;
	}

	public int getCustomerContactID() {
		if (this.customerContactID < 0 && this.customerContacts != null && this.customerContacts.size() > 0)
			this.customerContactID = this.customerContacts.get(0).getId();
		
		return customerContactID;
	}

	public void setCustomerContactID(int customerContactID) {
		this.customerContactID = customerContactID;
	}

	public CustomerContact getCustomerContact() {
		if (this.getCustomerContactID() < 0 || this.customerContacts == null
				|| (this.customerContacts != null && this.customerContacts.size() < 1))
			return null;

		CustomerContact contact = this.customerContacts.stream().filter(A -> A.getId() == this.customerContactID)
				.findAny().get();

		// List<CustomerContact> contacts = this.customerContacts.stream()
		// .filter(A -> A.getId() ==
		// this.customerContactID).collect(Collectors.toList());
		//
		// CustomerContact contact = null;
		// if (contacts != null && contacts.size() > 0) {
		// contact = contacts.get(0);
		// }

		return contact;
	}

	public CustomerContact getCustomerPrimaryContact() {
		if (this.customerContacts == null || (this.customerContacts != null && this.customerContacts.size() < 1))
			return null;

		List<CustomerContact> contact = this.customerContacts.stream().filter(A -> A.isIsPrimaryContact())
				.collect(Collectors.toList());
		if (contact != null && contact.size() > 0) {
			return contact.get(0);
		}

		return this.customerContacts.get(0);
	}

	public List<CustomerActivity> getCustomerActivities() {
		return customerActivities;
	}

	public int getCustomerActivityID() {
		if (customerActivityID < 0 && this.customerActivities != null && customerActivities.size() > 0)
			customerActivityID = customerActivities.get(0).getId();

		return customerActivityID;
	}

	public void setCustomerActivityID(int customerActivityID) {
		this.customerActivityID = customerActivityID;
	}

	public CustomerActivity getCustomerActivity() {
		if (this.getCustomerActivityID() < 0 || this.customerActivities == null
				|| (this.customerActivities != null && this.customerActivities.size() < 1))
			return null;

		CustomerActivity activity = this.customerActivities.stream()
				.filter(A -> A.getId() == this.getCustomerActivityID()).findAny().get();

		return activity;
	}
}
