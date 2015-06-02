<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
  
  #Main_News { width: 95%; margin-left: 10px; }

  .Main_News_Content { width: 97%; color: Navy; margin-top: 3px; margin-bottom: 20px; margin-left: 10px; }
  
</style>

<c:forEach var="eachnews" items="${newslist }">
  <c:if test="${eachnews.active }">
    <div>
      <div class="SubTitle" style="width: 100%;">
        <span style="width: 78%; display: inline-block;" >${eachnews.title }</span>
        <span style="width: 20%; display: inline-block; text-align: right;"><fmt:formatDate value="${eachnews.modifiedTime }" pattern="yyyy-MM-dd hh:mm:ss" /></span>
      </div>
      <div class="Main_News_Content wordwrap">${eachnews.content }</div>
    </div>
  </c:if>
</c:forEach>
