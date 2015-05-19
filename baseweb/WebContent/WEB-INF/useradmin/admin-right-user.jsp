<%@ page language="java"%>

<style type="text/css">
	#tab3 ul, #tab3 li { list-style-type: none; }
	#tab3 ul>li { width: 160px; } 
</style>

<div class="ViewTitle">User: ${userdetail.userName}</div>
<div>
	<table class="Tabs" cellpadding=0 cellspacing=2>
	<tr>
	  <td class="SelectedTab" onclick="tabClick(this,'tab1');">User Info</td>
	  <td class="Tab" onclick="tabClick(this,'tab2');">User Policy</td>
	  <td class="Tab" onclick="tabClick(this,'tab3');">Password</td>
	</tr>
	</table>
	<div class="TabBody">
	  <%-- User Detail --%>
	  <div id="tab1">
	    <jsp:include page="/WEB-INF/useradmin/admin-right-userdetail.jsp"></jsp:include>
	  </div>
	  <div id="tab2" style="display: none;">
	  	<jsp:include page="/WEB-INF/useradmin/admin-right-policy.jsp"></jsp:include>
	  </div>
	  <div id="tab3" style="display: none;">
	    <jsp:include page="/WEB-INF/useradmin/admin-right-userpassword.jsp"></jsp:include>
	  </div>
	</div>
</div>

	