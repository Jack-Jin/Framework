<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<form action="" method="post">
	<ul>
	  <li>New Password:</li>
	  <li><input type="password" name="newPassword" ></li>
	</ul>
	<ul>
	  <li>Confirm Password:</li>
	  <li><input type="password" name="newPasswordConfirm" ></li>
	</ul>
	<ul>
	  <li>
	    <input type="submit" name="btnPasswordUpdate" value="Password Change" class="CusButton" />
	  </li>
	</ul>
	<input type="hidden" name="action" value="Password Change"/>
	<input type="hidden" name="companyID" value="${usercompany.id}"/>
	<input type="hidden" name="userID" value="${userdetail.id}"/>
</form>
