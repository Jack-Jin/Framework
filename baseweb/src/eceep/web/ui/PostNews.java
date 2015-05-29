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

import eceep.news.News;
import eceep.news.domain.NewsDetail;
import eceep.user.User;
import eceep.web.repository.WebContext;

/**
 * Servlet implementation class PostNews
 */
@WebServlet("/PostNews")
public class PostNews extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostNews() {
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

		// Get context
		WebContext context = WebContext.getContext(session);

		// Get User
		User user = context.getUser();

		try {
			News news = context.getNews();
			
			List<NewsDetail> newsList = news.getNewsList();
			
			
			
			
			
			request.setAttribute("newslist", newsList);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.getRequestDispatcher("/WEB-INF/news/postnews.jsp").forward(request, response);	
		return;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
