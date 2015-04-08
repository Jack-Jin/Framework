package eceep.user.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import eceep.user.dao.UserDao;
import eceep.user.domain.CompanyNode;
import eceep.user.domain.UserMenu;
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

			// rs = cStmt.executeQuery();
			// boolean isLogon = cStmt.getBoolean(7);
			// if (isLogon) {
			// userDetail = new UserDetail();
			// if (rs.next()) {
			// try {
			// userDetail = JdbcUtils.ResultSet2Object(rs, UserDetail.class);
			// } catch (Exception e) {
			// userDetail = null;
			// }
			// }
			// }

			boolean hadResults = cStmt.execute();
			boolean isLogon = cStmt.getBoolean(7);
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

				// 3. Get User Policy
				hadResults = cStmt.getMoreResults();
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

			}
		} finally {
			JdbcUtils.free(rs, cStmt, conn);
		}

		Object[] result = new Object[4];
		result[0] = userDetail;
		result[1] = userCompany;
		result[2] = userPolicy;
		result[3] = userMenu;

		return result;
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
			String sql = "SELECT ID,CompanyName,Address,Address1,City,State,Country,PostalCode";
			sql += ",Telephone,Fax,EMail,WWW,ContactID,ContactName,IsSystem,ParentID ";
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

	/* Functions */
	/* -------------------------------------------------- */
	private void result2PolicyDetail(ResultSet rs, List<UserPolicyRule> rules) throws SQLException {
		// PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,ValueType,RuleValue
		String ruleName = "";
		List<String> values = null;

		while (rs.next()) {
			String valueType = rs.getString("ValueType");
			int ruleID = rs.getInt("PolicyRuleID");
			String value = rs.getString("RuleValue");

			if (valueType.equalsIgnoreCase("value")) { // ValueType(value)
				// Rule Name
				ruleName = rs.getString("PolicyRuleName");

				UserPolicyRule<String> policyRule = new UserPolicyRule<String>(String.class);
				rules.add(policyRule);
				// ID, key
				policyRule.setId(ruleID);
				policyRule.setKey(ruleName);
				// Value
				policyRule.setValue(value);
			} else if (valueType.equalsIgnoreCase("check")) { // ValueType(check)
				// Rule Name
				ruleName = rs.getString("PolicyRuleName");

				UserPolicyRule<Boolean> policyRule = new UserPolicyRule<Boolean>(Boolean.class);
				rules.add(policyRule);
				// ID, key
				policyRule.setId(ruleID);
				policyRule.setKey(ruleName);
				// Value
				policyRule.setValue((value.equalsIgnoreCase("true") ? true : false));
			} else if (valueType.equalsIgnoreCase("option")) { // ValueType(option)
				if (!ruleName.equalsIgnoreCase(rs.getString("PolicyRuleName"))) {
					// Rule Name
					ruleName = rs.getString("PolicyRuleName");

					UserPolicyRule<List> policyRule = new UserPolicyRule<List>(List.class);
					rules.add(policyRule);
					// ID, Key
					policyRule.setId(ruleID);
					policyRule.setKey(ruleName);
					// Value
					values = new ArrayList<String>();
					values.add(value);
					policyRule.setValue(values);
				} else {
					values.add(value);
				}
			}
		}
	}

	private UserMenu resultSet2Menu(ResultSet rs) throws SQLException {
		UserMenu userMenu = new UserMenu();

		Map<String, List<UserMenuLeaf>> menus = userMenu.getMenus();
		menus.clear();

		List<UserMenuLeaf> menuLeaives = null;

		while (rs.next()) {
			// ID,ParentID,MenuText,IsLeaf,MenuNo,URL
			String menuText = rs.getString("MenuText");
			boolean isLeaf = rs.getBoolean("IsLeaf");

			if (!isLeaf) {
				menuLeaives = new ArrayList<UserMenuLeaf>();
				menus.put(menuText, menuLeaives);
			} else {
				UserMenuLeaf menuLeaf = new UserMenuLeaf();
				menuLeaf.setMenuText(menuText);
				menuLeaf.setPageUrl(rs.getString("URL"));
				menuLeaf.setEnabled(true);
				menuLeaf.setVisible(true);

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
