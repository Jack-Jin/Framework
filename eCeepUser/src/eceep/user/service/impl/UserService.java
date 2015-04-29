package eceep.user.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import eceep.user.dao.UserDao;
import eceep.user.dao.impl.UserDaoFactoryMySql;
import eceep.user.domain.CompanyNode;
import eceep.user.domain.UserMenu;
import eceep.user.domain.UserPolicy;
import eceep.user.domain.UserCompany;
import eceep.user.domain.UserDetail;
import eceep.user.service.User;

public class UserService implements User {
	private UserDao userDao;

	private UserDetail userDetail;
	private UserCompany userCompany;
	private UserPolicy userPolicy;
	private UserMenu userMenu;

	private boolean isLogin;

	public UserService() {
		this.isLogin = false;

		this.userDao = UserDaoFactoryMySql.getInstance();
	}

	@Override
	public boolean initial(String jdbcDriver, String url, String userName, String password) {
		boolean result = this.userDao.initial(jdbcDriver, url, userName, password);

		return result;
	}

	@Override
	public boolean logon(String userName, String password) throws SQLException {
		this.isLogin = false;

		Object[] result = userDao.logon(userName, password, "321321321", "2.2.2.2", "Win7 IE", 120);

		this.userDetail = (UserDetail) result[0];
		this.userCompany = (UserCompany) result[1];
		this.userPolicy = (UserPolicy) result[2];
		this.userMenu = (UserMenu) result[3];

		this.isLogin = (this.userDetail != null && this.userCompany != null);

		return this.isLogin;
	}

	@Override
	public boolean isLogin() {
		return isLogin;
	}

	@Override
	public UserDetail getUserDetail() {
		return this.userDetail;
	}

	@Override
	public UserCompany getUserCompany() {
		return this.userCompany;
	}

	@Override
	public UserPolicy getUserPolicy() {
		return this.userPolicy;
	}

	@Override
	public UserMenu getUserMenu() {
		return this.userMenu;
	}

	@Override
	public CompanyNode getAllOfCompanys() throws SQLException {
		return userDao.getAllOfCompanys();
	}

	@Override
	public boolean IsLeafCompany(CompanyNode node, int companyID) {
		return userDao.IsLeafCompany(node, companyID);
	}

	@Override
	public UserCompany getUserCompany(int companyID) throws SQLException {
		return userDao.getUserCompany(companyID);
	}

	@Override
	public boolean updateCompanyInfo(UserCompany company) throws SQLException {
		return userDao.updateCompanyInfo(company);
	}

	@Override
	public int AddNewCompany(boolean nearby, int companyID) throws SQLException {
		return userDao.AddNewCompany(nearby, companyID);
	}

	@Override
	public boolean RemoveCompany(int companyID, int byUserID) throws SQLException {
		return userDao.RemoveCompany(companyID, byUserID);
	}

	@Override
	public List<UserDetail> getUsersByCompanyID(int companyID) throws SQLException {
		return userDao.getUsersByCompanyID(companyID);
	}

	@Override
	public UserDetail getUserDetail(int userID) throws SQLException {
		return userDao.getUserDetail(userID);
	}

	@Override
	public boolean updateUserInfo(UserDetail userDetail, int companyID) throws SQLException {
		return userDao.updateUserInfo(userDetail, companyID);
	}

	@Override
	public int AddNewUser(int parentCompanyID, int createByID, String createBy) throws SQLException {
		return userDao.AddNewUser(parentCompanyID, createByID, createBy);
	}

	@Override
	public boolean RemoveUser(int userID, int byID, String byName) throws SQLException {
		return userDao.RemoveUser(userID, byID, byName);
	}

	@Override
	public boolean changePassword(int userID, boolean isValidateOld, String oldPassword, String newPassword)
			throws SQLException {
		return userDao.changePassword(userID, isValidateOld, oldPassword, newPassword);
	}

	@Override
	public Object[] getPolicy(boolean pTrueCompany_FalseUser, int pID) throws SQLException {
		return userDao.getPolicy(pTrueCompany_FalseUser, pID);
	}

	@Override
	public int updatePolicy(Map<Integer, Boolean> pMenus, Map<Integer, String> pRules, boolean companySelected, int id)
			throws SQLException {
		return userDao.updatePolicy(pMenus, pRules, companySelected, id);
	}

	@Override
	public void removePolicy(boolean companySelected, int id) throws SQLException {
		userDao.removePolicy(companySelected, id);
	}
}
