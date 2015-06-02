package eceep.web.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eceep.customer.domain.CustomerDetail;
import eceep.news.News;
import eceep.news.domain.NewsDetail;
import eceep.user.User;
import eceep.web.repository.WebContext;
import eceep.web.repository.WebUtils;

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

		// Object - user
		User user = context.getUser();

		// Object - news
		News news = context.getNews();

		int tabIndex = 0;
		int selectedNewsID = -1;
		String message = "";

		// Selected news detail
		NewsDetail newsDetail = null;

		try {
			List<NewsDetail> newsList = news.getNewsList();

			// Actions
			if (action.equalsIgnoreCase("Selected News")) { // ------------------------------ Action - Selected News
				
				selectedNewsID = Integer.parseInt(request.getParameter("selectedNewsID"));
				
			} else if (action.equalsIgnoreCase("News Update")) { // ------------------------- Action - News Update
			
				newsDetail = WebUtils.request2Bean(request, NewsDetail.class);
				
				news.updateNews(newsDetail, user.getUserDetail().getId());
				
				newsList = news.getNewsList();
				tabIndex = 1;
				selectedNewsID = newsDetail.getId();
				message = "News is updated.";

			}

			// News List
			if (selectedNewsID < 0 && newsList != null && newsList.size() > 0) {
				selectedNewsID = newsList.get(0).getId();
			}

			// News Detail
			int tmpSelectedNewsID = selectedNewsID;
			List<NewsDetail> tmpNewsList = newsList.stream().filter(A -> A.getId() == tmpSelectedNewsID)
					.collect(Collectors.toList());
			if (tmpNewsList != null && tmpNewsList.size() > 0) {
				newsDetail = tmpNewsList.get(0);
			}

			request.setAttribute("tabindex", tabIndex);
			request.setAttribute("newslist", newsList);
			request.setAttribute("selectednewsID", selectedNewsID);
			request.setAttribute("newsdetail", newsDetail);
			request.setAttribute("message", message);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.getRequestDispatcher("/WEB-INF/news/postnews.jsp").forward(request, response);
		return;

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doGet(request, response);
	}

}
