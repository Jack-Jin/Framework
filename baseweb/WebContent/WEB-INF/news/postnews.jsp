<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<f:layout>

<table>
<thead>
<tr>
  <th>Date</th>
  <th>Title</th>
  <th>Active</th>
</tr>
</thead>
<tbody>
<c:forEach var="eachnews" items="${newslist }">
<tr>
  <td>${eachnews.modifiedTime }</td>
  <td>${eachnews.title }</td>
  <td>${eachnews.active? "Active" : "Inactive" }</td>
</tr>

</c:forEach>
</tbody>
</table>

</f:layout>
