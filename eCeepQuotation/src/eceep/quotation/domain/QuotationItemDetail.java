package eceep.quotation.domain;

import java.math.BigDecimal;
import java.util.Date;

import eceep.quotation.*;

public class QuotationItemDetail {
	private String id;
	private String name;
	private String revision;

	private int sequence;
	
	private EnumProductType productType;
	private EnumProductApplicationType productApplicationType;
	private EnumIndustryType industryType;
	private BigDecimal cost;
	private BigDecimal price;

	private int unitID;
	private int currencyID;

	private Product product;

	private int createdByID;
	private String createdByName;
	private Date createdTime;

	private int modifiedByID;
	private String modifiedByName;
	private Date modifiedTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
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

	public int getCreatedByID() {
		return createdByID;
	}

	public void setCreatedByID(int createdByID) {
		this.createdByID = createdByID;
	}

	public String getCreatedByName() {
		return createdByName;
	}

	public void setCreatedByName(String createdByName) {
		this.createdByName = createdByName;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public int getModifiedByID() {
		return modifiedByID;
	}

	public void setModifiedByID(int modifiedByID) {
		this.modifiedByID = modifiedByID;
	}

	public String getModifiedByName() {
		return modifiedByName;
	}

	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

}
