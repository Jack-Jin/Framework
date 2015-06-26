<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="SubTitle">Customer Information</div>
<div>
    <span>Customer Search</span>
    <span>
        <input id="CustomerSearch" type="text" />
        <img id="SearchCustomer" title="Search Customer" alt="Search Customer" src="${pageContext.request.contextPath }/base/css/images/Start-Menu-Search-icon.png" />
        <img id="NewCustomer" title="New Customer" alt="New Customer" src="${pageContext.request.contextPath }/base/css/images/new-file-icon.png" />
    </span>
</div>
<div>
    <span>Customer Name:</span>
    <input type="text" name="customerName" value="${quotationheader.customerName }" />
    <input type="hidden" name="customerID" value="-1" />
</div>
<div>
    <span>Customer Address:</span>
    <input type="text" name="customerAddress" value="${quotationheader.customerAddress }" />
</div>
<div>
    <span>Customer Phone:</span>
    <input type="text" name="customerPhone" value="${quotationheader.customerPhone }" />
</div>
<div>
    <span>Customer Fax:</span>
    <input type="text" name="customerFax" value="${quotationheader.customerFax }" />
</div>
<div>
    <span>Contact:</span>
    <select name="contactID">
      <c:forEach var="item" items="${customercontacts }">
        <option value="${item.id }" selected='${item.id==customercontactsselectedID? "selected" : "" }'>${item.customerName }</option>
      </c:forEach>
    </select>
    
    <span>
        <img id="ContactEdit" title="Edit this Contact" src="${pageContext.request.contextPath }/base/css/images/Text-Edit-icon.png" alt="Contact Edit" />
        <img id="ContactNew" title="New Customer Contact" src="${pageContext.request.contextPath }/base/css/images/new-file-icon.png" alt="New Contact" />
    </span>
</div>

<%-- Pop up window: customer search 
<div id="winCustomerSearch">
    <div>
        <span>Customer Name</span>
        <input id="CustomerNameSearch" type="text" />
        <input id="CustomerNameSearch_Search" type="button" value="Search" />
    </div>
    <table>
        <thead>
          <tr>
            <td>Customer Name</td>
            <td>Contact</td>
            <td>Address</td>
            <td>City</td>
            <td>State</td>
            <td> Code</td>
            <td>Country</td>
            <td>Phone</td>
            <td>Fax</td>
          </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
--%>

<%-- Pop up window: new customer 
<div id="winCustomerNew">
    <div><span id="CustomerNew_lbCompanyName">Company Name *</span><input id="CustomerNew_CompanyName" type="text" /></div>
    <div><span id="CustomerNew_lbStreet">Street</span><input id="CustomerNew_Street" type="text" /></div>
    <div><span id="CustomerNew_lbCity">City</span><input id="CustomerNew_City" type="text" /></div>
    <div>
         <span id="CustomerNew_lbState">State / Province</span><input id="CustomerNew_State" type="text" />
         <span id="CustomerNew_lbCountry">Country</span><input id="CustomerNew_Country" type="text" />
    </div>
    <div><span id="CustomerNew_lbZip">Zip / Postal Code</span><input id="CustomerNew_PostalCode" type="text" /></div>
    <div>
         <span id="CustomerNew_lbPhone">Phone</span><input id="CustomerNew_Phone" type="text" />
         <span id="CustomerNew_lbFax">Fax</span><input id="CustomerNew_Fax" type="text" />
    </div>
    <div><span id="CustomerNew_lbNote">Note</span><textarea id="CustomerNew_Note" cols="2"></textarea></div>
    <hr />
    <div>
        <input id="CustomerNew_btnSave" type="button" value="Save" />
        <input id="CustomerNew_btnCancel" type="button" value="Cancel"  />
    </div>
</div>
--%>

<%-- Pop up window: contact new or edit 
<div id="winContactNewEdit">
    <div>
      <span id="ContactNewEdit_lbNamePrefix">en-CA:Prefix</span>
      <select id="ContactNewEdit_txtNamePrefix">
        <option value="Mr.">Mr.</option>
        <option value="Mrs.">Mrs.</option>
      </select>
    </div>
    <div><span id="ContactNewEdit_lbContactName">Contact Name</span><input id="ContactNewEdit_txtContactName" type="text" /></div>
    <div><span id="ContactNewEdit_lbPrimaryContract">Primary Contact</span><input id="ContactNewEdit_chkPrimaryContract" type="checkbox" /></div>
    <div><span id="ContactNewEdit_lbContactTitle">Contact Title</span><input id="ContactNewEdit_txtContactTitle" type="text" /></div>
    <div><span id="ContactNewEdit_lbDirectPhoneNo">Direct Phone No.</span><input id="ContactNewEdit_txtDirectPhoneNo" type="text" /></div>
    <div><span id="ContactNewEdit_lbDirectFaxNo">Direct Fax No.</span><input id="ContactNewEdit_txtDirectFaxNo" type="text" /></div>
    <div><span id="ContactNewEdit_lbEmailAddress">Email Address</span><input id="ContactNewEdit_txtEmailAddress" type="text" /></div>
    <div><span id="ContactNewEdit_lbNote">Note</span></div>
    <div><textarea id="ContactNewEdit_txtNote" cols="2" rows="1"></textarea></div>
    <hr />
    <div>
        <input id="ContactNewEdit_btnSave" type="button" value="Save" />
        <input id="ContactNewEdit_btnCancel" type="button" value="Cancel" />
    </div>
</div>
--%>