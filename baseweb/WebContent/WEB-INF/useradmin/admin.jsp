<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<t:layout>

<style>
  .UserAdminContent>table { width: 100%; }
  .UserAdminContent>table>tbody>tr>td:first-child { width: 200px; }
  .UserAdminContent>table>tbody>tr>td { height: 500px; border: solid 1px #cccccc; overflow-x: hidden; overflow-y: auto; }
  
  .UserAdminContent .Admin-Left-Top { width: 100%; height: 200px; overflow: scroll; }
  .UserAdminContent .Admin-Left-Middle { width: 100%; height: 16px; text-align: right; margin-top: 5px; margin-bottom: 1px; }
  .UserAdminContent .Admin-Left-Middle1 { width: 100%; height: 16px; text-align: right; padding-top: 1px; border-top: 1px solid #ccc; }
  .UserAdminContent .Admin-Left-Bottom { width: 100%; height: 260px; overflow: scroll; padding: 3px; }
  
  .Admin-Right input[type="checkbox"] { width: 10px; }
</style>

<div class="UserAdminContent">
  <table>
  <tr>
    <%-- Left Tree. --%>
    <td valign="top">
      <div class="Admin-Left-Top">
		<jsp:include page="/WEB-INF/useradmin/admin-left-top.jsp"/>
      </div>
      <div class="Admin-Left-Middle">
          <span class="CusButton" style="font-size: 0.75em;">+ Company</span>
          <span class="CusButton" style="font-size: 0.75em;">+ Child</span>
      </div>
      <div class="Admin-Left-Middle1">
          <span class="CusButton" style="font-size: 0.75em;">+ User</span>
      </div>
      <div class="Admin-Left-Bottom">
        <jsp:include page="/WEB-INF/useradmin/admin-left-bottom.jsp"/>
      </div>
    </td>
    <%-- Right Detail Info. --%>
    <td valign="top" class="Admin-Right">
		<jsp:include page="/WEB-INF/useradmin/admin-right.jsp"/>
    </td>
  </tr>
  </table>
</div>

</t:layout>
