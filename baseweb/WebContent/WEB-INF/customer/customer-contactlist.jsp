<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
contacts total: ${customercontacts.size() }
</div>

<table class="TBL_List">
<thead>
  <tr>
    <th>Name</th>
    <th>Title</th>
    <th>Phone</th>
    <th>Fax</th>
    <th>Email</th>
    <th>Primary</th>
  </tr>
</thead>
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
      </tr>    
    </c:forEach>
  </c:when>
  <c:otherwise>
    <tr>
      <td colspan="6">No Contact.</td>
    </tr>
  </c:otherwise>
  </c:choose>
</table>