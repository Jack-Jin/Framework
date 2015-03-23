package eceep.user.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import eceep.user.dao.UserDao;
import eceep.user.dao.UserDaoFactory;
import eceep.user.domain.UserDetail;

public class UserDaoService implements UserDao {
	private static UserDaoFactory factory;

	public static UserDao getInstance() {
		if (factory == null)
			factory = new UserDaoFactoryMySql();

		return factory.getUserDao();
	}

	UserDaoService() {
	}

	@Override
	public boolean initial(String url, String userName, String password) {
		JdbcUtils config = new JdbcUtils();
		config.setJdbcDriver("com.mysql.jdbc.Driver");
		config.setUrl(url);
		config.setUserName(userName);
		config.setPassword(password);

		JdbcUtils.initial(config);

		return true;
	}

	@Override
	public UserDetail logon(String userName, String password) throws SQLException {
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		UserDetail userDetail = new UserDetail();
		try {
			conn = JdbcUtils.getConnection();
			
			cs = conn.prepareCall("{ CALL UserLogon(?,?) }");
			cs.setString(1,userName);
			cs.setString(2, password);
			
			rs = cs.executeQuery();
			
			while(rs.next()){
				userDetail.setUserName(rs.getString("UserName"));
				userDetail.setFirstName(rs.getString("FirstName"));
				userDetail.setLastName(rs.getString("LastName"));
			}
		}
		finally{
			JdbcUtils.free(rs, cs, conn);
		}
		
		return userDetail;
	}
}
