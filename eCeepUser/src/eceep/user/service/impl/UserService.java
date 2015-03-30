package eceep.user.service.impl;

import java.sql.SQLException;

import eceep.user.dao.UserDao;
import eceep.user.dao.impl.UserDaoFactoryMySql;
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

	public UserService() {
		this.userDao = UserDaoFactoryMySql.getInstance();
	}

	@Override
	public boolean initial(String jdbcDriver, String url, String userName, String password) {
		boolean result = this.userDao.initial(jdbcDriver, url, userName, password);

		return result;
	}

	@Override
	public boolean logon(String userName, String password) throws SQLException {
		Object[] result = userDao.logon(userName, password, "321321321", "2.2.2.2", "Win7 IE", 120);

		this.userDetail = (UserDetail) result[0];
		this.userCompany = (UserCompany) result[1];
		this.userPolicy = (UserPolicy) result[2];
		this.userMenu = (UserMenu) result[3];

		return (this.userDetail != null) && (this.userCompany != null);
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
}