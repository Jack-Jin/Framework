<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set value="1" var="ThisIsChangeLanguage" scope="request"/>
<fmt:setLocale value="${eCeepLanguage}"/>
<fmt:setBundle basename="text" var="eceeptext"/>

<!DOCTYPE>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>eCeep</title>
  <link href="${pageContext.request.contextPath}/base/css/Site.css" rel="stylesheet" type="text/css" />
  <link href="${pageContext.request.contextPath}/base/css/Site_Controls.css" rel="stylesheet" type="text/css" />
  <link href="${pageContext.request.contextPath}/Content/Home_Logon.css" rel="stylesheet" type="text/css" />
<%--
	<link href="/Base/Content/themes/base/jquery.ui.core.css" rel="stylesheet" type="text/css" />
	<link href="/Base/Content/themes/base/jquery.ui.resizable.css" rel="stylesheet" type="text/css" />
	<link href="/Base/Content/themes/base/jquery.ui.selectable.css" rel="stylesheet" type="text/css" />
	<link href="/Base/Content/themes/base/jquery.ui.accordion.css" rel="stylesheet" type="text/css" />
	<link href="/Base/Content/themes/base/jquery.ui.autocomplete.css" rel="stylesheet" type="text/css" />
	<link href="/Base/Content/themes/base/jquery.ui.button.css" rel="stylesheet" type="text/css" />
	<link href="/Base/Content/themes/base/jquery.ui.dialog.css" rel="stylesheet" type="text/css" />
	<link href="/Base/Content/themes/base/jquery.ui.slider.css" rel="stylesheet" type="text/css" />
	<link href="/Base/Content/themes/base/jquery.ui.tabs.css" rel="stylesheet" type="text/css" />
	<link href="/Base/Content/themes/base/jquery.ui.datepicker.css" rel="stylesheet" type="text/css" />
	<link href="/Base/Content/themes/base/jquery.ui.progressbar.css" rel="stylesheet" type="text/css" />
	<link href="/Base/Content/themes/base/jquery.ui.theme.css" rel="stylesheet" type="text/css" />
--%>
</head>
<body>
  <div id="WebMainBodyDiv">
    <div class="PageContent">
        <div class="ApplicationTitle"><fmt:message key="login.label.pagetitle" bundle="${eceeptext}" /></div>
        <div class="logon">
			<form action="${pageContext.request.contextPath}/Default" method="post">                
			<fieldset>
	          <legend></legend>
	          <ol>
	            <li>
	              <span><fmt:message key="login.label.username" bundle="${eceeptext}" /></span>
	              <input name="username" type="text" data-val-required="The UserName field is required." />
	            </li>
	            <li>
	              <span><fmt:message key="login.label.password" bundle="${eceeptext}" /></span>
	              <input name="password" type="password" data-val-required="The Password field is required." />
	            </li>
	            <li>
	              <span><fmt:message key="login.label.language" bundle="${eceeptext}" /></span>
	              <select name="language" onchange="submit()">
	                <option value="en_CA" ${eCeepLanguage=="en_CA"? "selected" : ""}>Canada</option>
	                <option value="en_US" ${eCeepLanguage=="en_US"? "selected" : ""}>United States</option>
	                <option value="zh_CN" ${eCeepLanguage=="zh_CN"? "selected" : ""}>Chinese</option>
	              </select>
	              ${language}
	            </li>
	          </ol>
	          <fmt:message key="login.button.submit" bundle="${eceeptext}" var="txtSubmitButton" />
	          <input type="submit" value="${txtSubmitButton}" class="CusButton"/>
	        </fieldset>
	        </form>
	        <div class="LogonMessage">${ logon_message }</div>
        </div>
    </div>
  </div>
</body>
</html>
<script>
    function autoResizeDiv() {
        var winHeight = 0;

        if (window.innerHeight == undefined)
            winHeight = $(window).height();
        else
            winHeight = window.innerHeight;

        document.getElementById('WebMainBodyDiv').style.height = winHeight + 'px';
    }
    window.onresize = autoResizeDiv;
    autoResizeDiv();
</script>
