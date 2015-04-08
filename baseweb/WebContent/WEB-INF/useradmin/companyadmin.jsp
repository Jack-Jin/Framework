<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<t:layout>
<div class="UserAdminContent">
    <table style="width: 100%;">
    <tr>
    	<%-- Left Tree. --%>
        <td width="200px" valign="top" style="border: solid 1px #cccccc;">
			<jsp:include page="/WEB-INF/useradmin/Tree.jsp"/>
        </td>
        <%-- Right Detail Info. --%>
        <td valign="top">
            <div align="right" style="padding: 2px;">
              <a href="#">Add Child Company</a>
              &nbsp;
              <a href="#">Add Company</a>
            </div>
            
		  	<jsp:include page="/WEB-INF/useradmin/Info.jsp"/>
        </td>
    </tr>
    </table>
</div>
</t:layout>
