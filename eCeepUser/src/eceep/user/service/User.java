package eceep.user.service;

import java.sql.SQLException;

import eceep.user.domain.ApplicationPolicy;
import eceep.user.domain.UserCompany;
import eceep.user.domain.UserDetail;

public interface User {
	boolean initial(String url, String userName, String password);
	
	boolean logon(String userName, String password) throws SQLException ;
	
	UserDetail getUserDetail();
	
	UserCompany getUserCompany();
	
	ApplicationPolicy getUserPolicy();
}
