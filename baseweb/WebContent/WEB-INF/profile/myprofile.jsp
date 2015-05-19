<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>
<f:layout>

<jsp:include page="/WEB-INF/common/control_TAB.jsp" />

<div>
	<table class="Tabs">
	<tr>
	  <td onclick="tabClick(this,'tab1');" class='${tabindex==0? "SelectedTab": "Tab" }'>Profile</td>
	  <td onclick="tabClick(this,'tab2');" class='${tabindex==1? "SelectedTab": "Tab" }'>Password</td>
	</tr>
	</table>
	<div class="TabBody">
	  <div id="tab1">
	    <jsp:include page="/WEB-INF/useradmin/admin-right-userdetail.jsp"></jsp:include>
	  </div>
	  <div id="tab2" style="display: none;">
	    <jsp:include page="/WEB-INF/useradmin/admin-right-userpassword.jsp"></jsp:include>
	  </div>
	</div>
	<div style="color: red; text-align: left;">${message }</div>
</div>

</f:layout>