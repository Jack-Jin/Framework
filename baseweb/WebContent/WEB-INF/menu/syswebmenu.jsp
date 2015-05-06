<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="eceep.web.repository.WebContext" %>
<%@ page import="eceep.user.User"%>
<%@page import="eceep.user.domain.*"%>
<%@page import="java.util.*"%>

<%
	WebContext webContext = WebContext.getContext(request.getSession());
	List<UserMenuGroup> menus = webContext.getUser().getUserMenu().getMenus();
	request.setAttribute("webmenus", menus);
%>
<div class="arrowGreenMenu">
  <c:forEach items="${webmenus}" var="entry">
	<c:if test="${entry.isVisible}">
	    <div>${entry.title}</div>
	    <ul>
	      <c:forEach items="${entry.leaves}" var="leaf">
	      	<c:if test="${leaf.isVisible}">
	        	<li><a href="${leaf.pageUrl}">${leaf.menuText}</a>
	      	</c:if>
	      </c:forEach>
	    </ul>
	</c:if>
  </c:forEach>
</div>