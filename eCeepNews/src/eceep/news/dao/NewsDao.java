package eceep.news.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eceep.mysql.JdbcUtils;
import eceep.news.domain.NewsDetail;

public class NewsDao {
	public boolean initial(String jdbcDriver, String url, String userName, String password) {
		JdbcUtils config = new JdbcUtils();
		config.setJdbcDriver(jdbcDriver);
		config.setUrl(url);
		config.setUserName(userName);
		config.setPassword(password);

		JdbcUtils.initial(config);

		return true;
	}

	public List<NewsDetail> getNewsList() throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<NewsDetail> result = new ArrayList<NewsDetail>();
		try {
			conn = JdbcUtils.getConnection();

			String sql = "SELECT ID,Title,Content,Active,CreatedByID,CreatedByName,CreatedTime,ModifiedByID,ModifiedByName,ModifiedTime";
			sql += " FROM News ORDER BY ModifiedTime DESC";
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				NewsDetail detail = JdbcUtils.ResultSet2Object(rs, NewsDetail.class);

				result.add(detail);
			}

		} catch (InstantiationException | IllegalAccessException e) {
			result = new ArrayList<NewsDetail>();
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return result;
	}

	public boolean updateNews(NewsDetail newsDetail, int byUserID) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		boolean result = false;
		try {
			conn = JdbcUtils.getConnection();

			String sql = "UPDATE News SET Title=?, Content=?, Active=?";
			sql += ",ModifiedByID=?,ModifiedByName=(SELECT UserName FROM Users WHERE ID=?), ModifiedTime=NOW()";
			sql += " WHERE ID=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, newsDetail.getTitle());
			ps.setString(2, newsDetail.getContent());
			ps.setBoolean(3, newsDetail.isActive());
			ps.setInt(4, byUserID);
			ps.setInt(5, byUserID);
			ps.setInt(6, newsDetail.getId());

			int count = ps.executeUpdate();

			if (count > 0)
				result = true;
		} finally {
			JdbcUtils.free(rs, ps, conn);
		}

		return result;
	}

}
