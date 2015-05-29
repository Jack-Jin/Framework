package eceep.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eceep.news.News;
import eceep.news.domain.NewsDetail;
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

		// Read jdbc connection file
		InputStream resourceAsStream = this.getServletContext().getResourceAsStream("/WEB-INF/jdbc.properties");
		webContext.setConnWebBase(resourceAsStream);

		// Get user instance.
		User user = webContext.getUser();

		try {
			// Log on.
			user.logon(userName, pwd);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (!user.isLogin()) {
			request.setAttribute("logon_message", "Login failed.");

			String logonMsg = URLEncoder.encode("Login failed.", java.nio.charset.StandardCharsets.UTF_8.toString());
			response.sendRedirect("index.html?language=" + language + "&logonmessage=" + logonMsg);

			return;
		}

		// Get news
		News news = webContext.getNews();
		try {
			List<NewsDetail> newsList = news.getNewsList();
			
			request.setAttribute("newslist", newsList);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Page body
		request.getRequestDispatcher("/WEB-INF/main.jsp").include(request, response);
		return;
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
