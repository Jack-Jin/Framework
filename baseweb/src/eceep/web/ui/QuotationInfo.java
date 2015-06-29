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
import eceep.milestone.impl.StepDefault;
import eceep.quotation.EnumIndustryType;
import eceep.quotation.EnumProductApplicationType;
import eceep.quotation.EnumProductType;
import eceep.quotation.Product;
import eceep.quotation.Quotation;
import eceep.quotation.domain.QuotationHeaderDetail;
import eceep.quotation.domain.QuotationItemDetail;
import eceep.user.User;
import eceep.web.enumeration.Currency;
import eceep.web.enumeration.Steps;
import eceep.web.product.DemoProduct;
import eceep.web.repository.WebContext;
import eceep.web.repository.WebUtils;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String title_btn_NewItem = "New Item";
		final String title_btn_OpenItem = "Open";
		final String title_btn_DeleteItem = "Delete";

		// Action
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
		int loadQuotationID = -1;

		QuotationHeaderDetail quotationHeader = null; // Quotation Header
		List<CustomerContact> customerContacts = new ArrayList<CustomerContact>();
		int customerContactsSelectedID = -1;

		List<QuotationItemDetail> quotationItems = new ArrayList<QuotationItemDetail>();
		String quotationItemsCurrentID = "";

		// New quotation item default selection.
		QuotationItemDetail newQuotationItem = new QuotationItemDetail();
		newQuotationItem.setUnitID(context.getUnitSytemList().entrySet().iterator().next().getKey());
		newQuotationItem.setCurrencyID(Currency.CAD.getId());
		newQuotationItem.setProductType(EnumProductType.CrossFlow);
		newQuotationItem.setProductApplicationType(EnumProductApplicationType.SinglePhase);
		newQuotationItem.setIndustryType(EnumIndustryType.Industrial);

		Milestone<Step> milestone = null; // Milestone

		String message = "";
		// =============================================================================

		try {
			if (loadQuotationID < 0) {
				// New milestone
				milestone = MilestoneService.getInstance();
				milestone.addFirst(Steps.QuotationInfo.getStep());
				milestone.goTop();

				// New quotation
				quotation.newQuotation(QuotationHeaderDetail.class, milestone);
			}

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

			if (action.equalsIgnoreCase("New Item")) { // ------------------------- Action - New Item
				// Update quotation header.
				updateQuotationHeaderFromUI(request, quotationHeader);
				// Update quotation item.
				updateQuotationItemFromUI(request, quotation);
				// Milestone
				milestone = quotation.getMilestone();
				StepDefault step1 = (StepDefault) Steps.Step1.getStep(quotation.getQuotationItemsCurrentID());
				StepDefault step2 = (StepDefault) Steps.Step2.getStep(quotation.getQuotationItemsCurrentID());
				StepDefault step3 = (StepDefault) Steps.Step3.getStep(quotation.getQuotationItemsCurrentID());
				milestone.addChild(Steps.QuotationInfo.getStep(), step1).addChild(step1, step2).addChild(step2, step3);
				milestone.go(step1);
				
				// Redirect to next page
				response.sendRedirect(request.getContextPath() + milestone.getCurrentStep().getURI());
				return;
				
			} else if (action.equalsIgnoreCase("OpenItem")) { // --------------------- Action - Open Item
				quotationItemsCurrentID = "" + request.getParameter("quotationItemID");
				quotation.setQuotationItemsCurrentID(quotationItemsCurrentID);
				
				StepDefault step1 = (StepDefault) Steps.Step1.getStep(quotation.getQuotationItemsCurrentID());
				//milestone.next(step1);
				
				// Redirect to next page
				response.sendRedirect(request.getContextPath() + step1.getURI());
				return;
				
			} else if (action.equalsIgnoreCase("DeleteItem")) { // ------------------- Action - Delete Item
				String deleteQuotationItemID = "" + request.getParameter("quotationItemID");
				quotation.removeQuotationItem(deleteQuotationItemID);

				// Reload current page.
				response.sendRedirect(request.getContextPath() + "/QuotationInfo");
				return;
			}

			// Quotation items, current item id.
			quotationItems = quotation.getQuotationItems();
			quotationItemsCurrentID = quotation.getQuotationItemsCurrentID();

			// == Variables for UI. ====================================================
			request.setAttribute("quotationheader", quotationHeader);

			request.setAttribute("quotationitems", quotationItems);
			request.setAttribute("quotationitemscurrentID", quotationItemsCurrentID);

			request.setAttribute("milestone", milestone);

			request.setAttribute("newquotationitem", newQuotationItem);

			request.setAttribute("customercontacts", customerContacts);
			request.setAttribute("customercontactsselectedID", customerContactsSelectedID);

			request.setAttribute("unitsystemlist", context.getUnitSytemList().entrySet());
			request.setAttribute("currencylist", context.getCurrencyList().entrySet());
			request.setAttribute("producttypelist", context.getProductTypeList().entrySet());
			request.setAttribute("productapplicationtypelist", context.getProductApplicationTypeList().entrySet());
			request.setAttribute("industrylist", context.getIndustryList().entrySet());

			request.setAttribute("title_btn_newitem", title_btn_NewItem);
			request.setAttribute("title_btn_openitem", title_btn_OpenItem);
			request.setAttribute("title_btn_deleteitem", title_btn_DeleteItem);

			request.setAttribute("message", message);
			// =========================================================================

		} catch (SQLException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.getRequestDispatcher("/WEB-INF/quotation/quotationinfo.jsp").forward(request, response);
		return;
	}

	private void updateQuotationHeaderFromUI(HttpServletRequest request, QuotationHeaderDetail quotationHeader) {
		// Customer Info
		quotationHeader.setCustomerID(Integer.parseInt(request.getParameter("customerID")));
		quotationHeader.setCustomerName(request.getParameter("customerName"));
		quotationHeader.setCustomerAddress(request.getParameter("customerAddress"));
		quotationHeader.setCustomerPhone(request.getParameter("customerPhone"));
		quotationHeader.setCustomerFax(request.getParameter("customerFax"));
		// Project Info
		quotationHeader.setQuotationProjectName(request.getParameter("quotationProjectName"));
		quotationHeader.setQuotationNo(request.getParameter("quotationNo"));
		// Project Note
		quotationHeader.setQuotationNote(request.getParameter("quotationNote"));
	}

	private void updateQuotationItemFromUI(HttpServletRequest request, Quotation quotation)
			throws InstantiationException, IllegalAccessException {
		// Quotation Item
		Product product = new DemoProduct();
		quotation.newQuotationItem(QuotationItemDetail.class, product);
		QuotationItemDetail quotationItem = quotation.getQuotationItem();
		//    item name
		quotationItem.setItemName(request.getParameter("itemName"));
		//    item revision
		quotationItem.setItemRevision(request.getParameter("itemRevision"));
		//    unit id
		quotationItem.setUnitID(Integer.parseInt(request.getParameter("unitID")));
		//    currency id
		quotationItem.setCurrencyID(Integer.parseInt(request.getParameter("currencyID")));
		//    product type
		EnumProductType productType = WebUtils.getEnumId(EnumProductType.class,
				Integer.parseInt(request.getParameter("productType")));
		quotationItem.setProductType(productType);
		//    application type
		EnumProductApplicationType productApplicationType = WebUtils.getEnumId(EnumProductApplicationType.class,
				Integer.parseInt(request.getParameter("productApplicationType")));
		quotationItem.setProductApplicationType(productApplicationType);
		//    industry type
		EnumIndustryType industryType = WebUtils.getEnumId(EnumIndustryType.class,
				Integer.parseInt(request.getParameter("industryType")));
		quotationItem.setIndustryType(industryType);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doGet(request, response);
	}

}
