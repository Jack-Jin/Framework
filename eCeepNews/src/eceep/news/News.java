package eceep.news;

import java.sql.SQLException;
import java.util.List;

import eceep.news.domain.NewsDetail;

public interface News {
	boolean initial(String jdbcDriver, String url, String userName, String password);
	
	List<NewsDetail> getNewsList() throws SQLException;
	
	boolean newNews(NewsDetail detail);
	
	boolean updateNews(NewsDetail newsDetail, int byUserID) throws SQLException;
}
