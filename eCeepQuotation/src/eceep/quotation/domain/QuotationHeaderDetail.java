package eceep.quotation.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuotationHeaderDetail {
	private int id;
	private String no;
	private String projectName;
	private String reference;
	private String note;
	private String location;

	// Customer Info
	private int customerID;
	private String customerName;
	private String customerAddress;
	private String customerAddress1;
	private String customerCity;
	private String customerState;
	private String customerCountry;
	private String customerZip;
	private String customerPhone;
	private String customerFax;

	// Contact Info
	private int contactID;
	private String contactName;
	private String contactTitle;
	private String contactTel;
	private String contactFax;
	private String contactEMail;
	private String contactWWW;
	private String contactFacebook;
	private String contactTwitter;

	private int unitID;
	private int currencyID;

	private BigDecimal cost;
	private BigDecimal price;

	private String type;
	private String salesType;

	private int agentID;
	private int siteID;
	private int siteContactID;

	private int createdByID;
	private String createdByName;
	private Date createdTime;

	private int modifiedByID;
	private String modifiedByName;
	private Date modifiedTime;

	//Status & Log
	private int status;
	private List<QuotationLog> logs; //time ~ status ~ user ID ~ user name

	public QuotationHeaderDetail() {
		this.logs = new ArrayList<QuotationLog>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerAddress1() {
		return customerAddress1;
	}

	public void setCustomerAddress1(String customerAddress1) {
		this.customerAddress1 = customerAddress1;
	}

	public String getCustomerCity() {
		return customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public String getCustomerState() {
		return customerState;
	}

	public void setCustomerState(String customerState) {
		this.customerState = customerState;
	}

	public String getCustomerCountry() {
		return customerCountry;
	}

	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
	}

	public String getCustomerZip() {
		return customerZip;
	}

	public void setCustomerZip(String customerZip) {
		this.customerZip = customerZip;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerFax() {
		return customerFax;
	}

	public void setCustomerFax(String customerFax) {
		this.customerFax = customerFax;
	}

	public int getContactID() {
		return contactID;
	}

	public void setContactID(int contactID) {
		this.contactID = contactID;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTitle() {
		return contactTitle;
	}

	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getContactFax() {
		return contactFax;
	}

	public void setContactFax(String contactFax) {
		this.contactFax = contactFax;
	}

	public String getContactEMail() {
		return contactEMail;
	}

	public void setContactEMail(String contactEMail) {
		this.contactEMail = contactEMail;
	}

	public String getContactWWW() {
		return contactWWW;
	}

	public void setContactWWW(String contactWWW) {
		this.contactWWW = contactWWW;
	}

	public String getContactFacebook() {
		return contactFacebook;
	}

	public void setContactFacebook(String contactFacebook) {
		this.contactFacebook = contactFacebook;
	}

	public String getContactTwitter() {
		return contactTwitter;
	}

	public void setContactTwitter(String contactTwitter) {
		this.contactTwitter = contactTwitter;
	}

	public int getUnitID() {
		return unitID;
	}

	public void setUnitID(int unitID) {
		this.unitID = unitID;
	}

	public int getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(int currencyID) {
		this.currencyID = currencyID;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSalesType() {
		return salesType;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	public int getAgentID() {
		return agentID;
	}

	public void setAgentID(int agentID) {
		this.agentID = agentID;
	}

	public int getSiteID() {
		return siteID;
	}

	public void setSiteID(int siteID) {
		this.siteID = siteID;
	}

	public int getSiteContactID() {
		return siteContactID;
	}

	public void setSiteContactID(int siteContactID) {
		this.siteContactID = siteContactID;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<QuotationLog> getLogs() {
		return logs;
	}
}
