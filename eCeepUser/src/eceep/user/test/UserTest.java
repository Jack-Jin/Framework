package eceep.user.test;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eceep.user.domain.UserPolicy;
import eceep.user.service.User;
import eceep.user.service.impl.UserFactoryImpl;

public class UserTest {
	private User user;

	@Before
	public void Prepare() {
		user = UserFactoryImpl.getInstance();
	}

	@Test
	public void logon() throws SQLException {
		// Initial
		assert user.initial("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/eCeepFramework", "root", "123");

		// Logon
		boolean result = user.logon("test user", "peter__123");
		assert result;

		// Wrong username or password
		assert !user.logon("test user", "Peter__123");

		assert !user.logon("test use", "peter__123");

		assert !user.logon("", "");
	}

	@Test
	public void TestPolicy() throws SQLException {
		// Initial
		assert user.initial("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/eCeepFramework", "root", "123");

		// Logon
		boolean result = user.logon("test user", "peter__123");
		assert result;

		UserPolicy userPolicy = user.getUserPolicy();

		// Check
		assert userPolicy.getRuleValueBoolean("Access All Customer");
		assert !userPolicy.getRuleValueBoolean("Show All Quotes");

		// Value
		assert userPolicy.getRuleValueString("Factor").equals("1.85");
		assert userPolicy.getRuleValueString("Factor1").equals("2.88");

		List<String> ruleValueList = userPolicy.getRuleValueList("ModelList");
		ruleValueList.forEach(System.out::println);
	}
}
