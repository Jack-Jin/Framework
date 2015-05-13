package eceep.customer.domain;

import java.util.Date;

public class CustomerActivity {
	private int id;
	private int customerID;
	private String activity;
	private int activityTypeID;
	private String activityType;
	private String detail;
	private Date startTime;
	private Date endTime;
	private int closedByID;
	private String closedByName;
	private Date closedTime;
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

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public int getActivityTypeID() {
		return activityTypeID;
	}

	public void setActivityTypeID(int activityTypeID) {
		this.activityTypeID = activityTypeID;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getClosedByID() {
		return closedByID;
	}

	public void setClosedByID(int closedByID) {
		this.closedByID = closedByID;
	}

	public String getClosedByName() {
		return closedByName;
	}

	public void setClosedByName(String closedByName) {
		this.closedByName = closedByName;
	}

	public Date getClosedTime() {
		return closedTime;
	}

	public void setClosedTime(Date closedTime) {
		this.closedTime = closedTime;
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
