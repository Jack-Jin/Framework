package eceep.user.service;

import java.sql.SQLException;

import eceep.user.domain.UserPolicy;
import eceep.user.domain.UserCompany;
import eceep.user.domain.UserDetail;

public interface User {
	boolean initial(String jdbcDriver, String url, String userName, String password);

	boolean logon(String userName, String password) throws SQLException;

	UserDetail getUserDetail();

	UserCompany getUserCompany();

	UserPolicy getUserPolicy();
}
