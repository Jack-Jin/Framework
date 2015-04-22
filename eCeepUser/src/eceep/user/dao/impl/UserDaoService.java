package eceep.user.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import eceep.user.domain.UserPolicyOption;
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

			// 1. Get User Policy
			if (hadResults) {
				rs = cStmt.getResultSet();
				if (rs.next()) {
					try {
						userPolicy = JdbcUtils.ResultSet2Object(rs, UserPolicy.class);
					} catch (Exception e) {
						userPolicy = null;
					}
				}

				// 2. Get User Menu.
				hadResults = cStmt.getMoreResults();
				if (hadResults) {
					rs = cStmt.getResultSet();

					userMenu = resultSet2Menu(rs);
				}

				// 3. Get Policy Detail.
				hadResults = cStmt.getMoreResults();
				if (userPolicy != null && hadResults) {
					rs = cStmt.getResultSet();

					result2PolicyDetail(rs, userPolicy.getRules());
				}

				// 4. Get Policy Inherited.
				hadResults = cStmt.getMoreResults();
				if (userPolicy != null && hadResults) {
					rs = cStmt.getResultSet();

					boolean policyInherited = false;
					if (rs.next())
						policyInherited = rs.getBoolean("PolicyInherited");

					userPolicy.setPolicyInherited(policyInherited);
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

		CompanyNode node = new CompanyNode(0, "root", false);

		List<UserCompany> companys = new ArrayList<UserCompany>();
		try {
			conn = JdbcUtils.getConnection();
			String sql = "SELECT ID,CompanyName,ParentID,IFNULL(PolicyID,2)<=2 AS 'PolicyInherited' ";
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
	public int AddNewCompany(boolean nearby, int companyID) throws SQLException {
		// Super or Default company can not add child company.
		if(!nearby && (companyID==1 || companyID==2))
			return companyID;
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int newCompanyID = -1;
		try {
			conn = JdbcUtils.getConnection();
			
			String sql = "";
			if(nearby){
				sql = "INSERT UserCompany(CompanyName,ParentID,ParentName,PolicyID,Policy) ";
				sql += "SELECT 'New Company' AS 'CompanyName',ParentID,ParentName";
				sql += ",IF(ParentID=0,2,NULL) AS 'PolicyID',IF(ParentID=0,'System Default Policy',NULL) AS 'Policy' FROM UserCompany WHERE ID=?";
			} else {
				sql = "INSERT UserCompany(CompanyName,ParentID,ParentName) ";
				sql += "SELECT 'New Company' AS 'CompanyName',ID AS 'ParentID',CompanyName AS 'ParentName' FROM UserCompany WHERE ID=?";
			}
				
			ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, companyID);
			
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			
			if(rs.next())
				newCompanyID = rs.getInt(1);
			
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}
		
		return newCompanyID;
	}

	@Override
	public boolean RemoveCompany(int companyID, int byUserID) throws SQLException {
		Connection conn = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		boolean result = false;
		
		try{
			conn = JdbcUtils.getConnection();
			
			cs = conn.prepareCall("{ CALL RemoveCompany(?,?) }");
			cs.setInt(1, companyID);
			cs.setInt(2, byUserID);
			
			cs.execute();
			
			result = true;
			
		} finally {
			JdbcUtils.free(rs, cs, conn);
		}
		
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
			sql += ",Telephone,Fax,Email,WWW,Note,CurrencyID,UnitID,LanguageID,IsAdmin,CreateByID,CreateDate,LoginTime,LogoutTime,";
			sql += "IFNULL(PolicyID,2)<=2 AS 'PolicyInherited' ";
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

	@Override
	public int updatePolicy(Map<Integer, Boolean> pMenus, Map<Integer, String> pRules, boolean companySelected, int id)
			throws SQLException {
		int policyID = -1;

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = JdbcUtils.getConnection();

			boolean inherited = false;

			// Get Policy ID, Is inherited.
			String sql = "";
			if (companySelected)
				sql = "SELECT PolicyID,IFNULL(PolicyID,2)<=2 AS 'PolicyInherited' FROM UserCompany WHERE ID=?";
			else
				sql = "SELECT PolicyID,IFNULL(PolicyID,2)<=2 AS 'PolicyInherited' FROM Users WHERE ID=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);

			rs = ps.executeQuery();
			if (rs.next()) {
				policyID = rs.getInt("PolicyID");
				inherited = rs.getBoolean("PolicyInherited");
			}

			// Determine is insert or update.
			boolean isInsert = false;
			if (inherited) {
				if (companySelected) {
					if (id != 1 && id != 2) { // admin company & default company
						isInsert = true;
					}
				} else { // user
					isInsert = true;
				}
			}

			// Default company policy.
			boolean defaultCompanyPolicy = false;
			if (companySelected && id == 2)
				defaultCompanyPolicy = true;

			if (isInsert) {
				// Insert new policy from default policy.
				sql = "INSERT INTO Policy(PolicyName,Menus) SELECT ? AS 'PolicyName',Menus FROM Policy WHERE ID=2";
				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				String policyName = companySelected ? "C_" + id : "U_" + id;
				ps.setString(1, policyName);

				int count = ps.executeUpdate();

				rs = ps.getGeneratedKeys();
				if (rs.next())
					policyID = rs.getInt(1);

				if (count > 0) {
					// Insert Policy Detail from default policy.
					sql = "INSERT PolicyDetail(PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,RuleValue) ";
					sql += "SELECT ? AS 'PolicyID',? AS 'PolicyName',ID AS 'PolicyRuleID',RuleName AS 'PolicyRuleName',RuleValue ";
					sql += "FROM PolicyRule ORDER BY DisplayOrder";
					ps = conn.prepareStatement(sql);
					ps.setInt(1, policyID);
					ps.setString(2, policyName);

					count = ps.executeUpdate();

					// Update company or user policy ID by new policy ID.
					if (companySelected)
						sql = "UPDATE UserCompany SET PolicyID=?,Policy=? WHERE ID=?";
					else
						sql = "UPDATE Users SET PolicyID=?,Policy=? WHERE ID=?";
					ps = conn.prepareStatement(sql);
					ps.setInt(1, policyID);
					ps.setString(2, policyName);
					ps.setInt(3, id);

					count = ps.executeUpdate();
				}
			}

			// Update Menu
			String menus = "-1";
			for (Map.Entry<Integer, Boolean> menu : pMenus.entrySet())
				menus += menu.getValue() ? "," + menu.getKey() : "";

			sql = "UPDATE Policy SET Menus=? WHERE ID=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, menus);
			ps.setInt(2, policyID);

			int affectCount = ps.executeUpdate();

			// Update Details
			// set value is false when value type is check or option.
			if (defaultCompanyPolicy) {
				sql = "UPDATE PolicyRule SET RuleValue='False' WHERE ValueType='Check' OR ValueType='Option'";
				ps = conn.prepareStatement(sql);
			} else {
				sql = "UPDATE PolicyDetail SET RuleValue='False' WHERE PolicyID=? AND PolicyRuleID in (SELECT ID FROM PolicyRule WHERE ValueType='Check' OR ValueType='Option')";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, policyID);
			}
			ps.executeUpdate();

			// update values.
			if (defaultCompanyPolicy) {
				sql = "UPDATE PolicyRule SET RuleValue=? WHERE ID=?";
				ps = conn.prepareStatement(sql);

				for (Map.Entry<Integer, String> rule : pRules.entrySet()) {
					String v = rule.getValue().equals("on") ? "True" : rule.getValue();
					ps.setString(1, v);
					ps.setInt(2, rule.getKey());

					ps.addBatch();
				}
			} else {
				sql = "UPDATE PolicyDetail SET RuleValue=? WHERE PolicyID=? AND PolicyRuleID=?";
				ps = conn.prepareStatement(sql);

				for (Map.Entry<Integer, String> rule : pRules.entrySet()) {
					String v = rule.getValue().equals("on") ? "True" : rule.getValue();
					ps.setString(1, v);
					ps.setInt(2, policyID);
					ps.setInt(3, rule.getKey());

					ps.addBatch();
				}
			}

			ps.executeBatch();

		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return policyID;
	}

	@Override
	public void removePolicy(boolean companySelected, int id) throws SQLException {
		Connection conn = null;
		CallableStatement cs = null;

		try {
			conn = JdbcUtils.getConnection();

			cs = conn.prepareCall("{ CALL RemovePolicy(?,?) }");
			cs.setBoolean(1, companySelected);
			cs.setInt(2, id);

			cs.execute();
		} finally {
			JdbcUtils.free(null, cs, conn);
		}
	}

	/* Functions */
	/* -------------------------------------------------- */
	private void result2PolicyDetail(ResultSet rs, List<UserPolicyRule> rules) throws SQLException {
		// PolicyID,PolicyName,PolicyRuleID,PolicyRuleName,RuleOptionName,RuleOptionValue,ValueType,RuleValue
		String ruleName = "";
		List<UserPolicyOption> options = null;

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
					Class<?> userPolicyOptionClazz = (new ArrayList<UserPolicyOption>()).getClass();
					UserPolicyRule<List<UserPolicyOption>> policyRule = new UserPolicyRule(userPolicyOptionClazz);
					policyRule.setId(ruleID);
					policyRule.setName(ruleName);
					// Policy Rule - Option
					options = new ArrayList<UserPolicyOption>();
					options.add(new UserPolicyOption(ruleID, ruleOptionName, ruleOptionValue, (value
							.equalsIgnoreCase("true") ? true : false)));
					policyRule.setValue(options);

					// Add Policy Rule in Rules
					rules.add(policyRule);
				} else {
					// Policy Rule - Option
					options.add(new UserPolicyOption(ruleID, ruleOptionName, ruleOptionValue, (value
							.equalsIgnoreCase("true") ? true : false)));
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
			int id = rs.getInt("ID");
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
				menuLeaf.setId(id);
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
			UserCompany company = children.get(i);
			CompanyNode childNode = new CompanyNode(company.getId(), company.getCompanyName(),
					company.isPolicyInherited());
			getCompanyTree(childNode, companys);

			node.getChildren().add(childNode);
		}
	}

}
