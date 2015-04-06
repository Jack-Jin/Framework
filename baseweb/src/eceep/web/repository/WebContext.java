package eceep.web.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import eceep.user.service.User;
import eceep.user.service.impl.UserFactoryImpl;
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

	/* Methods */
	/* --------------------------- */
	public User getUser() {
		if (user == null) {
			// New instance of user.
			user = UserFactoryImpl.getInstance();
			
			user.initial(connWebBase.getJdbcDriver(), connWebBase.getJdbcURL(), connWebBase.getJdbcUserName(),
					connWebBase.getJdbcPassword());
		}

		return this.user;
	}

	public JdbcConnectionStr getConnWebBase() {
		return connWebBase;
	}

	public void setConnWebBase(InputStream resourceAsStream) {
		try {
			// InputStream resourceAsStream =
			// WebContext.class.getResourceAsStream("/WEB-INF/jdbc.properties");
			//InputStream resourceAsStream = WebContext.class.getClassLoader().getResourceAsStream("eceep/web/repository/jdbc.properties");
//			InputStream resourceAsStream = WebContext.class.getClassLoader().getResourceAsStream("WEB-INF/jdbc.properties");

			//ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			//InputStream resourceAsStream = classLoader.getResourceAsStream("/jdbc.properties");
			
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
	
	public void setLocale(Locale locale){
		this.locale = locale;
	}
	
	public Locale getLocale() {
		return this.locale;
	}
}
