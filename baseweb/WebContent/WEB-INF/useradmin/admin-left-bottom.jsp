<%@ page language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul style="list-style-type: none; margin: 0; padding: 0; color: #369;">
<c:forEach var="user" items="${users}">
  <li style="padding: 1px 0; cursor: default; ${(!companyselected && userdetail!=null && userdetail.id==user.id)? "background-color: #ccc;": ""}"
      onclick='post("${pageContext.request.contextPath}/UserCompanyManagement", { action : "Selected User", userID : ${user.id}, companyID: ${usercompany.id} });'>
      ${user.userName}${user.policyInherited? "<span style='color: red;'>&nbsp;*</span>" : "" }
  </li>
</c:forEach>
</ul>