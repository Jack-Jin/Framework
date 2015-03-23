package eceep.user.service.impl;

import java.sql.SQLException;

import eceep.user.dao.UserDao;
import eceep.user.dao.impl.UserDaoService;
import eceep.user.domain.ApplicationPolicy;
import eceep.user.domain.UserCompany;
import eceep.user.domain.UserDetail;
import eceep.user.service.User;
import eceep.user.service.UserFactory;

public class UserService implements User {
	private static UserFactory factory;

	public static User getInstance() {
		if (factory == null)
			factory = UserFactoryImpl.getInstance();

		return factory.getUser();
	}

	private UserDao userDao;

	private UserDetail userDetail;
	private UserCompany userCompany;
	private ApplicationPolicy userPolicy;

	UserService() {
		this.userDao = UserDaoService.getInstance();
	}

	@Override
	public boolean initial(String url, String userName, String password) {
		boolean result = this.userDao.initial(url, userName, password);
		
		return result;
	}

	@Override
	public boolean logon(String userName, String password) throws SQLException {
		this.userDetail = userDao.logon(userName, password);

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
