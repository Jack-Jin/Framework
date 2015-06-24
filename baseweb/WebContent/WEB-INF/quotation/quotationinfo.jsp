<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="f" %>

<f:layout withMilestone="true">

<style type="text/css">
	.Quotation_Index { width: 95%; margin-left: 10px; }
	
	/* Up Content */
	/* ---------------------------------------------------------------------------------------------------------------------------- */
	.Quotation_Content { width: 100%; }
	.Quotation_Content input[type="text"] { width: 210px; font-size: 0.95em; }
	.Quotation_Content select { width: 216px; }
	.Quotation_Content span { font-size: 0.95em; }
	
	.Quotation_CustomerInfo  
	{
	    width: 49%; 
	    float: left; 
	    padding: 2px 0px 2px 2px; 
	    border: 1px solid #aaa; 
	
	    border-radius: 0 10px 0 0;
	    -webkit-border-radius: 0 10px 0 0;
	    -moz-border-radius: 0 10px 0 0;
	}
	.Quotation_ProjectInfo  
	{
	    width: 49%; 
	    float: right; 
	    padding: 2px 0px 2px 2px; 
	    border: 1px solid #aaa; 
	
	    border-radius: 0 10px 0 0;
	    -webkit-border-radius: 0 10px 0 0;
	    -moz-border-radius: 0 10px 0 0;
	}
	
	/* Customer Info */
	#SearchCustomer, #NewCustomer, #ContactEdit, #ContactNew { width: 16px; height: 16px; cursor: hand; cursor: pointer; }
	#SearchCustomer:hover, #NewCustomer:hover, #ContactEdit:hover, #ContactNew:hover { outline: 1px solid blue; }
	
	.Quotation_CustomerInfo input[type="button"] { font-size: 5pt; width: 16px; height: 20px; }
	.Quotation_CustomerInfo>div { margin-bottom: 2px; }
	.Quotation_CustomerInfo>div>span:first-child { width: 35%; display: inline-block; font-weight: bold; }
	/* Project Info */
	.Quotation_ProjectInfo>div>span:first-child { width: 35%; display: inline-block; font-weight: bold; }
	.Quotation_ProjectInfo>div { margin-bottom: 2px; }
	
	/* Quotation Note */
	/* ---------------------------------------------------------------------------------------------------------------------------- */
	.Quotation_Note { width: 100%; margin-top: 2px; }
	.Quotation_Note>textarea { width: 99.2%; height: 50px; display: inline-block; }
	
	/* Product List */
	/* ---------------------------------------------------------------------------------------------------------------------------- */
	.Quotation_Products { width: 100%; margin-top: 5px; }
	.Quotation_Products>table { width: 100%; border-collapse: collapse;  }
	.Quotation_Products>table td, .Quotation_Products>table th  { font-size: 0.90em; padding: 2px 0 2px 0; border-right: 1px solid white; }
	.Quotation_Products>table>thead { background-color: #5D7B9D; }
	.Quotation_Products>table>thead th { color: White; }
	
	.StyleCurrentItem { background-color: #ffa; }
	
	.Quotation_Products input[type="text"] { width: 50px; } 
	.Quotation_Products select { width: 120px; }
	
	/************************************************* Pop window **********************************************************/
	/* Customer Search & New Pop Win */
	#winCustomerSearch>table { border-collapse: collapse; width:100%; margin-top: 5px; }
	#winCustomerSearch>table td { font-size: 0.85em; padding: 2px 0 2px 0;}
	#winCustomerSearch>table>thead>tr { background-color: blue; color: White; }
	#winCustomerSearch>table>tbody>tr>td:first-child>a { color: Blue; }
	#winCustomerSearch>table>tbody>tr:nth-child(odd) { background-color: #dfdfdf; }
	#winCustomerSearch>table>tbody>tr:nth-child(even) { background-color: #f1f1f1; }
	
	/* Customer New Pop Win */
	#winCustomerNew>div { margin-top:3px; }
	#winCustomerNew>div>span:first-child { width: 140px; display: inline-block; }
	#winCustomerNew>div>span:nth-child(3) { width: 60px; display: inline-block;margin-left:20px; }
	#winCustomerNew>div>input[type="text"] { width: 170px; }
	#winCustomerNew>div:first-child>input { width: 430px; }
	#winCustomerNew textarea { width: 570px; height: 50px; display: inline-block; }
	#winCustomerNew>div:last-child { text-align: right; }
	
	/* Contact Edit & New Pop Win */
	#winContactNewEdit>div { margin-top: 3px; }
	#winContactNewEdit>div>span { width: 150px; display: inline-block; }
	#winContactNewEdit>div>input[type="text"] { width: 200px; }
	#winContactNewEdit textarea { width: 350px; height: 30px; display: inline-block; }
	#winContactNewEdit>div:last-child { text-align: right; }
</style>

<div class="PageTitle">QUOTATION AND APPLICATION INFORMATION</div>
<%-- Message --%>
<div class="PageMessage"></div>

<div class="Quotation_Content">
  <%-- Customer Info --%>
  <div class="Quotation_CustomerInfo">
    <jsp:include page="/WEB-INF/quotation/quotationinfo-customer.jsp"/>
  </div>
  <%-- Quotation Info --%>
  <div class="Quotation_ProjectInfo">
    <jsp:include page="/WEB-INF/quotation/quotationinfo-project.jsp"/>
  </div>
  <div style="clear: both;"></div>
</div>

<div class="Quotation_Note">
    <div class="SubTitle">Miscellaneous Note</div>
    <textarea cols="20" id="QuotationNote" name="QuotationNote" rows="2">${quotationheader.quotationNote }</textarea>
</div>

</f:layout>
