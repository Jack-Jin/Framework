<%@ page language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/common/control_TAB.jsp" />

<style>
    .ViewTitle { color: White; background-color: SteelBlue; padding: 4px 2px 4px 2px; }
    
    #tab1 {  }
    #tab1 table>tbody>tr>td:first-child { width: 120px; text-align: right; }
    #tab1 table input[type=text] { width: 180px; }
</style>

<c:choose>
<c:when test="${companyselected}">
	<jsp:include page="/WEB-INF/useradmin/admin-right-company.jsp" />
</c:when>
<c:otherwise>
	<jsp:include page="/WEB-INF/useradmin/admin-right-user.jsp" />
</c:otherwise>
</c:choose>
