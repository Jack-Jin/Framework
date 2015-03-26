package eceep.user.service.impl;

import java.sql.SQLException;

import eceep.user.dao.UserDao;
import eceep.user.dao.impl.UserDaoFactoryMySql;
import eceep.user.domain.ApplicationPolicy;
import eceep.user.domain.UserCompany;
import eceep.user.domain.UserDetail;
import eceep.user.service.User;

public class UserService implements User {
	private UserDao userDao;

	private UserDetail userDetail;
	private UserCompany userCompany;
	private ApplicationPolicy userPolicy;

	public UserService() {
		this.userDao = UserDaoFactoryMySql.getInstance();
	}

	@Override
	public boolean initial(String jdbcDriver, String url, String userName,
			String password) {
		boolean result = this.userDao.initial(jdbcDriver, url, userName,
				password);

		return result;
	}

	@Override
	public boolean logon(String userName, String password) throws SQLException {
		this.userDetail = userDao.logon(userName, password, "321321321",
				"2.2.2.2", "Win7 IE", 120);

		return (this.userDetail != null);
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
	public ApplicationPolicy getUserPolicy() {
		return this.userPolicy;
	}
}
