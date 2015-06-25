package eceep.web.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import eceep.customer.Customer;
import eceep.customer.impl.CustomerFactoryImpl;
import eceep.news.News;
import eceep.news.impl.NewsFactoryImpl;
import eceep.quotation.EnumIndustryType;
import eceep.quotation.EnumProductApplicationType;
import eceep.quotation.EnumProductType;
import eceep.quotation.Quotation;
import eceep.quotation.impl.QuotationFactoryImpl;
import eceep.user.User;
import eceep.user.impl.UserFactoryImpl;
import eceep.web.domain.JdbcConnectionStr;
import eceep.web.enumeration.Currency;

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
	
	private News news;

	private Quotation quotation;
	
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
			// New Customer
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

	public News getNews() {
		if(this.news == null){
			// New News
			this.news = NewsFactoryImpl.getInstance();
			
			this.news.initial(connWebBase.getJdbcDriver(), connWebBase.getJdbcURL(), connWebBase.getJdbcUserName(),
					connWebBase.getJdbcPassword());
		}
		
		return this.news;
	}
	
	public Quotation getQuotation() {
		if(this.quotation == null){
			this.quotation = QuotationFactoryImpl.getInstance();
			
			this.quotation.initial(connWebBase.getJdbcDriver(), connWebBase.getJdbcURL(), connWebBase.getJdbcUserName(),
					connWebBase.getJdbcPassword());
		}
		
		return this.quotation;
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
	
	public Map<Integer, String> getUnitSytemList(){
		Map<Integer, String> unitSystemList = new LinkedHashMap<Integer, String>();
		unitSystemList.put(0, "SI");
		unitSystemList.put(1, "TH");
		unitSystemList.put(2, "US");
		unitSystemList.put(3, "Practical");
		
		for(Entry<Integer, String> item :  unitSystemList.entrySet()){
			//item.getKey()
			//item.getValue()
		}
		
		return unitSystemList;
	}
	
	public Map<Integer, String> getCurrencyList(){
		Map<Integer, String> list = new LinkedHashMap<Integer, String>();
		
		for(Currency item : Currency.values()){
			list.put(item.getId(), item.getLabel());
		}
		
		return list;
	}

	public Map<Integer, String> getProductTypeList(){
		Map<Integer, String> list = new LinkedHashMap<Integer, String>();
		
		list.put(EnumProductType.CrossFlow.getId(), EnumProductType.CrossFlow.getLabel());
		list.put(EnumProductType.GasketedPHE.getId(), EnumProductType.GasketedPHE.getLabel());
		
		return list;
	}
	
	public Map<Integer, String> getProductApplicationTypeList(){
		Map<Integer, String> list = new LinkedHashMap<Integer, String>();
		
		list.put(EnumProductApplicationType.SinglePhase.getId(), EnumProductApplicationType.SinglePhase.getLabel());
		list.put(EnumProductApplicationType.SteamHeater.getId(), EnumProductApplicationType.SteamHeater.getLabel());
		
		return list;
	}
	
	public Map<Integer, String> getIndustryList(){
		Map<Integer, String> list = new LinkedHashMap<Integer, String>();
		
		list.put(EnumIndustryType.Food.getId(), EnumIndustryType.Food.getLabel());
		list.put(EnumIndustryType.HVACCommercial.getId(), EnumIndustryType.HVACCommercial.getLabel());
		list.put(EnumIndustryType.Industrial.getId(), EnumIndustryType.Industrial.getLabel());
		
		return list;
	}
	
	
}
