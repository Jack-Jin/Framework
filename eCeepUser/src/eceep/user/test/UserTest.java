package eceep.user.test;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eceep.user.domain.UserPolicy;
import eceep.user.service.User;
import eceep.user.service.impl.UserFactoryImpl;

public class UserTest {
	private User user;

	@Before
	public void Prepare() throws SQLException {
		user = UserFactoryImpl.getInstance();

		// Initial
		boolean result = user.initial("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/eCeepFramework", "root",
				"123");
		Assert.assertTrue(result);

		// Logon
		result = user.logon("test user", "peter__123");
		Assert.assertTrue(result);
	}

	@Test
	public void logon() throws SQLException {
		// Wrong username or password
		boolean result = user.logon("test user", "Peter__123");
		Assert.assertFalse(result);

		result = user.logon("test use", "peter__123");
		Assert.assertFalse(result);

		result = user.logon("", "");
		Assert.assertFalse(result);
	}

	@Test
	public void TestPolicy() {
		UserPolicy userPolicy = user.getUserPolicy();

		// Check
		Boolean bValue = userPolicy.getRuleValueBoolean("Access All Customer");
		Assert.assertTrue(bValue);

		bValue = userPolicy.getRuleValueBoolean("Show All Quotes");
		Assert.assertFalse(bValue);

		// Value
		String sValue = userPolicy.getRuleValueString("Factor");
		Assert.assertEquals(sValue, "1.85");

		sValue = userPolicy.getRuleValueString("Factor1");
		Assert.assertEquals(sValue, "2.88");

		List<String> ruleValueList = userPolicy.getRuleValueList("ModelList");
		ruleValueList.forEach(System.out::println);
	}
}
