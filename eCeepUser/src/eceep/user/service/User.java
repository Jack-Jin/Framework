package eceep.user.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

	boolean updateUserInfo(UserDetail userDetail, int companyID) throws SQLException;

	// Return UserPolicy, UserMenu
	Object[] getPolicy(boolean pTrueCompany_FalseUser, int pID) throws SQLException;

	int updatePolicy(Map<Integer, Boolean> pMenus, Map<Integer, String> pRules, boolean companySelected, int id)
			throws SQLException;

	void removePolicy(boolean companySelected, int id) throws SQLException;
}
