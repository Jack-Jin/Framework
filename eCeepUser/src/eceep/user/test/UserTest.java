package eceep.user.test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eceep.user.domain.CompanyNode;
import eceep.user.domain.UserCompany;
import eceep.user.domain.UserDetail;
import eceep.user.domain.UserMenuGroup;
import eceep.user.domain.UserMenuLeaf;
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
		result = user.logon("admin", "1");
		Assert.assertTrue(result);
	}

	
	@Test
	public void logon() throws SQLException {
		// Wrong username or password
		boolean result = user.logon("admin", "1");
		Assert.assertTrue(result);
		Assert.assertEquals(1, user.getUserPolicy().getId());
		//System.out.println(user.getUserPolicy().getId());

		result = user.logon("test use", "peter__123");
		Assert.assertFalse(result);

		result = user.logon("", "");
		Assert.assertFalse(result);
		
		//
		result = user.logon("test1 user", "123");
		Assert.assertTrue(result);
		UserPolicy userPolicy = user.getUserPolicy();
		Assert.assertEquals(2, userPolicy.getId()); 
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

	@Test
	public void TestMenu() {
		List<UserMenuGroup> menus = user.getUserMenu().getMenus();

		for (UserMenuGroup entry : menus) {
			System.out.println(entry.getTitle() + "(" + entry.isIsVisible()  + ")---------------------------------");
			List<UserMenuLeaf> value = entry.getLeaves();
			value.forEach(A -> System.out.println(A.getMenuText() + "(" + A.isIsVisible() + "), [" + A.getPageUrl() + "]"));
		}
	}

	@Test
	public void testCompanyTree() {
		try {
			CompanyNode allOfCompanys = user.getAllOfCompanys();

			showCompanyTree("", allOfCompanys);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void showCompanyTree(String tab, CompanyNode node) {
		for (int i = 0; i < node.getChildren().size(); i++) {
			CompanyNode child = node.getChildren().get(i);

			System.out.println(tab + child.getName() + "(" + child.getId() + ")");

			showCompanyTree(tab + "\t", child);
		}
	}

	@Test
	public void testGetUserCompany() throws SQLException {
		UserCompany userCompany = user.getUserCompany(2);

		Assert.assertEquals("__System Default", userCompany.getCompanyName());
	}

	@Test
	public void testGetUsersByCompanyID() throws SQLException {
		List<UserDetail> users = user.getUsersByCompanyID(1);

		for (UserDetail user : users) {
			System.out.println("UserID: " + user.getId() + "; UserName: " + user.getUserName());
		}
	}

	@Test
	public void testUpdateCompanyInfo() throws SQLException {
		UserCompany company = user.getUserCompany(3);

		company.setCountry("Canada Test");

		user.updateCompanyInfo(company);

		company = user.getUserCompany(3);

		Assert.assertEquals("Canada Test", company.getCountry());

	}

	@Test
	public void testUserDetail() throws SQLException {
		UserDetail userDetail = user.getUserDetail(1);

		Assert.assertEquals("admin", userDetail.getUserName());
		Assert.assertEquals(true, userDetail.isIsAdmin());
	}

	@Test
	public void testUpdateUserDetail() throws SQLException {
		UserDetail userDetail = user.getUserDetail(1);

		userDetail.setTitle("Title test");
		user.updateUserInfo(userDetail, 1);

		userDetail = user.getUserDetail(1);

		Assert.assertEquals("Title test", userDetail.getTitle());
	}
}
