<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>

<style>
</style>

<c:forEach var="eachnews" items="${newslist }">
<div>
  <div class="SubTitle">${eachnews.title }&nbsp;(${eachnews.modifiedTime })</div>
  <div>${eachnews.content }</div>
</div></c:forEach>