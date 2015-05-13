<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
  .TBL_ActivityDetail { margin-top: 10px; }
  .TBL_ActivityDetail>tbody>tr>td:first-child { text-align: right; font-weight: bold; }
  .TBL_ActivityDetail>tfoot>tr>td { padding-top: 20px; }
</style>

<%-- Contact List --%>
<table class="TBL_List">
<thead>
  <tr>
    <th>Activity Type</th>
    <th>Subject</th>
    <th>Detail</th>
    <th>By</th>
    <th></th>
  </tr>
</thead>
<tbody>
  <c:choose>
  <c:when test="${customeractivities!=null && customeractivities.size()>0 }">
    <c:forEach var="eachActivity" items="${customeractivities }">
      <tr ${eachActivity.id==selectedactivityID? "class='Selected'": ""}>
        <td style="width: 120px;">${eachActivity.activityType }</td>
	    <td>
	      <a href='javascript: post("${pageContext.request.contextPath}/CustomerManagement", {action: "Selected Activity", selectedactivityID: ${eachActivity.id}, selectedCustomerID: ${selectedcustomerID } });'>
	        ${eachActivity.activity }
	      </a>
	    </td>
	    <td>${eachActivity.detail }</td>
	    <td>${eachActivity.createdByName }</td>
	    <td style="width: 38px;">
          <span class="CusButton" style="width: 35px; font-size: 0.95em;"
                onclick='if(confirm("Are you sure to delete this customer?")) post("${pageContext.request.contextPath}/CustomerManagement", {action: "Remove Activity", activityID: ${eachActivity.id}, customerID: ${selectedcustomerID } });'>
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
            onclick='post("${pageContext.request.contextPath}/CustomerManagement", {action: "Add Activity", customerID: ${selectedcustomerID }, customerName: "${customerdetail.customerName }" } );'>
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

  <table class="TBL_ActivityDetail">
  <tbody>
	<tr>
	  <td>Activity Type</td>
	  <td>
	    <select name="activityTypeID" >
	      <c:forEach var="activitytype" items="${activitylist }">
	        <option value="${activitytype.key }" ${customeractivity.activityTypeID==activitytype.key? "selected='selected'" : "" }>
	          ${activitytype.value }
	        </option>
	      </c:forEach>
	    </select>
	  </td>
	</tr>
	<tr>
	  <td>Subject</td>
	  <td><input type="text" name="activity" value="${customeractivity.activity }" /></td>
	</tr>
	<tr>
	  <td>Detail</td>
	  <td><textarea name="detail" rows="3" cols="40" style="width: 400px;" >${customeractivity.detail }</textarea></td>
	</tr>
  </tbody>
  <tfoot>
    <tr>
      <td></td>
      <td>
  	    <input type="submit" name="btnUpdate" value="Update" class="CusButton" style="width: 80px;"/>

	    <input type="hidden" name="action" value="Activity Update"/>
	    <input type="hidden" name="id" value="${customeractivity.id}"/>
	    <input type="hidden" name="customerID" value="${customeractivity.customerID}"/>
      </td>
    </tr>
  </tfoot>
  </table>

</form>