package eceep.quotation.domain;

import java.math.BigDecimal;

import eceep.quotation.*;

public class QuotationItemDetail {
	private String id;

	private int sequence;

	private String itemName;
	private String itemRevision;

	private EnumProductType productType;
	private EnumProductApplicationType productApplicationType;
	private EnumIndustryType industryType;
	private BigDecimal cost;
	private BigDecimal price;

	private int unitID;
	private int currencyID;

	private Product product;

	QuotationItemDetail() {
		this.id = "";
		this.sequence = -1;
		this.unitID = -1;
		this.currencyID = -1;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemRevision() {
		return itemRevision;
	}

	public void setItemRevision(String itemRevision) {
		this.itemRevision = itemRevision;
	}

	public EnumProductType getProductType() {
		return productType;
	}

	public void setProductType(EnumProductType productType) {
		this.productType = productType;
	}

	public EnumProductApplicationType getProductApplicationType() {
		return productApplicationType;
	}

	public void setProductApplicationType(EnumProductApplicationType productApplicationType) {
		this.productApplicationType = productApplicationType;
	}

	public EnumIndustryType getIndustryType() {
		return industryType;
	}

	public void setIndustryType(EnumIndustryType industryType) {
		this.industryType = industryType;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getUnitID() {
		return unitID;
	}

	public void setUnitID(int unitID) {
		this.unitID = unitID;
	}

	public int getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(int currencyID) {
		this.currencyID = currencyID;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
