package eceep.user.service;

import java.sql.SQLException;
import java.util.List;

import eceep.user.domain.CompanyNode;
import eceep.user.domain.UserMenu;
import eceep.user.domain.UserPolicy;
import eceep.user.domain.UserCompany;
import eceep.user.domain.UserDetail;

public interface User {
	boolean initial(String jdbcDriver, String url, String userName, String password);

	boolean logon(String userName, String password) throws SQLException;

	boolean isLogin();
	
	UserDetail getUserDetail();

	UserCompany getUserCompany();

	UserPolicy getUserPolicy();
	
	UserMenu getUserMenu();
	
	CompanyNode getAllOfCompanys() throws SQLException;
	
	UserCompany getUserCompany(int companyID) throws SQLException;
	
	List<UserDetail> getUsersByCompanyID(int companyID) throws SQLException;
	
	boolean updateCompanyInfo(UserCompany company) throws SQLException;
	
	UserDetail getUserDetail(int userID) throws SQLException;
}
