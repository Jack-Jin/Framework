<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
  .TBL_NewsEdit { width: 99%; }
  .TBL_NewsEdit tr { vertical-align: top; }

</style>

<form action="" method="post">
<table class="TBL_NewsEdit">
<tbody>
  <tr>
    <td style="width: 80px;">Title:</td>
    <td><input type="text" name="title" value="${newsdetail.title }" style="width: 98%;" /></td>
  </tr>
  <tr>
    <td>Content:</td>
    <td><textarea name="content" rows="10" cols="20" style="width: 98%;">${newsdetail.content }</textarea></td>
  </tr>
  <tr>
    <td>Active:</td>
    <td><input type="checkbox" name="active" ${newsdetail.active? "checked":"" } /> </td>
  </tr>
</tbody>
<tfoot>
  <tr>
    <td></td>
    <td>
	  <input type="submit" name="btnUpdate" value="Update" class="CusButton" style="width: 80px;"/>
	  
	  <input type="hidden" name="action" value="News Update"/>
	  <input type="hidden" name="id" value="${newsdetail.id}"/>
    </td>
  </tr>
</tfoot>
</table>
</form>
