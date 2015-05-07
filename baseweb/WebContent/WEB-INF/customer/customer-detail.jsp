<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>
.TBL_CustomerDetail { }
.TBL_CustomerDetail>tbody>tr>td:first-child { text-align: right; font-weight: bold; }
.TBL_CustomerDetail>tfoot>tr>td { padding-top: 20px; text-align: center; }
</style>

<form action="" method="post">

<table class="TBL_CustomerDetail">
<tbody>
  <tr>
    <td>Customer Name *</td>
    <td><input type="text" name="customerName" value="${customerdetail.customerName }"/ style="width: 200px;"></td>
  </tr>
  <tr>
    <td>Street</td>
    <td><input type="text" name="street" value="${customerdetail.street }" style="width: 400px;"/></td>
  </tr>
  <tr>
    <td>City</td>
    <td><input type="text" name="city" value="${customerdetail.city }"/></td>
  </tr>
  <tr>
    <td>State</td>
    <td><input type="text" name="state" value="${customerdetail.state }"/></td>
  </tr>
  <tr>
    <td>Country</td>
    <td><input type="text" name="country" value="${customerdetail.country }"/></td>
  </tr>
  <tr>
    <td>Postal Code</td>
    <td><input type="text" name="postalCode" value="${customerdetail.postalCode }" style="width: 50px;"/></td>
  </tr>
  <tr>
    <td>Phone</td>
    <td><input type="text" name="phoneNo" value="${customerdetail.phoneNo }" style="width: 80px;"/></td>
  </tr>
  <tr>
    <td>Fax</td>
    <td><input type="text" name="faxNo" value="${customerdetail.faxNo }" style="width: 80px;"/></td>
  </tr>
  <tr>
    <td>Note</td>
    <td><textarea name="notes" value="${customerdetail.notes }" rows="3" cols="40" style="width: 400px;"></textarea></td>
  </tr>
</tbody>
<tfoot>
  <tr>
    <td colspan="2">
	  <input type="submit" name="btnUpdate" value="Update" class="CusButton" style="width: 80px;"/>

	  <input type="hidden" name="action" value="Customer Update"/>
	  <input type="hidden" name="id" value="${customerdetail.id}"/>
    </td>
  </tr>
</tfoot>
</table>

</form>