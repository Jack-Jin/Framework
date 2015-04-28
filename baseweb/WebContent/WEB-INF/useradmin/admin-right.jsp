<%@ page language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    .ViewTitle { color: White; background-color: SteelBlue; padding: 4px 2px 4px 2px; }
    
    .Tabs { position: relative; top: 3px; left: 10px; }
    .Tab, .SelectedTab { color: #2f4f4f;  padding: 0px 10px; height: 16px; vertical-align: middle; cursor: default; }
    .Tab { background-color:#eeeeee; margin-left:2px; border: Solid 1px #b0c4de; }
    .SelectedTab { background-color: white; font-weight: bold; border: Solid 1px #b0c4de; border-bottom: Solid 1px white; }
    
    .TabBody { padding: 2px; background-color: white; height: auto; border: #b0c4de 1px solid; }
    
    #tab1 {  }
    #tab1 table>tbody>tr>td:first-child { width: 120px; text-align: right; }
    #tab1 table input[type=text] { width: 180px; }
    
</style>

<c:choose>
<c:when test="${companyselected}">
	<jsp:include page="/WEB-INF/useradmin/admin-right-company.jsp"></jsp:include>
</c:when>
<c:otherwise>
	<jsp:include page="/WEB-INF/useradmin/admin-right-user.jsp"></jsp:include>
</c:otherwise>
</c:choose>

<script>
	function tabClick(me, tabName){
	  	var tabNodes = me.parentElement.childNodes;
	  	for(i=0; i<tabNodes.length; i++){
	  		tabNodes[i].className = "Tab";		  
	  	}
	  
	  	var panelNodes = document.getElementsByClassName("TabBody")[0].childNodes;
	  	for(i=0; i<panelNodes.length; i++){
			if(panelNodes[i] instanceof Element) {
				panelNodes[i].style.display="none";
		  	}
	  	}

	  	me.className = "SelectedTab";
	  
	  	var selectedTab = document.getElementById(tabName);
	  	selectedTab.style.display="block";	  

	  	//var tab1 = document.getElementById("tab1");
	  	//var tab2 = document.getElementById("tab2");
	  	//tab1.style.display="none";
	  	//tab2.style.display="none";
  	}
</script>