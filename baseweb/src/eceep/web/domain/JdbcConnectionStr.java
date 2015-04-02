package eceep.web.domain;

public class JdbcConnectionStr {
	private String jdbcDriver;
	private String jdbcURL;
	private String jdbcUserName;
	private String jdbcPassword;

	public JdbcConnectionStr(String jdbcDriver, String jdbcURL, String jdbcUserName, String jdbcPassword) {
		super();
		this.jdbcDriver = jdbcDriver;
		this.jdbcURL = jdbcURL;
		this.jdbcUserName = jdbcUserName;
		this.jdbcPassword = jdbcPassword;
	}

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public String getJdbcURL() {
		return jdbcURL;
	}

	public String getJdbcUserName() {
		return jdbcUserName;
	}

	public String getJdbcPassword() {
		return jdbcPassword;
	}

}
