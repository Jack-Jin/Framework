package eceep.web.ui;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eceep.user.service.User;
import eceep.web.repository.WebContext;

/**
 * Servlet implementation class Default
 */
@WebServlet("/Default")
public class Default extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Default() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("username");
		String pwd = request.getParameter("password");

		// Get session
		HttpSession session = request.getSession(true);
		
		// New local context(only at the first time.), get local web context object
		WebContext webContext = WebContext.newContext(session);
		
		// Get user instance.
		InputStream resourceAsStream = this.getServletContext().getResourceAsStream("/WEB-INF/jdbc.properties");
		webContext.setConnWebBase(resourceAsStream);
		
		User user = webContext.getUser();
		try {
			user.logon(userName, pwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (!user.isLogin()) {
			response.sendRedirect("index.html");
			return;
		}

		// Page header
		request.getRequestDispatcher("/base/masterHeader.jsp").include(request, response);

		// Page body
		request.getRequestDispatcher("/WEB-INF/main.jsp").include(request, response);

		// Page footer
		request.getRequestDispatcher("/base/masterFooter.jsp").include(request, response);
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
