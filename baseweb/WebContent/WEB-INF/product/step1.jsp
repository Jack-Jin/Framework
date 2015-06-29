<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>

<f:layout withMilestone="true">

<div class="PageTitle">STEP 1</div>
<%-- Message --%>
<div class="PageMessage">${message }</div>

<div>
  Step 1

  <div style="width: 100%; text-align: center; ">
    <a href='${uriprevious }' class="CusButton" >Previous</a>
    &nbsp;&nbsp;
    <a href='${urinext }' class="CusButton">Next</a>
  </div>
</div>

</f:layout>
