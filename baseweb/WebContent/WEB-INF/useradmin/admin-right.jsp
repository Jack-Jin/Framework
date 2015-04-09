<%@ page language="java" %>
<div>
    <div style="padding: 4px 2px 4px 2px; background-color: SteelBlue; color: W.hite;">
        Top Company
    </div>
	<div  ID="uxMultiTabsCompany"></div>
	<div class="tabBody">
	  <%-- Company Info --%>
	  <div>
	    <table>
	    <tr>
	      <td>Company Name</td>
	      <td><input type="text" name="txtCompanyName" value="${usercompany.companyName}"/></td>
	    </tr>
	    <tr>
	      <td>Address 1</td>
	      <td><input type="text" name="txt" value="${usercompany.address}"/></td>
	    </tr>
	    <tr>
	      <td>Address 2</td>
	      <td><input type="text" name="txt" value="${usercompany.address1}"/></td>
	    </tr>
	    <tr>
	      <td>City</td>
	      <td><input type="text" name="txt" value="${usercompany.city}"/></td>
	    </tr>
	    <tr>
	      <td>State / Province</td>
	      <td><input type="text" name="txt" value="${usercompany.state}"/></td>
	    </tr>
	    <tr>
	      <td>Zip / Postal Code</td>
	      <td><input type="text" name="txt" value="${usercompany.postalCode}"/></td>
	    </tr>
	    <tr>
	      <td>Country</td>
	      <td><input type="text" name="txt" value="${usercompany.country}"/></td>
	    </tr>
	    <tr>
	      <td>Tel</td>
	      <td><input type="text" name="txt" value="${usercompany.telephone}"/></td>
	    </tr>
	    <tr>
	      <td>Fax</td>
	      <td><input type="text" name="txt" value="${usercompany.fax}"/></td>
	    </tr>
	    <tr>
	      <td>Email</td>
	      <td><input type="text" name="txt" value="${usercompany.email}"/></td>
	    </tr>
	    <tr>
	      <td>Web Address</td>
	      <td><input type="text" name="txt" value="${usercompany.www}"/></td>
	    </tr>
	    </table>
	    <input type="submit" name="btnUpdate" value="Update"/>
	  </div>
	</div>
</div>
