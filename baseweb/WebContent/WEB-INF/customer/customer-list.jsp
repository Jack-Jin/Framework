<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
  .TBL_CustomerList { width: 99%; background-color: #eee; }
  .TBL_CustomerList>tbody>tr { cursor: default; }
  .TBL_CustomerList>thead>tr>th { text-align: left; color: #eee; background-color: #666; }
  .TBL_CustomerList>tfoot { background-color: #ffe; }
  
  .TBL_CustomerList .Selected { color: #ff0; background-color: #33f; }
</style>

<%-- Variable: recordEnd --%>
<c:set var="leftRecords" value="${ customers.size() - (customerspagenumber-1)*pagemax }" />
<c:set var="recordEnd" value="${ leftRecords>5? pagemax : leftRecords }" />

<%-- Variable: totalpages --%>
<c:set var="tmptotalrecords" value="${ customers.size()-1 }" />
<c:set var="tmptotalpages" value="${ tmptotalrecords / pagemax + 1 }" />
<fmt:parseNumber var="totalpages" value="${ tmptotalpages }" integerOnly="true" type="number" />

<%-- Variable: groupMax, groupIndex, groupStart, groupEnd --%>
<c:set var="groupMax" value="5" />
<c:set var="groupStart" value="${ totalpages<=groupMax? 1 : (customerspagenumber>=(totalpages-groupMax+1)? totalpages-groupMax+1 : customerspagenumber) }" />
<c:set var="groupEnd" value="${ (groupStart+groupMax)>totalpages? totalpages : groupStart+groupMax-1  }" />

<%-- 
<div>
	total: ${customers.size() };
	leftRecords: ${leftRecords }
	recordEnd: ${recordEnd }
</div>
<div>
	customerspagenumber: ${customerspagenumber };
	totalpages: ${totalpages };
	groupIndex: ${groupIndex }; 
	groupStart: ${groupStart };
	groupEnd: ${groupEnd };	
</div>
--%>

<table class="TBL_CustomerList">
<thead>
  <tr>
    <th>Company Name</th>
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
  <c:forEach var="recordIndex" begin="0" end="${recordEnd-1 }" >
	<c:set var="customerIndex" value="${(customerspagenumber-1)*pagemax + recordIndex }" />
    <c:set var="eachcustomerdetail" value="${customers.get(customerIndex) }" />

    <tr ${eachcustomerdetail.id==selectedcustomerID? "class='Selected'": ""} 
        onclick='post("${pageContext.request.contextPath}/CustomerManagement", {action: "Selected Customer", selectedCustomerID: ${eachcustomerdetail.id} });'>
      <td>${eachcustomerdetail.customerName}</td>
      <td>${eachcustomerdetail.customerPrimaryContact.contactName}</td>
      <td>${eachcustomerdetail.street}</td>
      <td>${eachcustomerdetail.city}</td>
      <td>${eachcustomerdetail.state}</td>
      <td>${eachcustomerdetail.postalCode}</td>
      <td>${eachcustomerdetail.country}</td>
      <td>${eachcustomerdetail.phoneNo}</td>
      <td>${eachcustomerdetail.faxNo}</td>
      <td>${eachcustomerdetail.modifiedByName}</td>
      <td>
        <span class="CusButton" style="width: 35px; font-size: 0.95em;"
              onclick='post("${pageContext.request.contextPath}/CustomerManagement", {action: "Remove Customer", selectedCustomerID: ${eachcustomerdetail.id} });'>Delete</span>
      </td>
    </tr>
  </c:forEach>
</tbody>
<tfoot>
  <tr>
    <td>
      <span class="CusButton" style="width: 35px; font-size: 0.95em; text-align: center;"
            onclick='post("${pageContext.request.contextPath}/CustomerManagement", {action: "Add Customer"});'>Add</span>
    </td>
    <td colspan="10" style="text-align: right; padding-right: 20px;">
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
    </td>
  </tr>
</tfoot>
</table>

