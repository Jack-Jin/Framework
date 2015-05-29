package eceep.web.ui;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eceep.user.User;
import eceep.user.domain.UserCompany;
import eceep.user.domain.UserDetail;
import eceep.web.repository.WebContext;
import eceep.web.repository.WebUtils;

/**
 * Servlet implementation class myprofile
 */
@WebServlet("/MyProfile")
public class MyProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
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
			UserCompany userCompany = user.getUserCompany();
			UserDetail userDetail = user.getUserDetail();
			int tabIndex = 0;
			
			// Result Message
			String resultMessage = "";

			if (action.equalsIgnoreCase("User Update")) {

				userDetail = WebUtils.request2Bean(request, UserDetail.class);
				user.updateUserInfo(userDetail, userCompany.getId());
				
				user.refreshFromDB();

				resultMessage = "User information updated.";

			} else if (action.equalsIgnoreCase("Password Change")) {

				int userID = Integer.parseInt(request.getParameter("userID"));

				String newPassword = request.getParameter("newPassword");
				String newPasswordConfirm = request.getParameter("newPasswordConfirm");

				resultMessage = "Update password failed.";
				if (newPassword.equals(newPasswordConfirm)) {
					boolean change = user.changePassword(userID, false, "", newPassword);
					if (change)
						resultMessage = "New password is updated.";
				}

				userDetail = user.getUserDetail(userID);

			}
			
			request.setAttribute("tabindex", tabIndex);
			request.setAttribute("usercompany", userCompany);
			request.setAttribute("userdetail", userDetail);
			request.setAttribute("message", resultMessage);

		} catch (SQLException e) {
			e.printStackTrace();

			return;
		}

		request.getRequestDispatcher("/WEB-INF/profile/myprofile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doGet(request, response);
	}
}
