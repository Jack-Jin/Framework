package eceep.web.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
import eceep.user.service.User;
import eceep.web.repository.WebContext;
import eceep.web.repository.WebUtils;

/**
 * Servlet implementation class UserCompanyManagement
 */
@WebServlet("/UserCompanyManagement")
public class UserCompanyManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
			String paraCompanyID = "";
			// Selected company view.
			boolean companySelected = true;
			// Selected User Detail
			UserDetail userDetail = null;
			// Result Message
			String resultMessage = "";

			// Action: "Selected Company", "Company Update"
			if (action.equalsIgnoreCase("Selected Company")) {
				paraCompanyID = request.getParameter("companyID");
				companySelected = true;
			} else if (action.equalsIgnoreCase("Company Update")) {
				UserCompany company = WebUtils.request2Bean(request, UserCompany.class);
				user.updateCompanyInfo(company);

				paraCompanyID = company.getId() + "";
				companySelected = true;
				resultMessage = "Company information updated.";
			} else if (action.equalsIgnoreCase("Selected User")) {
				int paraUserID = Integer.parseInt(request.getParameter("userID"));
				userDetail = user.getUserDetail(paraUserID);
						
				paraCompanyID = request.getParameter("companyID");
				companySelected = false;
			} else if(action.equalsIgnoreCase("User Update")) {
				paraCompanyID = request.getParameter("companyID");
				
				userDetail = WebUtils.request2Bean(request, UserDetail.class);
				user.updateUserInfo(userDetail, Integer.parseInt(paraCompanyID));

				companySelected = false;
				resultMessage = "User information updated.";
			}

			// Get All of Company tree.
			CompanyNode allOfCompanys = user.getAllOfCompanys();

			// If no company ID, set current company ID is the first one of
			// company tree.
			if (paraCompanyID == null || paraCompanyID.isEmpty())
				paraCompanyID = allOfCompanys.getChildren().get(0).getId() + "";

			// Selected Company Info.
			UserCompany userCompany = user.getUserCompany(Integer.parseInt(paraCompanyID));

			// User List of Current Company
			List<UserDetail> users = user.getUsersByCompanyID(Integer.parseInt(paraCompanyID));
			
			// Selected company or user Policy.
			Object[] oPolicy = user.getPolicy(companySelected, (companySelected? userCompany.getId(): userDetail.getId()));
			UserPolicy userPolicy = (UserPolicy)oPolicy[0];
			UserMenu userMenu = (UserMenu)oPolicy[1];

			// Set attributes: node, usercompany, companyselected, users
			request.setAttribute("node", allOfCompanys);
			request.setAttribute("usercompany", userCompany);
			request.setAttribute("companyselected", companySelected);
			request.setAttribute("users", users);
			request.setAttribute("userdetail", userDetail);
			request.setAttribute("resultmessage", resultMessage);

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

}
