package eceep.user.dao.impl;

import eceep.user.dao.UserDao;
import eceep.user.dao.UserDaoFactory;

public class UserDaoFactoryMySql implements UserDaoFactory {
	private static UserDaoFactoryMySql userDaoFactory;
	
	public static UserDao getInstance(){
		if(userDaoFactory==null){
			userDaoFactory = new UserDaoFactoryMySql();
		}
		
		return userDaoFactory.getUserDao();
	}
	
	@Override
	public UserDao getUserDao() {
		return new UserDaoService();
	}
}
