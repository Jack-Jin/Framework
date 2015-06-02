<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>

<f:layout>

<style>
  .TBL_List { width: 99%; background-color: #eee; margin-top: 2px; }
  .TBL_List>tbody>tr { cursor: default; }
  .TBL_List>thead>tr>th { text-align: left; color: #eee; background-color: #666; }
  .TBL_List>tfoot { background-color: #ffe; }
  
  .TBL_List .Selected { color: #ff0; background-color: #aaa; }
</style>

<jsp:include page="/WEB-INF/common/control_TAB.jsp" />

<div>
	<table class="Tabs">
	<tr>
	  <td onclick="tabClick(this,'tab1');" class='${tabindex==0? "SelectedTab": "Tab" }'>News List</td>
	  <td onclick="tabClick(this,'tab2');" class='${tabindex==1? "SelectedTab": "Tab" }'>Detail Info</td>
	</tr>
	</table>
	<div class="TabBody">
	  <div id="tab1" style='${tabindex==0? "": "display: none;" }'>
	    <jsp:include page="/WEB-INF/news/news-list.jsp" />
	  </div>
	  <div id="tab2" style='${tabindex==1? "": "display: none;" }'>
	    <jsp:include page="/WEB-INF/news/news-detail.jsp" />
	  </div>
	</div>
	<div style="color: red; text-align: left;">${message }</div>
</div>

</f:layout>
