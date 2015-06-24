package eceep.web.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
import eceep.quotation.Quotation;
import eceep.quotation.domain.QuotationHeaderDetail;
import eceep.user.User;
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
		QuotationHeaderDetail quotationHeader = null;		// Quotation Header
		List<CustomerContact> customerContacts = new ArrayList<CustomerContact>();
		int customerContactsSelectedID = -1;
		Milestone<Step> milestone = null; // Milestone
		
		// =============================================================================
		
		try {
			// New quotation
			milestone = MilestoneService.getInstance();
			quotationHeader = new QuotationHeaderDetail();
			quotation.newQuotation(quotationHeader, milestone);
			
			quotationHeader = quotation.getQuotationHeader();
			milestone = quotation.getMilestone();

			// Customer
			if(quotationHeader!=null && quotationHeader.getCustomerID()>0) {
				if(customer.loadCustomer(quotationHeader.getCustomerID())) {
					customerContacts = customer.getCustomerDetail().getCustomerContacts();
					customerContactsSelectedID = customer.getCustomerDetail().getCustomerContactID();
				}
			}
			
			// == Variables for UI. ====================================================
			request.setAttribute("quotationheader", quotationHeader);
			request.setAttribute("customercontacts", customerContacts);
			request.setAttribute("customercontactsselectedID", customerContactsSelectedID);
			request.setAttribute("milestone", milestone);
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
