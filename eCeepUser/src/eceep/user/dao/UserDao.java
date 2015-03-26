package eceep.user.dao;

import java.sql.SQLException;

import eceep.user.domain.UserDetail;

public interface UserDao {
	boolean initial(String jdbcDriver, String url, String userName,
			String password);

	UserDetail logon(String userName, String password, String sessionID,
			String ip, String osInfo, int sessionTimeoutInMin)
			throws SQLException;
}
