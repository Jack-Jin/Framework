/*******************************************************************
* Global Variables
*******************************************************************/
//Root Path
//var scriptBaseUrl = document.location.protocol + '//' + document.location.hostname + ":" + document.location.port + '<% =ResolveUrl("~/") %>';
//var scriptBaseUrl = document.location.protocol + '//' + document.location.hostname + ":" + document.location.port + '@Url.Content("~")';

/*******************************************************************
 * autoResizeDiv() - Auto size layout, match for window size.
 *******************************************************************/
function autoResizeDiv() {
    var winHeight = 0;

    if (window.innerHeight == undefined)
        winHeight = $(window).height();
    else
        winHeight = window.innerHeight;

    document.getElementById('WebMainBodyDiv').style.height = winHeight + 'px';

    var rightHeight = winHeight - 200;
    document.getElementById('id_page_body_right_content').style.height = rightHeight + 'px';
}
window.onresize = autoResizeDiv;
autoResizeDiv();

/*******************************************************************
 * post(path, params, method)
 * for example: post('/contact/', {name: 'Johnny Bravo'});
 *******************************************************************/
function post(path, params, method) {
    method = method || "post"; // Set method to post by default if not specified.

    // The rest of this code assumes you are not using a library.
    // It can be made less wordy if you use one.
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", path);

    for(var key in params) {
        if(params.hasOwnProperty(key)) {
            var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", key);
            hiddenField.setAttribute("value", params[key]);

            form.appendChild(hiddenField);
         }
    }

    document.body.appendChild(form);
    form.submit();
}

/*******************************************************************
* Page Load
$(document).ready(function () {
    $.get(scriptBaseUrl + 'Main/GetText', { textCode: 'Log Out', textComment: 'Log Out' }, function (t) {
        $(".MenuLogout a").text(t);
    });

    //Set logout button.
    $("#btnLogout").attr("href", scriptBaseUrl + 'Main/Logout');

    //decorate menu
    ArrowGreenMenu("menuID");

    // Milestone
    if (typeof PageName == "undefined") {
        $.ajaxSetup({ cache: false });
        $.ajax({
            url: scriptBaseUrl + 'Main/Milestone',
            success: function (data) {
                $(".page_body_right_top").html(data);
            },
            async: false
        });
    }
    else {
        if (PageName == "CustomerManager" || PageName == "MyProfile" || PageName == "CompanyAdmin" || PageName == "InfoCenter" || PageName == "NewsPost" ||
            PageName == "QuotationList" || PageName == "DataUpdate") {

            $(".page_body_right_top").hide();
        }
        else {
        }
    }
	
    // Application Version
    $.get(scriptBaseUrl + "Main/GetBaseVersions", {}, function (data) {
        var version = "(" + data.baseWebVersion + " · " + data.dbVersion + ")";
        $(".page_footer_right").append(version);
    });

    if (typeof PageName != "undefined" && PageName == "Quotation Info") {
        $("#menu-Save").click(function () {
            SaveUpdateQuotationInfo();
        });
    }
});
 *******************************************************************/

/*******************************************************************
* ArrowGreenMenu() - Menu
function ArrowGreenMenu(menuID) {
    $("#" + menuID).addClass("arrowGreenMenu");

    //Style sheet class
    $("#" + menuID + " li>a").addClass("arrowGreenMenuItem");
    $("#" + menuID + " li>a").hover(function () {
        $(this).toggleClass("arrowGreenMenuFocus");
    });

    $("#" + menuID + " li>a").click(function () {
        $("#" + menuID + " li>a").removeClass("arrowGreenMenuSelected");
        $(this).toggleClass("arrowGreenMenuSelected");
    });
}
 *******************************************************************/

/*******************************************************************
* Menu - Save
function SaveUpdateQuotationInfo() {

}
 *******************************************************************/