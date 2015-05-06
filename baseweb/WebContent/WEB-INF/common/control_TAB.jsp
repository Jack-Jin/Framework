<%-- 
	<table class="Tabs">
	<tr>
	  <td class="SelectedTab" onclick="tabClick(this,'tab1');">Tab1 Title</td>
	  <td class="Tab" onclick="tabClick(this,'tab2');">Tab2 Title</td>
	  <td class="Tab" onclick="tabClick(this,'tab3');">Tab3 Title</td>
	</tr>
	</table>
	<div class="TabBody">
	  <!-- Tab1 Body -->
	  <div id="tab1">
	  </div>
	  <!-- Tab2 Body -->
	  <div id="tab2" style="display: none;">
	  </div>
	  <!-- Tab3 Body -->
	  <div id="tab3" style="display: none;">
	  </div>
	</div>
--%>

<style>
    .Tabs { position: relative; top: 3px; left: 10px; }
    .Tab, .SelectedTab { color: #2f4f4f;  padding: 0px 10px; height: 16px; vertical-align: middle; cursor: default; }
    .Tab { background-color:#eeeeee; margin-left:2px; border: Solid 1px #b0c4de; }
    .SelectedTab { background-color: white; font-weight: bold; border: Solid 1px #b0c4de; border-bottom: Solid 1px white; }
    
    .TabBody { padding: 2px; background-color: white; height: auto; border: #b0c4de 1px solid; }
</style>
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
  	}
</script>