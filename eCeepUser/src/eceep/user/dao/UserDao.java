package eceep.user.dao;

import java.sql.SQLException;
import java.util.List;

import eceep.user.domain.CompanyNode;
import eceep.user.domain.UserCompany;
import eceep.user.domain.UserDetail;

public interface UserDao {
	boolean initial(String jdbcDriver, String url, String userName,
			String password);

	Object[] logon(String userName, String password, String sessionID,
			String ip, String osInfo, int sessionTimeoutInMin)
			throws SQLException;
	
	CompanyNode getAllOfCompanys() throws SQLException;
	
	UserCompany getUserCompany(int companyID) throws SQLException;
	
	List<UserDetail> getUsersByCompanyID(int companyID) throws SQLException;
	
	boolean updateCompanyInfo(UserCompany company) throws SQLException;
	
	UserDetail getUserDetail(int userID) throws SQLException;
	
	boolean updateUserInfo(UserDetail userDetail, int companyID) throws SQLException;
	
	// Return UserPolicy, UserMenu
	Object[] getPolicy(boolean pTrueCompany_FalseUser, int pID) throws SQLException;
}
