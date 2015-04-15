<%@ page language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Menus --%>
<c:set value="${0}" var="num" scope="request"></c:set>
<c:set value="${4}" var="colnum" scope="request"></c:set>
<table style="width: 95%;">
<tr>
  <td colspan="${colnum}" style="border-bottom: 1px solid #333;">Menu</td>
</tr>
<tr>
<c:forEach items="${usermenu.menus}" var="usermenugroup">
	<c:forEach items="${usermenugroup.leaves}" var="usermenuleaf">
		<td><input type="checkbox" name="isVisible" ${usermenuleaf.isVisible? "checked=checked" : ""} />${usermenuleaf.menuText}</td>
		
		<c:set value="${num+1}" var="num" scope="request"></c:set>
		<c:if test="${num%colnum == 0}">
			</tr><tr>
		</c:if>
	</c:forEach>
</c:forEach>
<c:forEach begin="1" end="${colnum-num%colnum}" var="ii" step="1">
	<td></td>
</c:forEach>
</tr>
</table>

<%-- Policy - Check --%>
<c:if test="${userpolicycheck != null}">
	<c:set value="${0}" var="num" scope="request"></c:set>
	<c:set value="${4}" var="colnum" scope="request"></c:set>
	<table style="width: 95%;">
	<tr>
	  <td colspan="${colnum}" style="border-bottom: 1px solid #333;">Policy</td>
	</tr>
	<tr>
	<c:forEach items="${userpolicycheck}" var="checkpolicy">
	    <td><input type="checkbox" name="" ${checkpolicy.value? "checked=checked" : ""} />${checkpolicy.key}</td>
	  
		<c:set value="${num+1}" var="num" scope="request"/>
		<c:if test="${num%colnum == 0}">
			</tr><tr>
		</c:if>	  
	</c:forEach>
	<c:forEach begin="1" end="${colnum-num%colnum}" var="ii" step="1">
		<td></td>
	</c:forEach>
	</tr>
	</table>
</c:if>

<%-- Policy - Options --%>
<c:if test="${userpolicyoption != null}">
	<c:forEach items="${userpolicyoption}" var="optionpolicy">
		<c:set value="${0}" var="num" scope="request"></c:set>
		<c:set value="${4}" var="colnum" scope="request"></c:set>
		<table style="width: 95%;">
		<tr>
		<c:forEach items="${optionpolicy.value}" var="optionpolicyitem">
		    <td><input type="checkbox" name="" ${checkpolicy.value? "checked=checked" : ""} />${checkpolicy.key}</td>
		  
			<c:set value="${num+1}" var="num" scope="request"/>
			<c:if test="${num%colnum == 0}">
				</tr><tr>
			</c:if>	  
		</c:forEach>
		<c:forEach begin="1" end="${colnum-num%colnum}" var="ii" step="1">
			<td></td>
		</c:forEach>
		</tr>
		</table>
	</c:forEach>
</c:if>




