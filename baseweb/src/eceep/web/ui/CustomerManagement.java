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
import eceep.customer.domain.CustomerContact;
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

		// ** Variables for page. ***************************************
		// Tab default List.
		int tabIndex = 0;
		// CustomerList.
		List<CustomerDetail> customers = new ArrayList<CustomerDetail>();
		// Selected customer ID.
		int selectedCustomerID = -1;
		String searchByCondition = (session.getAttribute("CustomerManagement_SearchByCondition") == null) ? ""
				: (String) session.getAttribute("CustomerManagement_SearchByCondition");
		String message = "";

		// Page display customer record number.
		int pageMax = 15;
		// Current page number.
		int customersPageNumber = 1;

		// Selected customer detail
		CustomerDetail customerDetail = null;

		// Contact List
		int selectedContactID = -1;
		List<CustomerContact> customerContacts = new ArrayList<CustomerContact>();
		CustomerContact customerContact = null;

		// ** Variables for page. End ***********************************

		try {
			// Refresh Customer List.
			if (session.getAttribute("CustomerManagement_CustomerList") == null) {
				customers = customer.getCustomers(searchByCondition);

				session.setAttribute("CustomerManagement_CustomerList", customers);
			} else {
				customers = (List<CustomerDetail>) session.getAttribute("CustomerManagement_CustomerList");
			}

			// Actions
			if (action.equalsIgnoreCase("Selected Customer")) { // ------------------------- Action - Selected Customer

				selectedCustomerID = Integer.parseInt(request.getParameter("selectedCustomerID"));

			} else if (action.equalsIgnoreCase("Add Customer")) { // ----------------------- Action - Add Customer

				selectedCustomerID = customer.newCustomer(user.getUserDetail().getId());

				// Tab: customer detail
				tabIndex = 1;

				// Refresh Customer List.
				searchByCondition = "";
				session.setAttribute("CustomerManagement_SearchByCondition", searchByCondition);

				customers = customer.getCustomers(searchByCondition);
				session.setAttribute("CustomerManagement_CustomerList", customers);

			} else if (action.equalsIgnoreCase("Remove Customer")) { // -------------------- Action - Remove Customer

				int removeCustomerID = Integer.parseInt(request.getParameter("selectedCustomerID"));
				customer.removeCustomer(removeCustomerID, user.getUserDetail().getId());

				selectedCustomerID = -1;

				// Refresh Customer List.
				searchByCondition = "";
				session.setAttribute("CustomerManagement_SearchByCondition", searchByCondition);

				customers = customer.getCustomers(searchByCondition);
				session.setAttribute("CustomerManagement_CustomerList", customers);

			} else if (action.equalsIgnoreCase("Customer Update")) { // -------------------- Action - Customer Update

				CustomerDetail updateCustomer = WebUtils.request2Bean(request, CustomerDetail.class);
				customer.updateCustomer(updateCustomer, user.getUserDetail().getId());

				selectedCustomerID = updateCustomer.getId();
				// Tab: customer detail
				tabIndex = 1;
				message = "Customer info updated successfully.";

				// Refresh Customer List.
				customers = customer.getCustomers(searchByCondition);

				session.setAttribute("CustomerManagement_CustomerList", customers);

			} else if (action.equalsIgnoreCase("Change Page Number")) { // ----------------- Action - Change Page Number

				customersPageNumber = Integer.parseInt(request.getParameter("pageNumber"));

				selectedCustomerID = customers.get((customersPageNumber - 1) * pageMax).getId();

			} else if (action.equalsIgnoreCase("Search By Condition")) { // ---------------- Action - Search By
																			// Condition
				searchByCondition = request.getParameter("txtSearchCondition");

				session.setAttribute("CustomerManagement_SearchByCondition", searchByCondition);

				// Refresh Customer List.
				customers = customer.getCustomers(searchByCondition);

				session.setAttribute("CustomerManagement_CustomerList", customers);
			} else if (action.equalsIgnoreCase("Selected Contact")) { // ------------------ Action - Selected Contact

				selectedContactID = Integer.parseInt(request.getParameter("selectedContactID"));
				selectedCustomerID = Integer.parseInt(request.getParameter("selectedCustomerID"));
				
				// Tab: Contact
				tabIndex = 2;
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

			// Set page number depends on selected customer ID.
			if (selectedCustomerID > 0 && customers != null && customers.size() > 0) {
				// customersPageNumber
				for (int i = 0; customers != null && i < customers.size(); i++) {
					if (customers.get(i).getId() == selectedCustomerID) {
						customersPageNumber = i / pageMax + 1;

						break;
					}
				}
			}

			// Customer Contact List.
			if (customerDetail != null) {
				customerContacts = customerDetail.getCustomerContacts();
			}

			// Default selected contact ID.
			if (selectedContactID < 0 && customerContacts != null && customerContacts.size() > 0) {
				selectedContactID = customerContacts.get(0).getId();
			}

			// Contact Detail
			int tmpContactID = selectedContactID;
			List<CustomerContact> tmpContacts = customerContacts.stream().filter(A -> A.getId() == tmpContactID)
					.collect(Collectors.toList());
			if(tmpContacts !=null && tmpContacts.size() >0)
				customerContact = tmpContacts.get(0);

			// ** Variables for UI. **********************************************
			request.setAttribute("tabindex", tabIndex);
			request.setAttribute("customers", customers);
			request.setAttribute("selectedcustomerID", selectedCustomerID);
			request.setAttribute("searchbycondition", searchByCondition);
			request.setAttribute("customerdetail", customerDetail);
			request.setAttribute("customerspagenumber", customersPageNumber);
			request.setAttribute("pagemax", pageMax);

			request.setAttribute("customercontacts", customerContacts);
			request.setAttribute("selectedcontactID", selectedContactID);
			request.setAttribute("customercontact", customerContact);

			request.setAttribute("message", message);
			// ** Variables for UI. End ******************************************

		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Go to Page.
		request.getRequestDispatcher("/WEB-INF/customer/customer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		this.doGet(request, response);
	}
}
