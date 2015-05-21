package eceep.user.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import eceep.user.domain.CompanyNode;
import eceep.user.domain.UserCompany;
import eceep.user.domain.UserDetail;

public interface UserDao {
	// Initial
	boolean initial(String jdbcDriver, String url, String userName, String password);

	// Log on
	Object[] logon(String userName, String password, String sessionID, String ip, String osInfo, int sessionTimeoutInMin)
			throws SQLException;

	// -- Customer manager -----------------------------------------------------------
	CompanyNode getAllOfCompanys() throws SQLException;

	boolean isLeafCompany(CompanyNode node, int companyID);

	UserCompany getUserCompany(int companyID) throws SQLException;

	boolean updateCompanyInfo(UserCompany company) throws SQLException;

	int addNewCompany(boolean nearby, int companyID) throws SQLException;

	boolean removeCompany(int companyID, int byUserID) throws SQLException;

	// -- User manager ----------------------------------------------------------------
	List<UserDetail> getUsersByCompanyID(int companyID) throws SQLException;

	UserDetail getUserDetail(int userID) throws SQLException;

	boolean updateUserInfo(UserDetail userDetail, int companyID) throws SQLException;

	public int addNewUser(int parentCompanyID, int createByID, String createBy) throws SQLException;

	boolean removeUser(int userID, int byID, String byName) throws SQLException;

	boolean changePassword(int userID, boolean isValidateOld, String oldPassword, String newPassword)
			throws SQLException;

	// -- Policy manager ---------------------------------------------------------------
	// Return UserPolicy, UserMenu
	Object[] getPolicy(boolean pTrueCompany_FalseUser, int pID) throws SQLException;

	int updatePolicy(Map<Integer, Boolean> pMenus, Map<Integer, String> pRules, boolean companySelected, int id)
			throws SQLException;

	void removePolicy(boolean companySelected, int id) throws SQLException;
}
