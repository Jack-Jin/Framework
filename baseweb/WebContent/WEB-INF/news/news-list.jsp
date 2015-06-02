<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<table class="TBL_List">
<thead>
<tr>
  <th>Date</th>
  <th>Title</th>
  <th>Active</th>
</tr>
</thead>
<tbody>
<c:forEach var="eachnews" items="${newslist }">
  <tr ${eachnews.id==selectednewsID? "class='Selected'": "" }>
    <td style="width: 18%;"><fmt:formatDate value="${eachnews.modifiedTime }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
    <td>
      <a href='javascript: post("${pageContext.request.contextPath}/PostNews", { action: "Selected News", selectedNewsID: ${eachnews.id } })'>${eachnews.title }</a>
    </td>
    <td>${eachnews.active? "Active" : "Inactive" }</td>
  </tr>
</c:forEach>
</tbody>
</table>
