package eceep.user.test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import eceep.user.domain.CompanyNode;
import eceep.user.domain.UserCompany;
import eceep.user.domain.UserDetail;
import eceep.user.domain.UserMenu;
import eceep.user.domain.UserMenuGroup;
import eceep.user.domain.UserMenuLeaf;
import eceep.user.domain.UserPolicy;
import eceep.user.domain.UserPolicyOption;
import eceep.user.service.User;
import eceep.user.service.impl.UserFactoryImpl;

public class UserTest {
	private User user;

	@Before
	public void prepare() throws SQLException {
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
		// System.out.println(user.getUserPolicy().getId());

		result = user.logon("test use", "peter__123");
		Assert.assertFalse(result);

		result = user.logon("", "");
		Assert.assertFalse(result);

		//
		result = user.logon("test1 user", "123");
		Assert.assertTrue(result);
		UserPolicy userPolicy = user.getUserPolicy();
		System.out.println(userPolicy.getId());
	}

	@Test
	public void testPolicy() {
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

		displayPolicy(userPolicy);
	}

	@Test
	public void testMenu() {
		UserMenu userMenu = user.getUserMenu();

		this.displayMenu(userMenu);
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

	@Test
	public void testGetUserCompany() throws SQLException {
		UserCompany userCompany = user.getUserCompany(2);

		Assert.assertEquals("__System Default", userCompany.getCompanyName());
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
	public void AddNewCompany() throws SQLException {
		int newCompanyID = user.AddNewCompany(true, 4);

		Assert.assertTrue(newCompanyID > -1);

		System.out.println(newCompanyID);
		
		user.RemoveCompany(newCompanyID, 1);
	}

	@Test
	public void testGetUsersByCompanyID() throws SQLException {
		List<UserDetail> users = user.getUsersByCompanyID(1);

		for (UserDetail user : users) {
			System.out.println("UserID: " + user.getId() + "; UserName: " + user.getUserName() + "; PolicyInherited: "
					+ user.isPolicyInherited());
		}
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

	@Test
	public void testGetPolicy() throws SQLException {
		Object[] result = user.getPolicy(true, 1);
		UserPolicy policy = (UserPolicy) result[0];
		this.displayPolicy(policy);

		System.out.println("\n== Menu =================================================");
		UserMenu menu = (UserMenu) result[1];
		this.displayMenu(menu);
	}

	@Test
	public void testUpdateCompanyPolicy() throws SQLException {
		Map<Integer, Boolean> menus = new HashMap<Integer, Boolean>();
		menus.put(11, true);
		menus.put(12, true);
		menus.put(13, true);
		menus.put(14, true);
		menus.put(15, true);

		Map<Integer, String> rules = new HashMap<Integer, String>();
		rules.put(21, "False");
		rules.put(101, "True");
		rules.put(102, "True");
		rules.put(103, "False");
		rules.put(104, "True");
		rules.put(105, "False");
		rules.put(500, "50");

		int companyID = 4;
		int policyID = user.updatePolicy(menus, rules, true, companyID);

		Object[] result = user.getPolicy(true, companyID);
		UserPolicy policy = (UserPolicy) result[0];
		this.displayPolicy(policy);

		System.out.println("\n== Menu =================================================");
		UserMenu menu = (UserMenu) result[1];
		this.displayMenu(menu);
	}

	@Test
	public void testUpdateUserPolicy() throws SQLException {
		Map<Integer, Boolean> menus = new HashMap<Integer, Boolean>();
		menus.put(11, true);
		menus.put(12, true);
		menus.put(13, true);
		menus.put(14, true);
		menus.put(15, true);

		Map<Integer, String> rules = new HashMap<Integer, String>();
		rules.put(21, "False");
		rules.put(101, "True");
		rules.put(102, "True");
		rules.put(103, "False");
		rules.put(104, "True");
		rules.put(105, "False");
		rules.put(500, "50");

		int userID = 2;
		int policyID = user.updatePolicy(menus, rules, false, userID);

		Object[] result = user.getPolicy(false, userID);
		UserPolicy policy = (UserPolicy) result[0];
		this.displayPolicy(policy);

		System.out.println("\n== Menu =================================================");
		UserMenu menu = (UserMenu) result[1];
		this.displayMenu(menu);
	}

	private void showCompanyTree(String tab, CompanyNode node) {
		for (int i = 0; i < node.getChildren().size(); i++) {
			CompanyNode child = node.getChildren().get(i);

			System.out.println(tab + child.getName() + "(" + child.getId() + " ,Inherited: "
					+ child.isPolicyInherited() + ")");

			showCompanyTree(tab + "\t", child);
		}
	}

	private void displayMenu(UserMenu menu) {
		List<UserMenuGroup> menus = menu.getMenus();

		for (UserMenuGroup entry : menus) {
			System.out.println(entry.getTitle() + "(" + entry.isIsVisible() + ")---------------------------------");
			List<UserMenuLeaf> value = entry.getLeaves();
			value.forEach(A -> System.out.println(A.getId() + " - " + A.getMenuText() + "(" + A.isIsVisible() + "), ["
					+ A.getPageUrl() + "]"));
		}
	}

	private void displayPolicy(UserPolicy userPolicy) {
		String optionPolicyName = userPolicy.getPolicyName();
		System.out.println("Option policy name: " + optionPolicyName);

		System.out.println("Access All Customer: " + userPolicy.getRuleValueBoolean("Access All Customer"));
		System.out.println("Show All Quotes: " + userPolicy.getRuleValueBoolean("Show All Quotes"));

		System.out.println("Show All Quotes: " + userPolicy.getRuleValueString("Factor"));
		System.out.println("Show All Quotes: " + userPolicy.getRuleValueString("Factor1"));

		List<UserPolicyOption> ruleValueOptions = userPolicy.getRuleValueOptions("ModelList");
		for (UserPolicyOption option : ruleValueOptions) {
			System.out.println(option.getOptionName() + " : " + option.getOptionValue() + ", " + option.isRuleValue());
		}

		List<UserPolicyOption> materialOptions = userPolicy.getRuleValueOptions("MaterialList");
		materialOptions.forEach(A -> System.out.println(A.getOptionName() + " : " + A.getOptionValue() + ", "
				+ A.isRuleValue()));

		// ruleValueList.forEach(System.out::println);
	}
}
