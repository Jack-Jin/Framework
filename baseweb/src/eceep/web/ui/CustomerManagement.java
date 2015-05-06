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
import eceep.customer.domain.CustomerDetail;
import eceep.user.User;
import eceep.web.repository.WebContext;

/**
 * Servlet implementation class CustomerManagement
 */
@WebServlet("/CustomerManagement")
public class CustomerManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerManagement() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get session
		HttpSession session = request.getSession();
		
		// Get context
		WebContext context = WebContext.getContext(session);
		
		// Get User
		User user = context.getUser();
		
		// Get Customer
		Customer customer = context.getCustomer();
		
		// Selected customer
		int selectedCustomerID = -1;
		CustomerDetail selectedCustomerDetail = null;
		List<CustomerContact> selectedCustomerContacts = new ArrayList<CustomerContact>();
		
		try {
			// Get Customers
			List<CustomerDetail> customers = customer.getCustomers();
			
			// Set selected customer
			if(selectedCustomerID < 0 && customers!=null && customers.size()>0){
				selectedCustomerID = customers.get(0).getId();
			}
				
			// selected customer detail, contacts.
			//selectedCustomerDetail = customer.getCustomerDetail();
			//selectedCustomerContacts = customer.getCustomerContacts();
			
			// request variables
			request.setAttribute("customers", customers);
			request.setAttribute("selectedcustomerID", selectedCustomerID);
			
			// Go to Page.
			request.getRequestDispatcher("/WEB-INF/customer/customer.jsp").forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
