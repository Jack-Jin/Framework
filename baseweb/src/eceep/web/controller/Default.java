package eceep.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eceep.user.User;
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
		String language = request.getParameter("language");

		// Get session
		HttpSession session = request.getSession(true);

		// New local context(only at the first time.), get local web context
		// object
		WebContext webContext = WebContext.newContext(session);

		// Set Language
		Locale local = request.getLocale();
		for (Locale loc : Locale.getAvailableLocales()) {
			if ((loc.getLanguage() + "_" + loc.getCountry()).equalsIgnoreCase(language)) {
				local = loc;
				break;
			}
		}
		webContext.setLocale(local);

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
			request.setAttribute("logon_message", "Login failed.");
			// request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request,
			// response);

			String logonMsg = URLEncoder.encode("Login failed.", java.nio.charset.StandardCharsets.UTF_8.toString());
			response.sendRedirect("index.html?language=" + language + "&logonmessage=" + logonMsg);
			return;
		}

		// Page body
		request.getRequestDispatcher("/WEB-INF/main.jsp").include(request, response);
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
