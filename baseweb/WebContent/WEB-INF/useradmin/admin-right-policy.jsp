<%@ page language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
  .blockline { border-bottom: 1px solid #bbb; }
  .TBL-PolicyOptions { width: 100%; border-collapse: collapse; }
  .TBL-PolicyOptions, .TBL-PolicyOptions tr, .TBL-PolicyOptions td { border: 0; padding: 0; margin: 0; }
</style>

<form action="" method="post">

<%-- Menus --%>
<c:set value="${0}" var="num" scope="request"></c:set>
<c:set value="${4}" var="colnum" scope="request"></c:set>
<table style="width: 95%;">
<tr>
  <td colspan="${colnum}" class="blockline">Menu</td>
</tr>
<tr>
<c:forEach items="${usermenu.menus}" var="usermenugroup">
	<c:forEach items="${usermenugroup.leaves}" var="usermenuleaf">
		<td><input type="checkbox" name="menus-${usermenuleaf.id}" ${usermenuleaf.isVisible? "checked=checked" : ""} />${usermenuleaf.menuText}</td>
		
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
	<c:set value="${3}" var="colnum" scope="request"></c:set>
	<c:set value="${100/colnum}" var="colwidth" scope="request"></c:set>
	<table style="width: 95%;">
	<tr>
	  <td colspan="${colnum}" class="blockline">Policy</td>
	</tr>
	<tr>
	<c:forEach items="${userpolicycheck}" var="checkpolicy">
	    <td style="width: ${colwidth}%;"><input type="checkbox" name="policy-${checkpolicy.id}" ${checkpolicy.value? "checked=checked" : ""} />${checkpolicy.name}</td>
	  
		<c:set value="${num+1}" var="num" scope="request"/>
		<c:if test="${num%colnum == 0}">
			</tr><tr>
		</c:if>	  
	</c:forEach>
	<c:forEach begin="1" end="${colnum-num%colnum}" var="ii" step="1">
		<td style="width: ${colwidth}%;"></td>
	</c:forEach>
	</tr>
	</table>
</c:if>

<%-- Policy - Options --%>
<c:if test="${userpolicyoption != null}">
  <table style="width: 95%;">
	<c:forEach items="${userpolicyoption}" var="optionpolicy">
	  <tr><td colspan=2 class="blockline">&nbsp;</td></tr>
      <tr>
        <td valign="top" style="width: 15%; padding-top: 3px;">${optionpolicy.name}</td>
        <td>
			<c:set value="${0}" var="num" scope="request"></c:set>
			<c:set value="${6}" var="colnum" scope="request"></c:set>
			<c:set value="${100/colnum}" var="colwidth" scope="request"></c:set>
			<table class="TBL-PolicyOptions">
			<tr valign="top">
			<c:forEach items="${optionpolicy.value}" var="optionpolicyitem">
			    <td style="width: ${colwidth}%;"><input type="checkbox" name="policy-${optionpolicyitem.id}" ${optionpolicyitem.ruleValue? "checked=checked" : ""} />${optionpolicyitem.optionName}</td>
			  
				<c:set value="${num+1}" var="num" scope="request"/>
				<c:if test="${num%colnum == 0}">
					</tr><tr>
				</c:if>	  
			</c:forEach>
			<c:forEach begin="1" end="${colnum-num%colnum}" var="ii" step="1">
				<td style="width: ${colwidth}%;"></td>
			</c:forEach>
			</tr>
			</table>
        </td>
      </tr>
	</c:forEach>
  </table>
</c:if>

<%-- Policy - Values --%>
<c:if test="${userpolicyvalue != null}">
	<c:set value="${0}" var="num" scope="request"></c:set>
	<c:set value="${3}" var="colnum" scope="request"></c:set>
	<c:set value="${100/colnum}" var="colwidth" scope="request"></c:set>
	<table style="width: 95%;">
	<tr>
	  <td colspan="${colnum}" class="blockline">&nbsp;</td>
	</tr>
	<tr>
	<c:forEach items="${userpolicyvalue}" var="voluepolicy">
	    <td style="width: ${colwidth}%;">
	      ${voluepolicy.name}:&nbsp;
	      <input type="text" name="policy-${voluepolicy.id}" value="${voluepolicy.value}" style="width: 80px;"/>
	    </td>
	  
		<c:set value="${num+1}" var="num" scope="request"/>
		<c:if test="${num%colnum == 0}">
			</tr><tr>
		</c:if>	  
	</c:forEach>
	<c:forEach begin="1" end="${colnum-num%colnum}" var="ii" step="1">
		<td style="width: ${colwidth}%;"></td>
	</c:forEach>
	</tr>
	</table>
</c:if>

<div style="width: 95%; padding-left: 120px; margin-top: 10px;">
    <input type="submit" name="btnUpdate" value="${caption_Button_PolicyUpdate}" class="CusButton" style="width: 80px;"/>
   	<c:if test="${!userpolicy.policyInherited}">
	    <input type="submit" name="btnUpdate" value="${caption_Button_PolicyRemove}" class="CusButton" style="width: 160px;"/>
   	</c:if>

    <c:choose>
    <c:when test="${companyselected}">
	    <input type="hidden" name="action" value="Company Policy Update"/>
    </c:when>
    <c:otherwise>
	    <input type="hidden" name="action" value="User Policy Update"/>
    </c:otherwise>
    </c:choose>
    
    <input type="hidden" name="companyID" value="${usercompany.id}"/>
    <c:if test="${!companyselected}">
	    <input type="hidden" name="userID" value="${userdetail.id}"/>
    </c:if>
    <input type="hidden" name="policyID" value="${userpolicy.id}"/>
</div>
</form>
