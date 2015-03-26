package eceep.user.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import eceep.user.dao.UserDao;
import eceep.user.domain.UserDetail;

public class UserDaoService implements UserDao {

	@Override
	public boolean initial(String jdbcDriver, String url, String userName,
			String password) {
		JdbcUtils config = new JdbcUtils();
		config.setJdbcDriver(jdbcDriver);
		config.setUrl(url);
		config.setUserName(userName);
		config.setPassword(password);

		JdbcUtils.initial(config);

		return true;
	}

	@Override
	public UserDetail logon(String userName, String password, String sessionID,
			String ip, String osInfo, int sessionTimeoutInMin)
			throws SQLException {
		Connection conn = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;

		UserDetail userDetail = null;
		try {
			conn = JdbcUtils.getConnection();
			cStmt = conn.prepareCall("{ CALL UserLogon(?,?,?,?,?,?,?) }");
			cStmt.setString(1, userName); // pUserName
			cStmt.setString(2, password); // pPassword
			cStmt.setString(3, sessionID); // pSessionID
			cStmt.setString(4, ip); // pIp
			cStmt.setString(5, osInfo); // pOsInfo
			cStmt.setInt(6, sessionTimeoutInMin); // pSessionTimeout
			cStmt.registerOutParameter(7, Types.BIT); // pIsLogon

			rs = cStmt.executeQuery();
			boolean isLogon = cStmt.getBoolean(7);
			if (isLogon) {
				userDetail = new UserDetail();
				while (rs.next()) {
					userDetail.setUserName(rs.getString("UserName"));
					userDetail.setFirstName(rs.getString("FirstName"));
					userDetail.setLastName(rs.getString("LastName"));
				}
			}

			// boolean hadResults = cStmt.execute();
			// boolean isLogon = cStmt.getBoolean(7);
			// if (isLogon)
			// {
			// userDetail = new UserDetail();
			// while (hadResults) {
			// rs = cStmt.getResultSet();
			//
			// while (rs.next()) {
			// userDetail.setUserName(rs.getString("UserName"));
			// userDetail.setFirstName(rs.getString("FirstName"));
			// userDetail.setLastName(rs.getString("LastName"));
			// }
			//
			// hadResults = cStmt.getMoreResults();
			// }
			// }
		} finally {
			JdbcUtils.free(rs, cStmt, conn);
		}

		return userDetail;
	}
}
