<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<t:layout>

<style>
  .UserAdminContent>table { width: 100%; }
  .UserAdminContent>table>tbody>tr>td:first-child { width: 200px; }
  .UserAdminContent>table>tbody>tr>td { height: 500px; border: solid 1px #cccccc; overflow-x: hidden; overflow-y: auto; }
  
  .UserAdminContent .Admin-Left-Top { width: 100%; height: 200px; overflow: scroll; }
  .UserAdminContent .Admin-Left-Bottom { width: 100%; height: 300px; overflow: scroll; }
</style>

<div class="UserAdminContent">
  <table>
  <tr>
    <%-- Left Tree. --%>
    <td valign="top">
      <div class="Admin-Left-Top">
		<jsp:include page="/WEB-INF/useradmin/admin-left-top.jsp"/>
      </div>
      <div class="Admin-Left-Bottom">
        <jsp:include page="/WEB-INF/useradmin/admin-left-bottom.jsp"/>
      </div>
    </td>
    <%-- Right Detail Info. --%>
    <td valign="top">
        <div align="right" style="padding: 2px;">
        <a href="#">Add Child Company</a>
        &nbsp;
        <a href="#">Add Company</a>
        </div>
            
		<jsp:include page="/WEB-INF/useradmin/admin-right.jsp"/>
    </td>
  </tr>
  </table>
</div>

</t:layout>
