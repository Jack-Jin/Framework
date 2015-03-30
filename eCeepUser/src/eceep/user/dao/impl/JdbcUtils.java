package eceep.user.dao.impl;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

public class JdbcUtils {
	private static DataSource dataSource = null;

	public static void initial(JdbcUtils config) {
		try {
			if (dataSource == null) {
				Class.forName(config.getJdbcDriver());

				Properties prop = new Properties();
				prop.put("driverClassName", config.getJdbcDriver());
				prop.put("url", config.getUrl());
				prop.put("username", config.getUserName());
				prop.put("password", config.getPassword());

				// Initial Connection Size
				prop.put("initialSize", "10");

				// Maximum Connection Size
				prop.put("maxTotal", "50");

				// Maximum not using Connection Size
				prop.put("maxIdle", "20");

				// Minimum not using Connection Size
				prop.put("minIdle", "5");

				// Waiting time, millimonsecond
				prop.put("maxWaitMillis", "60000");

				//
				// prop.put("defaultAutoCommit", "true");

				//
				// prop.put("defaultReadOnly", "");

				//
				// prop.put("defaultTransactionIsolation", "true");

				dataSource = BasicDataSourceFactory.createDataSource(prop);

			}

		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static DataSource getDataSource() {
		return dataSource;
	}

	public static Connection getConnection() throws SQLException {
		return (dataSource == null) ? null : dataSource.getConnection();
	}

	public static void free(ResultSet rs, Statement st, Connection conn) {
		// Close ResultSet
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close Statement
			try {
				if (st != null)
					st.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// Close Connection
				try {
					if (conn != null)
						conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static <T> T ResultSet2Object(ResultSet rs, Class<T> clazz) throws InstantiationException,
			IllegalAccessException {
		T object = clazz.newInstance();

		Method[] methods = clazz.getDeclaredMethods();

		for (Method md : methods) {
			// if "set" method
			if (md.getName().substring(0, 3).equalsIgnoreCase("set")) {
				String colName = md.getName().substring(3);

				// If column name not match, skip.
				try {
					md.invoke(object, rs.getObject(colName));
				} catch (Exception e) {
				}
			}
		}

		return object;
	}

	private String jdbcDriver = "com.mysql.jdbc.Driver";
	private String url;
	private String userName;
	private String password;

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
