package eceep.customer.domain;

import java.util.Date;

public class CustomerContact {
	private int id;
	private int customerID;
	private String customerName;
	private String contactName;
	private boolean isPrimaryContact;
	private String contactTitle;
	private String directPhoneNo;
	private String directFaxNo;
	private String emailAddress;
	private String note;

	private int createdByID;
	private String createdByName;
	private Date createdTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public boolean isIsPrimaryContact() {
		return isPrimaryContact;
	}

	public void setIsPrimaryContact(boolean isPrimaryContact) {
		this.isPrimaryContact = isPrimaryContact;
	}

	public String getContactTitle() {
		return contactTitle;
	}

	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}

	public String getDirectPhoneNo() {
		return directPhoneNo;
	}

	public void setDirectPhoneNo(String directPhoneNo) {
		this.directPhoneNo = directPhoneNo;
	}

	public String getDirectFaxNo() {
		return directFaxNo;
	}

	public void setDirectFaxNo(String directFaxNo) {
		this.directFaxNo = directFaxNo;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

}
