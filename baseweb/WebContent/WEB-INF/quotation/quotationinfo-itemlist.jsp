<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="SubTitle">Item List</div>
<table cellpadding=0 cellspacing=0>
<thead>
  <tr>
    <th>Activity</th>
    <th>Item</th>
    <th>Revision</th>
    <th>Unit System</th>
    <th>Currency</th>
    <th>Product Type</th>
    <th>Industry</th>
    <th>Application Type</th>
  </tr>
</thead>
<tbody>
  <c:forEach var="item" items="${quotationitems }">
    <tr class='${item.id==quotationitemscurrentID? "StyleCurrentItem" : (item.sequence % 2==0? "EvenLine" : "OddLine") }'>
	  <td>
	    <input type="submit" name="submitButton" value="Open" class="CusButton" />
	    <input type="submit" name="submitButton" value="Delete" class="CusButton" />
	  </td>
	  <td>${item.itemName }</td>
	  <td>${item.itemRevision }</td>
	  <td>
	    <c:forEach var="list" items="${unitsystemlist }">
	      <c:if test="${list.key==item.unitID }">${list.value }</c:if>
	    </c:forEach>
	  </td>
	  <td>
	    <c:forEach var="list" items="${currencylist }">
	      <c:if test="${list.key==item.currencyID }">${list.value }</c:if>
	    </c:forEach>
	  </td>
	  <td>
	    <c:forEach var="list" items="${producttypelist }">
	      <c:if test="${list.key==item.productType.id }">${list.value }</c:if>
	    </c:forEach>
	  </td>
	  <td>
	    <c:forEach var="list" items="${productapplicationtypelist }">
	      <c:if test="${list.key==item.productApplicationType.id }">${list.value }</c:if>
	    </c:forEach>
	  </td>
	  <td>
	    <c:forEach var="list" items="${industrylist }">
	      <c:if test="${list.key==item.productApplicationType.id }">${list.value }</c:if>
	    </c:forEach>
	  </td>
    </tr>
  </c:forEach>
</tbody>
<tfoot>
  <tr>
    <td>
      <input type="submit" name="submitButton" value="New Item" class="CusButton" />
    </td>
    <td><input type="text" name="itemName" value="${newquotationitem.itemName }" /></td>
    <td><input type="text" name="itemRevision" value="${newquotationitem.itemRevision }" /></td>
    <td>
      <select name="unitID">
        <c:forEach var="list" items="${unitsystemlist }">
          <option value="${list.key }" ${list.key==newquotationitem.unitID? "selected=selected" : "" }>${list.value }</option>
        </c:forEach>
      </select>
    </td>
    <td>
      <select name="currencyID">
        <c:forEach var="list" items="${currencylist }">
          <option value="${list.key }" ${list.key==newquotationitem.currencyID? "selected=selected" : "" }>${list.value }</option>
        </c:forEach>
      </select>
    </td>
    <td>
      <select name="productType">
        <c:forEach var="list" items="${producttypelist }">
          <option value="${list.key }" ${list.key==newquotationitem.productType.id? "selected=selected" : "" }>${list.value }</option>
        </c:forEach>
      </select>
    </td>
    <td>
      <select name="industryType">
        <c:forEach var="list" items="${industrylist }">
          <option value="${list.key }" ${list.key==newquotationitem.industryType.id? "selected=selected" : "" }>${list.value }</option>
        </c:forEach>
      </select>
    </td>
    <td>
      <select name="productApplicationType">
        <c:forEach var="list" items="${productapplicationtypelist }">
          <option value="${list.key }" ${list.key==newquotationitem.productApplicationType.id? "selected=selected" : "" }>${list.value }</option>
        </c:forEach>
      </select>
    </td>
  </tr>
</tfoot>
</table>
