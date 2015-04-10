<%@ page language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${companyselected}">
	<jsp:include page="/WEB-INF/useradmin/admin-right-company.jsp"></jsp:include>
</c:if>