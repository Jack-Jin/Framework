package eceep.web.product.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eceep.customer.Customer;
import eceep.milestone.Milestone;
import eceep.milestone.Step;
import eceep.milestone.impl.StepDefault;
import eceep.quotation.Quotation;
import eceep.user.User;
import eceep.web.enumeration.Steps;
import eceep.web.repository.WebContext;

/**
 * Servlet implementation class Step1
 */
@WebServlet("/Step1")
public class Step1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Step1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		// Get milestone
		Milestone<Step> milestone = quotation.getMilestone();
		Step currentStep = Steps.Step1.getStep(quotation.getQuotationItemsCurrentID());
		milestone.go(currentStep);

		// Uri
		String uriPrevious = request.getContextPath() + milestone.getTheFirstStep().getURI();
		String uriNext = request.getContextPath() + Steps.Step2.getStep(quotation.getQuotationItemsCurrentID()).getURI();
		
		request.setAttribute("uriprevious", uriPrevious);
		request.setAttribute("urinext", uriNext);
		
		request.getRequestDispatcher("/WEB-INF/product/step1.jsp").forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
