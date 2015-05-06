<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>
<f:layout>

<jsp:include page="/WEB-INF/common/control_TAB.jsp" />

<div>
	<table class="Tabs">
	<tr>
	  <td class="SelectedTab" onclick="tabClick(this,'tab1');">Customer List</td>
	  <td class="Tab" onclick="tabClick(this,'tab2');">Detail Info</td>
	  <td class="Tab" onclick="tabClick(this,'tab3');">Contact</td>
	</tr>
	</table>
	<div class="TabBody">
	  <div id="tab1">
	    <jsp:include page="/WEB-INF/customer/customer-customers.jsp" />
	  </div>
	  <div id="tab2" style="display: none;">
	    Customer Detail Info
	  </div>
	  <div id="tab3" style="display: none;">
	    Contact
	  </div>
	</div>
</div>

</f:layout>