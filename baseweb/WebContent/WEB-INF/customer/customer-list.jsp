<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%-- Variable: recordEnd --%>
<c:set var="leftRecords" value="${ customers.size() - (customerspagenumber-1)*pagemax }" scope="request" />
<c:set var="recordEnd" value="${ leftRecords>pagemax? pagemax : leftRecords }" scope="request" />

<%-- Variable: totalpages --%>
<c:set var="tmptotalrecords" value="${ customers.size()-1 }" scope="request" />
<c:set var="tmptotalpages" value="${ tmptotalrecords / pagemax + 1 }" scope="request" />
<fmt:parseNumber var="totalpages" value="${ tmptotalpages }" integerOnly="true" type="number" scope="request" />

<%-- Variable: groupMax, groupIndex, groupStart, groupEnd --%>
<c:set var="groupMax" value="5" scope="request" />
<c:set var="groupStart" value="${ totalpages<=groupMax? 1 : (customerspagenumber>=(totalpages-groupMax+1)? totalpages-groupMax+1 : customerspagenumber) }" scope="request" />
<c:set var="groupEnd" value="${ (groupStart+groupMax)>totalpages? totalpages : groupStart+groupMax-1  }" scope="request" />

<%-- 
<div>
	total: ${customers.size() };
	leftRecords: ${leftRecords }
	recordEnd: ${recordEnd }
</div>
<div>
	customerspagenumber: ${customerspagenumber };
	totalpages: ${totalpages };
	groupStart: ${groupStart };
	groupEnd: ${groupEnd };	
</div>
--%>
<div style="margin-top: 5px;">
  <form method="post">
  	<span style="font-weight: bold;">Customer Name:&nbsp;</span>
  	<input type="text" name="txtSearchCondition" value="${searchbycondition }" style="width: 200px;" />
  	<input type="submit" value="Search" class="CusButton" style="width: 80px;" />
  	<input type="hidden" name="action" value="Search By Condition" />
  </form>
</div>

<table class="TBL_List">
<thead>
  <tr>
    <th>Customer Name</th>
    <th>Contact</th>
    <th>Address</th>
    <th>City</th>
    <th>State</th>
    <th>Postal Code</th>
    <th>Country</th>
    <th>Phone</th>
    <th>Fax</th>
    <th>By</th>
    <th style="width: 40px;"></th>
  </tr>
</thead>
<tbody>
  <c:choose>
  <c:when test="${customers!=null && customers.size()>0 }">
    <c:forEach var="recordIndex" begin="0" end="${recordEnd-1 }"  >
	  <c:set var="customerIndex" value="${(customerspagenumber-1)*pagemax + recordIndex }" scope="request" />
      <c:set var="eachcustomerdetail" value="${customers[customerIndex] }" scope="request" />

      <tr ${eachcustomerdetail.id==selectedcustomerID? "class='Selected'": ""}>
        <td>
          <a href='javascript: post("${pageContext.request.contextPath}/CustomerManagement", {action: "Selected Customer", selectedCustomerID: ${eachcustomerdetail.id} });'>
            ${eachcustomerdetail.customerName}
          </a>
        </td>
        <td>${eachcustomerdetail.customerPrimaryContact.contactName}</td>
        <td>${eachcustomerdetail.street}</td>
        <td>${eachcustomerdetail.city}</td>
        <td>${eachcustomerdetail.state}</td>
        <td>${eachcustomerdetail.postalCode}</td>
        <td>${eachcustomerdetail.country}</td>
        <td>${eachcustomerdetail.phoneNo}</td>
        <td>${eachcustomerdetail.faxNo}</td>
        <td>${eachcustomerdetail.modifiedByName}</td>
        <td style="width: 38px;">
          <span class="CusButton" style="width: 35px; font-size: 0.95em;"
                onclick='if(confirm("Are you sure to delete this customer?")) post("${pageContext.request.contextPath}/CustomerManagement", {action: "Remove Customer", selectedCustomerID: ${eachcustomerdetail.id} });'>
                Delete
          </span>
        </td>
      </tr>
    </c:forEach>
  </c:when>
  <c:otherwise>
    <tr>
      <td colspan="11">No record.</td>
    </tr>
  </c:otherwise>
  </c:choose>
</tbody>
<tfoot>
  <tr>
    <td style="padding-top: 5px; padding-bottom: 5px;">
      <span class="CusButton" style="width: 35px; font-size: 0.95em; text-align: center;"
            onclick='post("${pageContext.request.contextPath}/CustomerManagement", {action: "Add Customer"});'>
            Add
      </span>
    </td>
    <td colspan="10" style="text-align: right; padding-right: 20px;">
    <c:if test="${customers!=null && customers.size()>0 }">

      Pages:&nbsp;
      <a href='javascript: post("${pageContext.request.contextPath}/CustomerManagement",{ action: "Change Page Number", pageNumber: 1 })'>
        |&lt;
      </a>
      &nbsp;&nbsp;
      
      <a href='javascript: post("${pageContext.request.contextPath}/CustomerManagement",{ action: "Change Page Number", pageNumber: ${groupStart } })'
         style='${customerspagenumber==groupStart? "font-weight:bold;" : "" }'>
        ${groupStart }
      </a>
      <c:forEach var="totalpagesindex" begin="${groupStart+1 }" end="${groupEnd }">
    	,
    	<a href='javascript: post("${pageContext.request.contextPath}/CustomerManagement",{ action: "Change Page Number", pageNumber: ${totalpagesindex } })'
    	   style='${customerspagenumber==totalpagesindex? "font-weight:bold;" : "" }'>
    	   ${totalpagesindex }
    	</a>	
      </c:forEach>
      
      &nbsp;&nbsp;
      <a href='javascript: post("${pageContext.request.contextPath}/CustomerManagement",{ action: "Change Page Number", pageNumber: ${totalpages } })'>
        &gt;|
      </a>
    
    </c:if>
    </td>
  </tr>
</tfoot>
</table>

