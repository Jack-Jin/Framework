<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>

<style>
  
  #Main_News { width: 95%; margin-left: 10px; }

  .Main_News_Content { width: 97%; color: Navy; margin-top: 3px; margin-bottom: 20px; margin-left: 10px; }
  
</style>

<c:forEach var="eachnews" items="${newslist }">
<div>
  <div class="SubTitle">${eachnews.title }&nbsp;(${eachnews.modifiedTime })</div>
  <div class="Main_News_Content wordwrap">${eachnews.content }</div>
</div></c:forEach>
