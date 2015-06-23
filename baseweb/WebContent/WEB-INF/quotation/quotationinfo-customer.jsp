<%@ page language="java" pageEncoding="UTF-8"%>

<div class="SubTitle">Customer Information</div>
<div>
    <span>Customer Search</span>
    <span>
        <input id="CustomerSearch" type="text" />
        <img id="SearchCustomer" title="Search Customer" alt="Search Customer" src="/base/base/css/images/Start-Menu-Search-icon.png" />
        <img id="NewCustomer" title="New Customer" alt="New Customer" src="/base/base/css/images/new-file-icon.png" />
    </span>
</div>
<div>
    <span>Customer Name:</span>
    <input id="CustomerName" name="CustomerName" type="text" value="" />
    <input data-val="true" data-val-number="The field CustomerID must be a number." data-val-required="The CustomerID field is required." id="CustomerID" name="CustomerID" type="hidden" value="-1" />
</div>
<div>
    <span>Customer Address:</span>
    <input id="CustomerAddress" name="CustomerAddress" type="text" value="" />
</div>
<div>
    <span>Customer Phone:</span>
    <input id="CustomerPhone" name="CustomerPhone" type="text" value="" />
</div>
<div>
    <span>Customer Fax:</span>
    <input id="CustomerFax" name="CustomerFax" type="text" value="" />
</div>
<div>
    <span>Contact:</span>
    <select data-val="true" data-val-number="The field ContactID must be a number." data-val-required="The ContactID field is required." id="ddlContactList" name="ContactID"></select>
    <input id="CustomerContactName" name="ContactName" type="hidden" value="" />
    <span>
        <img id="ContactEdit" title="Edit this Contact" src="/base/base/css/images/Text-Edit-icon.png" alt="Contact Edit" />
        <img id="ContactNew" title="New Customer Contact" src="/base/base/css/images/new-file-icon.png" alt="New Contact" />
    </span>
</div>

<%-- Pop up window: customer search --%>
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

<%-- Pop up window: new customer --%>
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

<%-- Pop up window: contact new or edit --%>
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
