<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="ViewTitle">User: ${userdetail.userName}</div>
<div>
	<table class="Tabs" cellpadding=0 cellspacing=2>
	<tr>
	  <td class="SelectedTab" onclick="tabClick(this,'tab1');">User Info</td>
	  <td class="Tab" onclick="tabClick(this,'tab2');">User Policy</td>
	</tr>
	</table>
	<div class="TabBody">
	  <%-- User Detail --%>
	  <div id="tab1">
	    <form action="" method="post">
		<table>
		<tr>
		  <td>User Name</td>
		  <td><input type="text" name="userName" value="${userdetail.userName}"/></td>
		</tr>
		<tr>
		  <td>First Name</td>
		  <td>
		    <input type="text" name="firstName" value="${userdetail.firstName}"/>
		    &nbsp;&nbsp;
		    Last Name
		    <input type="text" name="lastName" value="${userdetail.lastName}"/>
		  </td>
		</tr>
		<tr>
		  <td>Title</td>
		  <td><input type="text" name="title" value="${userdetail.title}"/></td>
		</tr>
		<tr>
		  <td>Address</td>
		  <td>
		    <input type="text" name="address" value="${userdetail.address}"/>
		    <input type="text" name="address1" value="${userdetail.address1}"/>
		  </td>
		</tr>
		<tr>
		  <td>City</td>
		  <td><input type="text" name="city" value="${userdetail.city}"/></td>
		</tr>
		<tr>
		  <td>State</td>
		  <td><input type="text" name="state" value="${userdetail.state}"/></td>
		</tr>
		<tr>
		  <td>Country</td>
		  <td><input type="text" name="country" value="${userdetail.country}"/></td>
		</tr>
		<tr>
		  <td>Postal Code</td>
		  <td><input type="text" name="postalCode" value="${userdetail.postalCode}"/></td>
		</tr>
		<tr>
		  <td>Telephone</td>
		  <td><input type="text" name="telephone" value="${userdetail.telephone}"/></td>
		</tr>
		<tr>
		  <td>Fax</td>
		  <td><input type="text" name="fax" value="${userdetail.fax}"/></td>
		</tr>
		<tr>
		  <td>EMail</td>
		  <td><input type="text" name="email" value="${userdetail.email}"/></td>
		</tr>
		<tr>
		  <td>WWW</td>
		  <td><input type="text" name="www" value="${userdetail.www}"/></td>
		</tr>
		<tr>
		  <td>Note</td>
		  <td><input type="text" name="note" value="${userdetail.note}"/></td>
		</tr>
		<tr>
		  <td>Is Admin</td>
		  <td><input type="checkbox" name="isAdmin" ${(userdetail.isAdmin)? "checked=checked" : "" }/>
		</tr>
		<tr>
		  <td>Is Never Expire</td>
		  <td><input type="checkbox" name="isNeverExpire" ${(userdetail.isNeverExpire)? "checked=checked" : ""}/>
		</tr>
		<tr>
		  <td>Expiry Date</td>
		  <fmt:formatDate value="${userdetail.expiryDate}" var="fmt_ExpiryDate" scope="request" pattern="yyyy-MM-dd"/>
		  <td><input type="text" name="expiryDate" value="${fmt_ExpiryDate}" maxlength="10"/>&nbsp;(YYYY-MM-DD)</td>
		</tr>
		<tr>
		  <td></td>
	      <td>
		    <input type="submit" name="btnUpdate" value="Update" class="CusButton" style="width: 80px;"/>
		    <input type="hidden" name="action" value="User Update"/>
		    <input type="hidden" name="id" value="${userdetail.id}"/>
		    <input type="hidden" name="companyID" value="${usercompany.id}"/>
	      </td>
	    </tr>
	    <tr>
	      <td colspan=2 style="color: red; text-align: left;">${resultmessage}</td>
	    </tr>
		</table>
   	    </form>
	  </div>
	  <div id="tab2" style="display: none;">
	  	User Policy
	  </div>
	</div>
</div>

	