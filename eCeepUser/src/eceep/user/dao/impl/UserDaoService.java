package eceep.user.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.LinkedMap;

import eceep.user.dao.UserDao;
import eceep.user.domain.CompanyNode;
import eceep.user.domain.UserMenu;
import eceep.user.domain.UserMenuGroup;
import eceep.user.domain.UserMenuLeaf;
import eceep.user.domain.UserPolicy;
import eceep.user.domain.UserCompany;
import eceep.user.domain.UserDetail;
import eceep.user.domain.UserPolicyRule;

public class UserDaoService implements UserDao {
	/* Methods */
	/* -------------------------------------------------- */
	@Override
	public boolean initial(String jdbcDriver, String url, String userName, String password) {
		JdbcUtils config = new JdbcUtils();
		config.setJdbcDriver(jdbcDriver);
		config.setUrl(url);
		config.setUserName(userName);
		config.setPassword(password);

		JdbcUtils.initial(config);

		return true;
	}

	@Override
	public Object[] logon(String userName, String password, String sessionID, String ip, String osInfo,
			int sessionTimeoutInMin) throws SQLException {

		Connection conn = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;

		UserDetail userDetail = null;
		UserCompany userCompany = null;
		UserPolicy userPolicy = null;
		UserMenu userMenu = null;

		boolean isLogon = false;

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

			boolean hadResults = cStmt.execute();
			isLogon = cStmt.getBoolean(7);
			if (isLogon) {
				// 1. Get user info.
				if (hadResults) {
					rs = cStmt.getResultSet();
					if (rs.next()) {
						try {
							userDetail = JdbcUtils.ResultSet2Object(rs, UserDetail.class);
						} catch (Exception e) {
							userDetail = null;
						}
					}
				}

				// 2. Get company info.
				hadResults = cStmt.getMoreResults();
				if (hadResults) {
					rs = cStmt.getResultSet();
					if (rs.next()) {
						try {
							userCompany = JdbcUtils.ResultSet2Object(rs, UserCompany.class);
						} catch (Exception e) {
							userCompany = null;
						}
					}
				}
			}
		} finally {
			JdbcUtils.free(rs, cStmt, conn);
		}

		if (isLogon && userDetail != null) {
			Object[] resultPolicy = getPolicy(false, userDetail.getId());
			userPolicy = (UserPolicy) resultPolicy[0];
			userMenu = (UserMenu) resultPolicy[1];
		}

		Object[] result = new Object[4];
		result[0] = userDetail;
		result[1] = userCompany;
		result[2] = userPolicy;
		result[3] = userMenu;

		return result;
	}

	@Override
	public Object[] getPolicy(boolean pTrueCompany_FalseUser, int pID) throws SQLException {
		Connection conn = null;
		CallableStatement cStmt = null;
		ResultSet rs = null;

		UserPolicy userPolicy = null;
		UserMenu userMenu = null;

		try {
			conn = JdbcUtils.getConnection();

			cStmt = conn.prepareCall("{ CALL GetPolicy(?,?) }");
			cStmt.setBoolean(1, pTrueCompany_FalseUser);
			cStmt.setInt(2, pID);

			boolean hadResults = cStmt.execute();

			// 3. Get User Policy
			if (hadResults) {
				rs = cStmt.getResultSet();
				if (rs.next()) {
					try {
						userPolicy = JdbcUtils.ResultSet2Object(rs, UserPolicy.class);
					} catch (Exception e) {
						userPolicy = null;
					}
				}

				// 4. Get User Menu.
				hadResults = cStmt.getMoreResults();
				if (hadResults) {
					rs = cStmt.getResultSet();

					userMenu = resultSet2Menu(rs);
				}

				// 5. Get Policy Detail.
				hadResults = cStmt.getMoreResults();
				if (userPolicy != null && hadResults) {
					rs = cStmt.getResultSet();

					result2PolicyDetail(rs, userPolicy.getRules());
				}
			}
		} finally {
			JdbcUtils.free(rs, cStmt, conn);
		}

		Object[] result = new Object[2];
		result[0] = userPolicy;
		result[1] = userMenu;

		return result;
	}

	@Override
	public List<UserDetail> getUsersByCompanyID(int companyID) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<UserDetail> users = new ArrayList<UserDetail>();

		try {
			conn = JdbcUtils.getConnection();

			String sql = "SELECT ID,UserName,FirstName,LastName,Title,Address,Address1,City,State,Country,PostalCode";
			sql += ",Telephone,Fax,Email,WWW,Note,CurrencyID,UnitID,LanguageID,IsAdmin,CreateByID,CreateDate,LoginTime,LogoutTime ";
			sql += "FROM Users WHERE CompanyID=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, companyID);

			rs = ps.executeQuery();

			while (rs.next()) {
				UserDetail userDetail = JdbcUtils.ResultSet2Object(rs, UserDetail.class);
				users.add(userDetail);
			}

		} catch (InstantiationException | IllegalAccessException e) {
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return users;
	}

	@Override
	public UserCompany getUserCompany(int companyID) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		UserCompany company = null;

		try {
			conn = JdbcUtils.getConnection();

			String sql = "SELECT ID,CompanyName,Address,Address1,City,State,Country,PostalCode";
			sql += ",Telephone,Fax,EMail,WWW,ContactID,ContactName,IsSystem,ParentID ";
			sql += "FROM UserCompany WHERE ID=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, companyID);

			rs = ps.executeQuery();
			if (rs.next()) {
				company = JdbcUtils.ResultSet2Object(rs, UserCompany.class);
			}
		} catch (InstantiationException | IllegalAccessException e) {
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return company;
	}

	@Override
	public CompanyNode getAllOfCompanys() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		CompanyNode node = new CompanyNode(0, "root");

		List<UserCompany> companys = new ArrayList<UserCompany>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "SELECT ID,CompanyName,ParentID ";
			sql += "FROM UserCompany";
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				UserCompany company = JdbcUtils.ResultSet2Object(rs, UserCompany.class);

				companys.add(company);
			}
		} catch (InstantiationException | IllegalAccessException e) {
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		if (companys.size() > 0) {
			getCompanyTree(node, companys);
		}

		return node;
	}

	@Override
	public boolean updateCompanyInfo(UserCompany company) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int updateCount = 0;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "UPDATE UserCompany SET ";
			sql += "CompanyName=?,Address=?,Address1=?,City=?,State=?,";
			sql += "Country=?,PostalCode=?,Telephone=?,Fax=?,EMail=?,";
			sql += "WWW=?,ContactName=? ";
			sql += "WHERE ID=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, company.getCompanyName());
			ps.setString(2, company.getAddress());
			ps.setString(3, company.getAddress1());
			ps.setString(4, company.getCity());
			ps.setString(5, company.getState());
			ps.setString(6, company.getCountry());
			ps.setString(7, company.getPostalCode());
			ps.setString(8, company.getTelephone());
			ps.setString(9, company.getFax());
			ps.setString(10, company.getEmail());
			ps.setString(11, company.getWww());
			ps.setString(12, company.getContactName());
			ps.setInt(13, company.getId());

			updateCount = ps.executeUpdate();
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return (updateCount > 0) ? true : false;
	}

	@Override
	public UserDetail getUserDetail(int userID) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		UserDetail userDetail = null;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "SELECT ID,UserName,FirstName,LastName,Title,Address,Address1,City,";
			sql += "State,Country,PostalCode,Telephone,Fax,Email,WWW,Note,CurrencyID,UnitID,";
			sql += "LanguageID,IsAdmin,IsNeverExpire,ExpiryDate,CreateByID,CreateDate,LoginTime,LogoutTime ";
			sql += "FROM Users WHERE ID=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userID);

			rs = ps.executeQuery();

			if (rs.next()) {
				userDetail = JdbcUtils.ResultSet2Object(rs, UserDetail.class);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return userDetail;
	}

	@Override
	public boolean updateUserInfo(UserDetail userDetail, int companyID) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int updateCount = 0;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "UPDATE Users SET UserName=?,FirstName=?,LastName=?,Title=?,Address=?";
			sql += ",Address1=?,City=?,State=?,Country=?,PostalCode=?";
			sql += ",Telephone=?,Fax=?,Email=?,WWW=?,Note=?";
			sql += ",IsAdmin=?,IsNeverExpire=?,ExpiryDate=?,CompanyID=?,Company=(SELECT CompanyName FROM UserCompany WHERE ID=?) ";
			sql += "WHERE ID=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, userDetail.getUserName());
			ps.setString(2, userDetail.getFirstName());
			ps.setString(3, userDetail.getLastName());
			ps.setString(4, userDetail.getTitle());
			ps.setString(5, userDetail.getAddress());
			ps.setString(6, userDetail.getAddress1());
			ps.setString(7, userDetail.getCity());
			ps.setString(8, userDetail.getState());
			ps.setString(9, userDetail.getCountry());
			ps.setString(10, userDetail.getPostalCode());
			ps.setString(11, userDetail.getTelephone());
			ps.setString(12, userDetail.getFax());
			ps.setString(13, userDetail.getEmail());
			ps.setString(14, userDetail.getWww());
			ps.setString(15, userDetail.getNote());
			ps.setBoolean(16, userDetail.isIsAdmin());
			ps.setBoolean(17, userDetail.isIsNeverExpire());
			ps.setDate(18, new Date(userDetail.getExpiryDate().getTime()));
			ps.setInt(19, companyID);
			ps.setInt(20, companyID);
			ps.setInt(21, userDetail.getId());

			updateCount = ps.executeUpdate();

		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return (updateCount > 0) ? true : false;
	}

	/* Functions */
	/* -------------------------------------------------- */
	private void result2PolicyDetail(ResultSet rs, List<UserPolicyRule> rules) throws SQLException {
		// PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,RuleOptionName,RuleOptionValue,ValueType,RuleValue
		String ruleName = "";
		Map<String, String> options = null;
		Map<String, Boolean> values = null;

		while (rs.next()) {
			String valueType = rs.getString("ValueType");
			int ruleID = rs.getInt("PolicyRuleID");
			String value = rs.getString("RuleValue");

			if (valueType.equalsIgnoreCase("value")) { // ValueType(value)
				// Rule Name
				ruleName = rs.getString("PolicyRuleName");

				// Policy Rule
				UserPolicyRule<String> policyRule = new UserPolicyRule<String>(String.class);
				policyRule.setId(ruleID);
				policyRule.setName(ruleName);
				// Policy Rule - Value
				policyRule.setValue(value);

				// Add Policy Rule in Rules
				rules.add(policyRule);
			} else if (valueType.equalsIgnoreCase("check")) { // ValueType(check)
				// Rule Name
				ruleName = rs.getString("PolicyRuleName");

				// Policy Rule
				UserPolicyRule<Boolean> policyRule = new UserPolicyRule<Boolean>(Boolean.class);
				policyRule.setId(ruleID);
				policyRule.setName(ruleName);
				// Policy Rule - Value
				policyRule.setValue((value.equalsIgnoreCase("true") ? true : false));

				// Add Policy Rule in Rules
				rules.add(policyRule);
			} else if (valueType.equalsIgnoreCase("option")) { // ValueType(option)
				String ruleOptionName = rs.getString("RuleOptionName");
				String ruleOptionValue = rs.getString("RuleOptionValue");

				if (!ruleName.equalsIgnoreCase(rs.getString("PolicyRuleName"))) {
					// Rule Name
					ruleName = rs.getString("PolicyRuleName");
					
					// Policy Rule
					UserPolicyRule<Map> policyRule = new UserPolicyRule<Map>(Map.class);
					policyRule.setId(ruleID);
					policyRule.setName(ruleName);
					// Policy Rule - Option
					options = new LinkedMap<String, String>();
					options.put(ruleOptionName, ruleOptionValue);
					policyRule.setOptions(options);
					// Policy Rule - Value
					values = new LinkedMap<String,Boolean>();
					values.put(ruleOptionName, (value.equalsIgnoreCase("true") ? true : false));					
					policyRule.setValue(values);
					
					// Add Policy Rule in Rules
					rules.add(policyRule);
				} else {
					// Policy Rule - Option
					options.put(ruleOptionName, ruleOptionValue);
					
					// Policy Rule - Value
					values.put(ruleOptionName, (value.equalsIgnoreCase("true") ? true : false));
				}
			}
		}
	}

	private UserMenu resultSet2Menu(ResultSet rs) throws SQLException {
		UserMenu userMenu = new UserMenu();

		List<UserMenuGroup> menus = userMenu.getMenus();
		menus.clear();

		List<UserMenuLeaf> menuLeaives = null;

		while (rs.next()) {
			// ID,ParentID,MenuText,IsLeaf,MenuNo,URL
			String menuText = rs.getString("MenuText");
			boolean isLeaf = rs.getBoolean("IsLeaf");
			boolean isVisible = rs.getBoolean("IsVisiable");

			if (!isLeaf) {
				UserMenuGroup menuGroup = new UserMenuGroup();
				menuGroup.setTitle(menuText);
				menuLeaives = menuGroup.getLeaves();

				menus.add(menuGroup);
			} else {
				UserMenuLeaf menuLeaf = new UserMenuLeaf();
				menuLeaf.setMenuText(menuText);
				menuLeaf.setPageUrl(rs.getString("URL"));
				menuLeaf.setIsVisible(isVisible);

				menuLeaives.add(menuLeaf);
			}
		}

		return userMenu;
	}

	private void getCompanyTree(CompanyNode node, List<UserCompany> companys) {
		if (companys.size() <= 0)
			return;

		List<UserCompany> children = companys.stream().filter(A -> A.getParentID() == node.getId())
				.collect(Collectors.toList());

		for (int i = 0; children != null && i < children.size(); i++) {
			CompanyNode childNode = new CompanyNode(children.get(i).getId(), children.get(i).getCompanyName());
			getCompanyTree(childNode, companys);

			node.getChildren().add(childNode);
		}
	}
}
