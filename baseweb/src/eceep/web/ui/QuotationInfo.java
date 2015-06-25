package eceep.web.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eceep.customer.Customer;
import eceep.customer.domain.CustomerContact;
import eceep.milestone.Milestone;
import eceep.milestone.Step;
import eceep.milestone.impl.MilestoneService;
import eceep.quotation.EnumIndustryType;
import eceep.quotation.EnumProductApplicationType;
import eceep.quotation.EnumProductType;
import eceep.quotation.Quotation;
import eceep.quotation.domain.QuotationHeaderDetail;
import eceep.quotation.domain.QuotationItemDetail;
import eceep.user.User;
import eceep.web.enumeration.Currency;
import eceep.web.repository.WebContext;

/**
 * Servlet implementation class QuotationInfo
 */
@WebServlet("/QuotationInfo")
public class QuotationInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QuotationInfo() {
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

		// Get Customer
		Customer customer = context.getCustomer();

		// Get Quotation
		Quotation quotation = context.getQuotation();

		// == Variables for page. ======================================================
		QuotationHeaderDetail quotationHeader = null; // Quotation Header
		List<CustomerContact> customerContacts = new ArrayList<CustomerContact>();
		int customerContactsSelectedID = -1;

		List<QuotationItemDetail> quotationItems = new ArrayList<QuotationItemDetail>();
		String quotationItemsCurrentID = "";

		QuotationItemDetail newQuotationItem = new QuotationItemDetail();
		// New quotation default selection.
		newQuotationItem.setUnitID(context.getUnitSytemList().entrySet().iterator().next().getKey());
		newQuotationItem.setCurrencyID(Currency.CAD.getId());
		newQuotationItem.setProductType(EnumProductType.CrossFlow);
		newQuotationItem.setProductApplicationType(EnumProductApplicationType.SinglePhase);
		newQuotationItem.setIndustryType(EnumIndustryType.Industrial);
		
		Milestone<Step> milestone = null; // Milestone

		// =============================================================================

		try {
			// New quotation
			milestone = MilestoneService.getInstance();
			quotationHeader = new QuotationHeaderDetail();
			quotation.newQuotation(quotationHeader, milestone);

			// Assign quotation header, quotation items, items current id, milestone
			quotationHeader = quotation.getQuotationHeader();
			quotationItems = quotation.getQuotationItems();
			quotationItemsCurrentID = quotation.getQuotationItemsCurrentID();
			milestone = quotation.getMilestone();

			// Load Customer
			if (quotationHeader != null && quotationHeader.getCustomerID() > 0) {
				if (customer.loadCustomer(quotationHeader.getCustomerID())) {
					customerContacts = customer.getCustomerDetail().getCustomerContacts();
				}
				customer.getCustomerDetail().setCustomerContactID(quotationHeader.getContactID());
				customerContactsSelectedID = quotationHeader.getContactID();
			}

			// == Variables for UI. ====================================================
			request.setAttribute("quotationheader", quotationHeader);
			
			request.setAttribute("customercontacts", customerContacts);
			request.setAttribute("customercontactsselectedID", customerContactsSelectedID);
			
			request.setAttribute("quotationitems", quotationItems);
			request.setAttribute("quotationitemscurrentID", quotationItemsCurrentID);
			
			request.setAttribute("milestone", milestone);
			
			request.setAttribute("newquotationitem", newQuotationItem);
			
			request.setAttribute("unitsystemlist", context.getUnitSytemList().entrySet());
			request.setAttribute("currencylist", context.getCurrencyList().entrySet());
			request.setAttribute("producttypelist", context.getProductTypeList().entrySet());
			request.setAttribute("productapplicationtypelist", context.getProductApplicationTypeList().entrySet());
			request.setAttribute("industrylist", context.getIndustryList().entrySet());
			
			// =========================================================================

		} catch (SQLException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.getRequestDispatcher("/WEB-INF/quotation/quotationinfo.jsp").forward(request, response);
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
