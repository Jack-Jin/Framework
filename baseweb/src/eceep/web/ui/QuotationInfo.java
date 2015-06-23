package eceep.web.ui;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eceep.customer.Customer;
import eceep.quotation.Quotation;
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
		
		
		
		
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/quotation/quotationinfo.jsp");
		requestDispatcher.forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
