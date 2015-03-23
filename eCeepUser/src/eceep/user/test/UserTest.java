package eceep.user.test;

import java.sql.SQLException;

import junit.framework.Assert;

import org.junit.Test;

import eceep.user.service.User;
import eceep.user.service.impl.UserService;

public class UserTest {
	@Test
	public void logon() throws SQLException {
		User user = UserService.getInstance();
		
		// Initial
		boolean result =user.initial("jdbc:mysql://localhost:3306/eCeepFramework", "root", "123");
		Assert.assertTrue(result);
		
		// Logon
		result = user.logon("test user", "peter__123");
		Assert.assertTrue(result);

		// Wrong username or password
		result = user.logon("test user", "peter__12");
		Assert.assertTrue(result);
		result = user.logon("test use", "peter__123");
		Assert.assertTrue(result);
		result = user.logon("", "");
		Assert.assertTrue(result);
	}
}
