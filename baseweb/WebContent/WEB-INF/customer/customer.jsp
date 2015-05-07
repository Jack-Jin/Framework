<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>
<f:layout>

<jsp:include page="/WEB-INF/common/control_TAB.jsp" />

<div>
	<table class="Tabs">
	<tr>
	  <td onclick="tabClick(this,'tab1');" class='${tabindex==0? "SelectedTab": "Tab" }'>Customer List</td>
	  <td onclick="tabClick(this,'tab2');" class='${tabindex==1? "SelectedTab": "Tab" }'>Detail Info</td>
	  <td onclick="tabClick(this,'tab3');" class='${tabindex==2? "SelectedTab": "Tab" }'>Contact</td>
	</tr>
	</table>
	<div class="TabBody">
	  <div id="tab1" style='${tabindex==0? "": "display: none;" }'>
	    <jsp:include page="/WEB-INF/customer/customer-list.jsp" />
	  </div>
	  <div id="tab2" style='${tabindex==1? "": "display: none;" }'>
	    <jsp:include page="/WEB-INF/customer/customer-detail.jsp" />
	  </div>
	  <div id="tab3" style='${tabindex==2? "": "display: none;" }'>
	    Contact
	  </div>
	</div>
	<div style="color: red; text-align: left;">${message }</div>
</div>

</f:layout>