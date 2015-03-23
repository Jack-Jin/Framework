package eceep.user.dao;

import java.sql.SQLException;

import eceep.user.domain.UserDetail;

public interface UserDao {
	boolean initial(String url, String userName, String password);
	
	UserDetail logon(String userName, String password) throws SQLException;
}
