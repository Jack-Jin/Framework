<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<% if(false) { %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<% } %>
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