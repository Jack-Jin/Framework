package eceep.web.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import eceep.customer.Customer;
import eceep.customer.impl.CustomerFactoryImpl;
import eceep.user.User;
import eceep.user.impl.UserFactoryImpl;
import eceep.web.domain.JdbcConnectionStr;

public class WebContext {
	public static WebContext newContext(HttpSession session) {
		if (session != null) {
			session.setAttribute("eCeepWebFramework_Context", new WebContext());
		}

		return getContext(session);
	}

	public static WebContext getContext(HttpSession session) {
		if (session == null)
			return null;

		return (WebContext) session.getAttribute("eCeepWebFramework_Context");
	}

	/* Fields */
	/* --------------------------- */
	private JdbcConnectionStr connWebBase;
	private Locale locale;

	private User user;
	private Customer customer;

	/* Methods */
	/* --------------------------- */
	public boolean getPolicy_IsCustomersByUserID(){
		return false;
	}
	
	public User getUser() {
		if (this.user == null) {
			// New instance of user.
			this.user = UserFactoryImpl.getInstance();

			this.user.initial(connWebBase.getJdbcDriver(), connWebBase.getJdbcURL(), connWebBase.getJdbcUserName(),
					connWebBase.getJdbcPassword());
		}

		return this.user;
	}

	public Customer getCustomer() {
		if (this.customer == null) {
			this.customer = CustomerFactoryImpl.getInstance();

			this.customer.initial(connWebBase.getJdbcDriver(), connWebBase.getJdbcURL(), connWebBase.getJdbcUserName(),
					connWebBase.getJdbcPassword());
			
			//Set policy - customer list by user ID.
			int userID = -1;
			if(this.getPolicy_IsCustomersByUserID() && this.getUser().isLogin()){
				userID = this.getUser().getUserDetail().getId();
			}
			this.customer.setPolicy_CustomersByUserID(userID);
		}

		return this.customer;
	}

	public JdbcConnectionStr getConnWebBase() {
		return connWebBase;
	}

	public void setConnWebBase(InputStream resourceAsStream) {
		try {
			// InputStream resourceAsStream =
			// WebContext.class.getResourceAsStream("/WEB-INF/jdbc.properties");
			// InputStream resourceAsStream =
			// WebContext.class.getClassLoader().getResourceAsStream("eceep/web/repository/jdbc.properties");
			// InputStream resourceAsStream =
			// WebContext.class.getClassLoader().getResourceAsStream("WEB-INF/jdbc.properties");

			// ClassLoader classLoader =
			// Thread.currentThread().getContextClassLoader();
			// InputStream resourceAsStream =
			// classLoader.getResourceAsStream("/jdbc.properties");

			// Load jdbc config (jdbc.properties) for web base.
			Properties prop = new Properties();
			prop.load(resourceAsStream);

			String jdbcDriver = prop.getProperty("Web_DriverClassName");
			String jdbcURL = prop.getProperty("Web_URL");
			String jdbcUserName = prop.getProperty("Web_UserName");
			String jdbcPassword = prop.getProperty("Web_Password");

			this.connWebBase = new JdbcConnectionStr(jdbcDriver, jdbcURL, jdbcUserName, jdbcPassword);

		} catch (IOException e) {
			throw new RuntimeException("jdbc properties read error.");
		}
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Locale getLocale() {
		return this.locale;
	}
}
