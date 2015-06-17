package eceep.quotation.domain;

import java.util.Date;

public class QuotationLog {
	private Date time;
	private int status;
	private String logonID;
	private String userName;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLogonID() {
		return logonID;
	}

	public void setLogonID(String logonID) {
		this.logonID = logonID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
