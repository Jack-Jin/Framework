package eceep.web.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eceep.user.domain.CompanyNode;
import eceep.user.domain.UserCompany;
import eceep.user.domain.UserDetail;
import eceep.user.domain.UserMenu;
import eceep.user.domain.UserPolicy;
import eceep.user.domain.UserPolicyOption;
import eceep.user.domain.UserPolicyRule;
import eceep.user.service.User;
import eceep.web.repository.WebContext;
import eceep.web.repository.WebUtils;

/**
 * Servlet implementation class UserCompanyManagement
 */
@WebServlet("/UserCompanyManagement")
public class UserCompanyManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String caption_Button_PolicyUpdate = "Update";
	private String caption_Button_PolicyRemove = "Remove Private Policy";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserCompanyManagement() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		action = (action == null) ? "" : action;

		// Get session
		HttpSession session = request.getSession();

		// Get this context
		WebContext context = WebContext.getContext(session);

		// Get User
		User user = context.getUser();

		try {
			// Selected Company ID
			String selectedCompanyID = "";
			// Selected company view.
			boolean companySelected = true;
			// Selected User Detail
			UserDetail userDetail = null;
			// Result Message
			String resultMessage = "";

			// Action: "Selected Company", "Company Update"
			if (action.equalsIgnoreCase("Selected Company")) {

				selectedCompanyID = request.getParameter("companyID");
				companySelected = true;

			} else if (action.equalsIgnoreCase("Company Update")) {

				UserCompany company = WebUtils.request2Bean(request, UserCompany.class);
				user.updateCompanyInfo(company);

				selectedCompanyID = company.getId() + "";
				companySelected = true;
				resultMessage = "Company information updated.";

			} else if (action.equalsIgnoreCase("Selected User")) {

				int selectedUserID = Integer.parseInt(request.getParameter("userID"));
				userDetail = user.getUserDetail(selectedUserID);

				selectedCompanyID = request.getParameter("companyID");
				companySelected = false;

			} else if (action.equalsIgnoreCase("Add Company") || action.equalsIgnoreCase("Add Child Company")) {

				boolean nearby = action.equalsIgnoreCase("Add Company");

				int nearCompanyID = Integer.parseInt(request.getParameter("companyID"));
				int newCompanyID = user.AddNewCompany(nearby, nearCompanyID);

				selectedCompanyID = "" + newCompanyID;
				companySelected = true;
				resultMessage = "This is new company.";

			} else if (action.equalsIgnoreCase("Delete Company")) {

				int deleteCompanyID = Integer.parseInt(request.getParameter("companyID"));

				user.RemoveCompany(deleteCompanyID, user.getUserDetail().getId());

				selectedCompanyID = "";
				companySelected = true;

			} else if (action.equalsIgnoreCase("User Update")) {

				selectedCompanyID = request.getParameter("companyID");

				userDetail = WebUtils.request2Bean(request, UserDetail.class);
				user.updateUserInfo(userDetail, Integer.parseInt(selectedCompanyID));

				companySelected = false;
				resultMessage = "User information updated.";

			} else if (action.equalsIgnoreCase("Add User")) {

				selectedCompanyID = request.getParameter("companyID");

				int newUserID = user.AddNewUser(Integer.parseInt(selectedCompanyID), user.getUserDetail().getId(), user
						.getUserDetail().getUserName());

				companySelected = false;
				userDetail = user.getUserDetail(newUserID);
				resultMessage = "New user is added.";

			} else if (action.equalsIgnoreCase("Delete User")) {

				int deletedUserID = Integer.parseInt(request.getParameter("userID"));
				selectedCompanyID = request.getParameter("companyID");

				user.RemoveUser(deletedUserID, user.getUserDetail().getId(), user.getUserDetail().getUserName());

				companySelected = true;
				resultMessage = "User is removed.";

			} else if (action.equalsIgnoreCase("Company Policy Update")
					|| action.equalsIgnoreCase("User Policy Update")) {

				companySelected = action.equalsIgnoreCase("Company Policy Update");
				int policyID = Integer.parseInt(request.getParameter("policyID"));
				selectedCompanyID = request.getParameter("companyID");

				int selectedUserID = -1;
				if (!companySelected) {
					selectedUserID = Integer.parseInt(request.getParameter("userID"));

					userDetail = user.getUserDetail(selectedUserID);
				}

				if (request.getParameter("btnUpdate").equals(caption_Button_PolicyUpdate)) {

					updatePolicy(user, request, companySelected, Integer.parseInt(selectedCompanyID), selectedUserID);

					resultMessage = "Policy updated.";

				} else {

					int id = companySelected ? Integer.parseInt(selectedCompanyID) : selectedUserID;

					user.removePolicy(companySelected, id);

					resultMessage = "Private policy removed.";

				}
			}

			// Get All of Company tree.
			CompanyNode allOfCompanys = user.getAllOfCompanys();

			// If no company ID, set current company ID is the first one of
			// company tree.
			if (selectedCompanyID == null || selectedCompanyID.isEmpty())
				selectedCompanyID = allOfCompanys.getChildren().get(2).getId() + "";

			// Selected Company Info.
			UserCompany userCompany = user.getUserCompany(Integer.parseInt(selectedCompanyID));

			// Determine selected company is leaf or not.
			boolean isLeafCompany = user.IsLeafCompany(allOfCompanys, userCompany.getId());

			// User List of Current Company
			List<UserDetail> users = user.getUsersByCompanyID(Integer.parseInt(selectedCompanyID));

			// Selected company or user Policy.
			Object[] oPolicy = user.getPolicy(companySelected,
					(companySelected ? userCompany.getId() : userDetail.getId()));

			// Selected policy
			UserPolicy userPolicy = (UserPolicy) oPolicy[0];

			List<UserPolicyRule<Boolean>> userPolicyCheck = userPolicy.getRules().stream()
					.filter(A -> A.getType() == Boolean.class).collect(Collectors.toList());
			Class<?> userPolicyOptionListType = (new ArrayList<UserPolicyOption>()).getClass();
			List<UserPolicyRule<List>> userPolicyOption = userPolicy.getRules().stream()
					.filter(A -> A.getType() == userPolicyOptionListType).collect(Collectors.toList());
			List<UserPolicyRule<String>> userPolicyValue = userPolicy.getRules().stream()
					.filter(A -> A.getType() == String.class).collect(Collectors.toList());

			// Selected menu
			UserMenu userMenu = (UserMenu) oPolicy[1];

			// Set attributes: node, usercompany, companyselected, users
			request.setAttribute("companyselected", companySelected);

			request.setAttribute("node", allOfCompanys);
			request.setAttribute("usercompany", userCompany);
			request.setAttribute("isLeafCompany", isLeafCompany);

			request.setAttribute("users", users);
			request.setAttribute("userdetail", userDetail);

			request.setAttribute("userpolicy", userPolicy);
			request.setAttribute("userpolicycheck", userPolicyCheck);
			request.setAttribute("userpolicyoption", userPolicyOption);
			request.setAttribute("userpolicyvalue", userPolicyValue);
			request.setAttribute("usermenu", userMenu);

			request.setAttribute("resultmessage", resultMessage);

			request.setAttribute("caption_Button_PolicyUpdate", caption_Button_PolicyUpdate);
			request.setAttribute("caption_Button_PolicyRemove", caption_Button_PolicyRemove);

		} catch (SQLException e) {
			e.printStackTrace();

			return;
		}

		request.getRequestDispatcher("/WEB-INF/useradmin/admin.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doGet(request, response);
	}

	private void updatePolicy(User user, HttpServletRequest request, boolean companySelected, int selectedCompanyID,
			int selectedUserID) throws SQLException {
		Map<Integer, Boolean> menus = new HashMap<Integer, Boolean>();
		Map<Integer, String> rules = new HashMap<Integer, String>();

		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();

			if (paramName.isEmpty())
				continue;

			if (paramName.startsWith("menus-")) {
				int key = Integer.parseInt(paramName.substring(6));
				Boolean value = request.getParameter("menus-" + key).equals("on") ? true : false;

				menus.put(key, value);
			} else if (paramName.startsWith("policy-")) {
				int key = Integer.parseInt(paramName.substring(7));
				String value = request.getParameter("policy-" + key);

				rules.put(key, value);
			}
		}

		user.updatePolicy(menus, rules, companySelected, (companySelected ? selectedCompanyID : selectedUserID));
	}

}
