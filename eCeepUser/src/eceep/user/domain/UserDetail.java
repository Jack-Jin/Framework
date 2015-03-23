package eceep.user.domain;

import java.util.Date;

public class UserDetail {
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

	private int defaultCurrencyID;
	private int defaultUnitID;
	private int defaultLanguageID;

	private boolean isAdministrator;
	private int createBy;
	private Date createDate;

	private Date loginTime;
	private Date logoutTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
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

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWww() {
		return www;
	}

	public void setWww(String www) {
		this.www = www;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getDefaultCurrencyID() {
		return defaultCurrencyID;
	}

	public void setDefaultCurrencyID(int defaultCurrencyID) {
		this.defaultCurrencyID = defaultCurrencyID;
	}

	public int getDefaultUnitID() {
		return defaultUnitID;
	}

	public void setDefaultUnitID(int defaultUnitID) {
		this.defaultUnitID = defaultUnitID;
	}

	public int getDefaultLanguageID() {
		return defaultLanguageID;
	}

	public void setDefaultLanguageID(int defaultLanguageID) {
		this.defaultLanguageID = defaultLanguageID;
	}

	public boolean isAdministrator() {
		return isAdministrator;
	}

	public void setAdministrator(boolean isAdministrator) {
		this.isAdministrator = isAdministrator;
	}

	public int getCreateBy() {
		return createBy;
	}

	public void setCreateBy(int createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

}
