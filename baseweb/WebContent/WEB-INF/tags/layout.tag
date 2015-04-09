<%@ tag description="Page freamework" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
    <link href="${ pageContext.request.contextPath }/base/css/Site.css" rel="stylesheet" type="text/css" />
    <link href="${ pageContext.request.contextPath }/base/css/Site_Controls.css" rel="stylesheet" type="text/css" />
    <link href="${ pageContext.request.contextPath }/base/css/_Layout.css" rel="stylesheet" type="text/css" />
    <link href="${ pageContext.request.contextPath }/base/css/Home_MainMenu.css" rel="stylesheet" type="text/css" />
<%-- 
    <script src="${ pageContext.request.contextPath }/Scripts/jquery-1.6.2.min.js" type="text/javascript"/>   
--%>    
</head>
<body>
    <div id="WebMainBodyDiv" class="page">
        <!-- Page Header -->
        <div class="page_header">
            <div>eCeep Framework Demo</div>
            <div></div>
        </div>
        <!-- Page Body -->
        <div class="page_body">
            <div class="page_body_left">
                <div class="MenuLogout" >
                    <a id="btnLogout" href="${ pageContext.request.contextPath }/index.html">Log Out</a>
                </div>
                <div class="page_left_menu">
                	<jsp:include page="/WEB-INF/menu/syswebmenu.jsp" />
                </div>
            </div>
            <div id="id_page_body_right" class="page_body_right">
                <div class="page_body_right_top">
                </div>
                <div id="id_page_body_right_content" class="page_body_right_content">
				  <jsp:doBody />
                </div>
            </div>
            <div style="clear:both;"></div>
        </div>
        <!-- Page Footer -->
        <div class="page_footer">
            <div>
                <div class="page_footer_left">
                    &nbsp;
                </div>
                <div class="page_footer_right">
                </div>
                <div style="clear:both;"></div>
            </div>
        </div>
    </div>
</body>
<script src="${ pageContext.request.contextPath }/base/script/global.js" type="text/javascript"></script>
</html>
