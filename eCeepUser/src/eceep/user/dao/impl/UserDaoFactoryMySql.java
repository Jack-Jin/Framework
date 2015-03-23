package eceep.user.dao.impl;

import eceep.user.dao.UserDao;
import eceep.user.dao.UserDaoFactory;

class UserDaoFactoryMySql implements UserDaoFactory {
	private static UserDaoFactoryMySql userDaoFactoryImpl;
	
	public static UserDaoFactoryMySql getInstance(){
		if(userDaoFactoryImpl==null){
			userDaoFactoryImpl = new UserDaoFactoryMySql();
		}
		
		return userDaoFactoryImpl;
	}
	
	@Override
	public UserDao getUserDao() {
		return new UserDaoService();
	}
}
