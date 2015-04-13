<%@ page language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- If first is "root", do not display. --%>
<c:choose>
<c:when test="${ node.id==0 }">       								<%-- If is root --%>
   	<c:forEach var="node" items="${ node.children }">
   		<c:set var="node" value="${ node }" scope="request" />
   		<jsp:include page="TreeNode.jsp" />	    
   	</c:forEach>
</c:when>
<c:otherwise>                         								<%-- it is not root --%>
    <%-- If have children. --%>
    <c:choose>
    <c:when test="${ node.children.size()>0 }">
      	<li>
          <div class="treeoftree-expand">
            <span onclick="nodeclick(this.parentElement);"></span>
            <span style='${usercompany.id==node.id?"background-color: #ccc;":""}' 
                  onclick='post("${pageContext.request.contextPath}/UserCompanyManagement", { action : "Selected Company", companyID : ${node.id} });'>${node.name}</span>
          </div>
	  	  <ul class="treeoftree">
        	<c:forEach var="node" items="${node.children}">
	      		<c:set var="node" value="${node}" scope="request" />
	      		<jsp:include page="TreeNode.jsp" />	    
	    	</c:forEach>
	  	  </ul>
	  	</li>
    </c:when>
    <c:otherwise>
		<li><span style='${usercompany.id==node.id?"background-color: #ccc;":""}' 
		          onclick='post("${pageContext.request.contextPath}/UserCompanyManagement", { action : "Selected Company", companyID : ${node.id} });'>${node.name}</span></li>
    </c:otherwise>
    </c:choose>
</c:otherwise>
</c:choose>
