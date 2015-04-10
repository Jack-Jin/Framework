<%@ page language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul style="list-style-type: none; margin: 0; padding: 0; color: #369;">
<c:forEach var="user" items="${users}">
  <li style="padding: 1px 0;">${user.userName}</li>
</c:forEach>
</ul>