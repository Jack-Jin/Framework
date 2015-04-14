<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="ViewTitle">Company: ${usercompany.companyName}</div>
<div>
	<table class="Tabs" cellpadding=0 cellspacing=2>
	<tr>
	  <td class="SelectedTab" onclick="tabClick(this,'tab1');">Company Info</td>
	  <td class="Tab" onclick="tabClick(this,'tab2');">Company Policy</td>
	</tr>
	</table>
	<div class="TabBody">
	  <%-- Company Info --%>
	  <div id="tab1">
	    <form action="" method="post">
	    <table>
	    <tr>
	      <td>Company Name</td>
	      <td><input type="text" name="companyName" value="${usercompany.companyName}"/></td>
	    </tr>
	    <tr>
	      <td>Address 1</td>
	      <td>
	      	<input type="text" name="address" value="${usercompany.address}"/>
	      	<input type="text" name="address1" value="${usercompany.address1}"/>
	      </td>
	    </tr>
	    <tr>
	      <td>City</td>
	      <td><input type="text" name="city" value="${usercompany.city}"/></td>
	    </tr>
	    <tr>
	      <td>State / Province</td>
	      <td><input type="text" name="state" value="${usercompany.state}"/></td>
	    </tr>
	    <tr>
	      <td>Postal Code</td>
	      <td><input type="text" name="postalCode" value="${usercompany.postalCode}"/></td>
	    </tr>
	    <tr>
	      <td>Country</td>
	      <td><input type="text" name="country" value="${usercompany.country}"/></td>
	    </tr>
	    <tr>
	      <td>Telephone</td>
	      <td><input type="text" name="telephone" value="${usercompany.telephone}"/></td>
	    </tr>
	    <tr>
	      <td>Fax</td>
	      <td><input type="text" name="fax" value="${usercompany.fax}"/></td>
	    </tr>
	    <tr>
	      <td>Email</td>
	      <td><input type="text" name="email" value="${usercompany.email}"/></td>
	    </tr>
	    <tr>
	      <td>Web Address</td>
	      <td><input type="text" name="www" value="${usercompany.www}"/></td>
	    </tr>
	    <tr>
	      <td>Contact Name</td>
	      <td><input type="text" name="contactName" value="${usercompany.contactName}"/></td>
	    </tr>
	    <tr>
	      <td></td>
	      <td>
		    <input type="submit" name="btnUpdate" value="Update" class="CusButton" style="width: 80px;"/>
		    <input type="hidden" name="action" value="Company Update"/>
		    <input type="hidden" name="id" value="${usercompany.id}"/>
	      </td>
	    </tr>
	    <tr>
	      <td colspan=2 style="color: red; text-align: left;">${resultmessage}</td>
	    </tr>
	    </table>
   	    </form>
	  </div>
	  <div id="tab2" style="display: none;">
	  	<jsp:include page="/WEB-INF/useradmin/admin-right-policy.jsp"></jsp:include>
	  </div>
	</div>
</div>
