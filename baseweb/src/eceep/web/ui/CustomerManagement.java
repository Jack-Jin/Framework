package eceep.web.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import eceep.customer.Customer;
import eceep.customer.domain.CustomerDetail;
import eceep.user.User;
import eceep.web.repository.WebContext;
import eceep.web.repository.WebUtils;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
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

		int tabIndex = 0;
		List<CustomerDetail> customers = new ArrayList<CustomerDetail>(); // CustomerList.
		int selectedCustomerID = -1; // Selected customer ID.
		int customersPageNumber = 1;
		int pageMax = 5;
		String message = "";

		CustomerDetail customerDetail = null; // Selected customer detail

		try {
			// Refresh Customer List.
			if (session.getAttribute("CustomerManagement_CustomerList") == null) {
				customers = customer.getCustomers();

				session.setAttribute("CustomerManagement_CustomerList", customers);
			} else {
				customers = (List<CustomerDetail>) session.getAttribute("CustomerManagement_CustomerList");
			}

			if (action.equalsIgnoreCase("Selected Customer")) {

				selectedCustomerID = Integer.parseInt(request.getParameter("selectedCustomerID"));

			} else if (action.equalsIgnoreCase("Add Customer")) {

				selectedCustomerID = customer.newCustomer(user.getUserDetail().getId());

				// Tab: customer detail
				tabIndex = 1;

				// Refresh Customer List.
				customers = customer.getCustomers();

				session.setAttribute("CustomerManagement_CustomerList", customers);

			} else if (action.equalsIgnoreCase("Remove Customer")) {

				int removeCustomerID = Integer.parseInt(request.getParameter("selectedCustomerID"));
				customer.removeCustomer(removeCustomerID, user.getUserDetail().getId());

				selectedCustomerID = -1;

				// Refresh Customer List.
				customers = customer.getCustomers();

				session.setAttribute("CustomerManagement_CustomerList", customers);

			} else if (action.equalsIgnoreCase("Customer Update")) {

				CustomerDetail updateCustomer = WebUtils.request2Bean(request, CustomerDetail.class);
				customer.updateCustomer(updateCustomer, user.getUserDetail().getId());

				selectedCustomerID = updateCustomer.getId();
				tabIndex = 1;
				message = "Customer info updated successfully.";

				// Refresh Customer List.
				customers = customer.getCustomers();

				session.setAttribute("CustomerManagement_CustomerList", customers);

			} else if (action.equalsIgnoreCase("Change Page Number")) {

				customersPageNumber = Integer.parseInt(request.getParameter("pageNumber"));

				selectedCustomerID = customers.get((customersPageNumber - 1) * pageMax).getId();

			}

			// Default selected customer ID.
			if (selectedCustomerID < 0 && customers != null && customers.size() > 0) {
				selectedCustomerID = customers.get(0).getId();
			}

			// Selected Customer Detail
			int tmpCustomerID = selectedCustomerID;
			List<CustomerDetail> tmpList = customers.stream().filter(A -> A.getId() == tmpCustomerID)
					.collect(Collectors.toList());
			if (tmpList != null && tmpList.size() > 0)
				customerDetail = tmpList.get(0);

			// Variables for UI.
			request.setAttribute("tabindex", tabIndex);
			request.setAttribute("customers", customers);
			request.setAttribute("selectedcustomerID", selectedCustomerID);
			request.setAttribute("customerdetail", customerDetail);
			request.setAttribute("customerspagenumber", customersPageNumber);
			request.setAttribute("pagemax", pageMax);
			request.setAttribute("message", message);

			// Go to Page.
			request.getRequestDispatcher("/WEB-INF/customer/customer.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
		}
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
