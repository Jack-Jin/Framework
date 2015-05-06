<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<table>
<thead>
  <tr>
    <th>Company Name</th>
    <th>Contact</th>
    <th>Address</th>
    <th>City</th>
    <th>State</th>
    <th>Postal Code</th>
    <th>Country</th>
    <th>Phone</th>
    <th>Fax</th>
    <th>Created By</th>
  </tr>
</thead>
<tbody>
  <c:forEach var="eachcustomerdetail" items="${customers}">
    <tr>
      <td>${eachcustomerdetail.companyName}</td>
      <td>${eachcustomerdetail.}</td>
      <td>${eachcustomerdetail.street}</td>
      <td>${eachcustomerdetail.city}</td>
      <td>${eachcustomerdetail.state}</td>
      <td>${eachcustomerdetail.postalCode}</td>
      <td>${eachcustomerdetail.country}</td>
      <td>${eachcustomerdetail.phoneNo}</td>
      <td>${eachcustomerdetail.faxNo}</td>
      <td>${eachcustomerdetail.}</td>
    </tr>
  </c:forEach>
</tbody>
</table>

