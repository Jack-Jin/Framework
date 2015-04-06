<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="eceep.web.repository.WebContext" %>
<%@ page import="eceep.user.service.User"%>
<%@page import="eceep.user.domain.*"%>
<%@page import="java.util.*"%>

<%
	WebContext webContext = WebContext.getContext(request.getSession());
	Map<String,List<UserMenuLeaf>> menus = webContext.getUser().getUserMenu().getMenus();
	request.setAttribute("webmenus", menus);
%>
<div class="arrowGreenMenu">
  <c:forEach items="${webmenus}" var="entry">
    <div>${entry.key}</div>
    <ul>
      <c:forEach items="${entry.value}" var="leaf">
        <li><a href="${leaf.pageUrl}">${leaf.menuText}</a>
      </c:forEach>
    </ul>
  </c:forEach>
</div>