<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
  .TBL_ContactDetail { margin-top: 10px; }
  .TBL_ContactDetail>tbody>tr>td:first-child { text-align: right; font-weight: bold; }
  .TBL_ContactDetail>tfoot>tr>td { padding-top: 20px; }
</style>

<%-- Contact List --%>
<table class="TBL_List">
<thead>
  <tr>
    <th>Name</th>
    <th>Title</th>
    <th>Phone</th>
    <th>Fax</th>
    <th>Email</th>
    <th>Primary</th>
    <th></th>
  </tr>
</thead>
<tbody>
  <c:choose>
  <c:when test="${customercontacts!=null && customercontacts.size()>0 }">
    <c:forEach var="eachContact" items="${customercontacts }">
      <tr ${eachContact.id==selectedcontactID? "class='Selected'": ""}>
	    <td>
	      <a href='javascript: post("${pageContext.request.contextPath}/CustomerManagement", {action: "Selected Contact", selectedContactID: ${eachContact.id}, selectedCustomerID: ${selectedcustomerID } });'>
	        ${eachContact.contactName }
	      </a>
	    </td>
	    <td>${eachContact.contactTitle }</td>
	    <td>${eachContact.directPhoneNo }</td>
	    <td>${eachContact.directFaxNo }</td>
	    <td>${eachContact.emailAddress }</td>
	    <td>${eachContact.isPrimaryContact? "Yes" : "" }</td>
	    <td style="width: 38px;">
          <span class="CusButton" style="width: 35px; font-size: 0.95em;"
                onclick='if(confirm("Are you sure to delete this customer?")) post("${pageContext.request.contextPath}/CustomerManagement", {action: "Remove Contact", contactID: ${eachContact.id}, customerID: ${selectedcustomerID } });'>
                Delete
          </span>	    
	    </td>
      </tr>    
    </c:forEach>
  </c:when>
  <c:otherwise>
    <tr>
      <td colspan="6">No Contact.</td>
    </tr>
  </c:otherwise>
  </c:choose>
</tbody>
<tfoot>
  <tr>
    <td colspan="7" style="padding-top: 1px;">
      <span class="CusButton" style="width: 35px; font-size: 0.95em; text-align: center;"
            onclick='post("${pageContext.request.contextPath}/CustomerManagement", {action: "Add Contact", customerID: ${selectedcustomerID }, customerName: "${customerdetail.customerName }" } );'>
            Add
      </span>
    </td>
  </tr>
</tfoot>
</table>

<%-- Contact Detail --%>
<%-- 
	private String contactName;
	private boolean isPrimaryContact;
	private String contactTitle;
	private String directPhoneNo;
	private String directFaxNo;
	private String emailAddress;
	private String note;
--%>
<form method="post">

  <table class="TBL_ContactDetail">
  <tbody>
	<tr>
	  <td>Contact Name</td>
	  <td><input type="text" name="contactName" value="${customercontact.contactName }" /></td>
	</tr>
	<tr>
	  <td>Primary</td>
	  <td><input type="checkbox" name="isPrimaryContact" ${customercontact.isPrimaryContact? "checked" : "" } /></td>
	</tr>
	<tr>
	  <td>Contact Title</td>
	  <td><input type="text" name="contactTitle" value="${customercontact.contactTitle }" /></td>
	</tr>
	<tr>
	  <td>Phone No</td>
	  <td><input type="text" name="directPhoneNo" value="${customercontact.directPhoneNo }" style="width: 80px;" /></td>
	</tr>
	<tr>
	  <td>Fax</td>
	  <td><input type="text" name="directFaxNo" value="${customercontact.directFaxNo }" style="width: 80px;" /></td>
	</tr>
	<tr>
	  <td>Email</td>
	  <td><input type="text" name="emailAddress" value="${customercontact.emailAddress }" /></td>
	</tr>
	<tr>
	  <td>Note</td>
	  <td><textarea name="note" rows="3" cols="40" style="width: 400px;" >${customercontact.note }</textarea></td>
	</tr>
  </tbody>
  <tfoot>
    <tr>
      <td></td>
      <td>
  	    <input type="submit" name="btnUpdate" value="Update" class="CusButton" style="width: 80px;"/>

	    <input type="hidden" name="action" value="Contact Update"/>
	    <input type="hidden" name="id" value="${customercontact.id}"/>
	    <input type="hidden" name="customerID" value="${customercontact.customerID}"/>
      </td>
    </tr>
  </tfoot>
  </table>

</form>