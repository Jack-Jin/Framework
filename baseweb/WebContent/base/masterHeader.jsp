<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
    <link href="base/css/Site.css" rel="base/css/site.css" type="text/css" />
    <link href="base/css/Site.css" rel="base/css/Site_Controls.css" type="text/css" />
    <link href="base/css/Site.css" rel="base/css/_Layout.css" type="text/css" />
    <link href="base/css/Site.css" rel="base/css/Home_MainMenu.css" type="text/css" />
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
                    <a id="btnLogout">Log Out</a>
                </div>
                <div class="page_left_menu">
                </div>
            </div>
            <div id="id_page_body_right" class="page_body_right">
                <div class="page_body_right_top">
                </div>
                <div id="id_page_body_right_content" class="page_body_right_content">
                  <form id="form1" runat="server">
                     <asp:ContentPlaceHolder ID="ctHolder" runat="server"></asp:ContentPlaceHolder>
                  </form>
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




<% if(false) { %>
</body>
</html>
<% } %>