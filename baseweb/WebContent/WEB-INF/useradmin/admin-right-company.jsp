<%@ page language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    .CompanyTitle { color: White; background-color: SteelBlue; padding: 4px 2px 4px 2px; }
    
    .Tabs { position: relative; top: 3px; left: 10px; }
    .Tab, .SelectedTab { color: #2f4f4f;  padding: 0px 10px; height: 16px; vertical-align: middle; cursor: default; }
    .Tab { background-color:#eeeeee; margin-left:2px; border: Solid 1px #b0c4de; }
    .SelectedTab { background-color: white; font-weight: bold; border: Solid 1px #b0c4de; border-bottom: Solid 1px white; }
    
    .TabBody { padding: 2px; background-color: white; height: auto; border: #b0c4de 1px solid; }
    
    #tabCompanyInfo { height: 100%; }
    #tabCompanyInfo table>tbody>tr>td:first-child { width: 120px; text-align: right; }
</style>

<div class="CompanyTitle">${usercompany.companyName}</div>
<div>
	<table class="Tabs" cellpadding=0 cellspacing=2>
	<tr>
	  <td class="SelectedTab" onclick="tabClick(this,'tabCompanyInfo');">Company Info</td>
	  <td class="Tab" onclick="tabClick(this,'tabCompanyPolicy');">Company Policy</td>
	</tr>
	</table>
	<div class="TabBody">
	  <%-- Company Info --%>
	  <div id="tabCompanyInfo">
	    <form action="" method="post">
	    <table>
	    <tr>
	      <td>Company Name</td>
	      <td><input type="text" name="companyName" value="${usercompany.companyName}"/></td>
	    </tr>
	    <tr>
	      <td>Address 1</td>
	      <td><input type="text" name="address" value="${usercompany.address}"/></td>
	    </tr>
	    <tr>
	      <td>Address 2</td>
	      <td><input type="text" name="address1" value="${usercompany.address1}"/></td>
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
	      <td>Zip / Postal Code</td>
	      <td><input type="text" name="postalCode" value="${usercompany.postalCode}"/></td>
	    </tr>
	    <tr>
	      <td>Country</td>
	      <td><input type="text" name="country" value="${usercompany.country}"/></td>
	    </tr>
	    <tr>
	      <td>Tel</td>
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
	      <td colspan=2>
		    <input type="submit" name="btnUpdate" value="Update" class="CusButton" style="width: 80px;"/>
	      </td>
	    </tr>
	    </table>
   	    </form>
	  </div>
	  <div id="tabCompanyPolicy" style="display: none;">
	  	Company Policy
	  </div>
	</div>
</div>
<script>
  function tabClick(me, tabName){
	  var nodes = me.parentElement.childNodes;
	  for(i=0; i<nodes.length; i++){
		  nodes[i].className = "Tab";
	  }
	  
	  me.className = "SelectedTab";
	  
	  var tabCompanyInfo = document.getElementById("tabCompanyInfo");
	  var tabCompanyPolicy = document.getElementById("tabCompanyPolicy");
	  var selectedTab = document.getElementById(tabName);
	  tabCompanyInfo.style.display="none";
	  tabCompanyPolicy.style.display="none";
	  selectedTab.style.display="block";	  
  }
</script>