<%@ page language="java"%>
<style>
    /* treeoftree */
    ul.treeoftree { margin: 0; padding: 0; color: #369;	font-weight: bold; list-style-type: none; }
	ul.treeoftree>li { margin: 0; padding: 0 16px; line-height: 20px; }
	div.treeoftree-expand, div.treeoftree-collapse { margin-left: -16px; padding: 0 16px; inline-height: 20px; }  
    div.treeoftree-expand { background: url(${ pageContext.request.contextPath }/base/css/images/expand.png) no-repeat; }
    div.treeoftree-collapse { background: url(${ pageContext.request.contextPath }/base/css/images/collapse.png) no-repeat; }
</style>
<script type="text/javascript">
	function nodeclick(me) {
		var sibling = me.nextElementSibling;
		if(me.className=="treeoftree-collapse") {
			me.className="treeoftree-expand";
			sibling.style.display="block";
		}
		else {
			me.className="treeoftree-collapse";
			sibling.style.display="none";
		}
	}
</script>

<ul class="treeoftree">
  <jsp:include page="/WEB-INF/useradmin/TreeNode.jsp"></jsp:include>
</ul>