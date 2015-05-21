<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>
<f:layout>

<style>
    .ViewTitle { color: White; background-color: SteelBlue; padding: 4px 2px 4px 2px; }
    
    #tab1 {  }
    #tab1 table>tbody>tr>td:first-child { width: 120px; text-align: right; }
    #tab1 table input[type=text] { width: 180px; }
    
	#tab2 ul, #tab3 li { list-style-type: none; }
	#tab2 ul>li { width: 160px; } 
</style>

<jsp:include page="/WEB-INF/common/control_TAB.jsp" />

<div>
	<table class="Tabs">
	<tr>
	  <td onclick="tabClick(this,'tab1');" class='${tabindex==0? "SelectedTab": "Tab" }'>Profile</td>
	  <td onclick="tabClick(this,'tab2');" class='${tabindex==1? "SelectedTab": "Tab" }'>Password</td>
	</tr>
	</table>
	<div class="TabBody">
	  <div id="tab1" style='${tabindex==0? "": "display: none;" }'>
	    <jsp:include page="/WEB-INF/useradmin/admin-right-userdetail.jsp"></jsp:include>
	  </div>
	  <div id="tab2" style='${tabindex==1? "": "display: none;" }'>
	    <jsp:include page="/WEB-INF/useradmin/admin-right-userpassword.jsp"></jsp:include>
	  </div>
	</div>
	<div style="color: red; text-align: left;">${message }</div>
</div>

</f:layout>
