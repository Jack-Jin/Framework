package eceep.news.impl;

import java.sql.SQLException;
import java.util.List;

import eceep.news.News;
import eceep.news.dao.NewsDao;
import eceep.news.domain.NewsDetail;

public class NewsService implements News {
	private NewsDao newsDao;
	
	public NewsService() {
		this.newsDao = new NewsDao();
	}
	
	@Override
	public boolean initial(String jdbcDriver, String url, String userName, String password) {
		return this.newsDao.initial(jdbcDriver, url, userName, password);
	}
	
	@Override
	public List<NewsDetail> getNewsList() throws SQLException {
		return this.newsDao.getNewsList();
	}

	@Override
	public boolean newNews(NewsDetail detail) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateNews(NewsDetail detail) {
		// TODO Auto-generated method stub
		return false;
	}

}
