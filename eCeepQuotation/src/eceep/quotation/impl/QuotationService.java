package eceep.quotation.impl;

import java.util.List;

import eceep.milestone.Milestone;
import eceep.milestone.Step;
import eceep.quotation.Quotation;
import eceep.quotation.dao.QuotationDao;
import eceep.quotation.domain.QuotationHeaderDetail;
import eceep.quotation.domain.QuotationItemDetail;

public class QuotationService implements Quotation {
	private QuotationDao dao;
	
	private QuotationHeaderDetail quotationHeader;
	
	private Milestone<Step> milestone;
	
	private List<QuotationItemDetail> quotationItems;
	private String  quotationItemsCurrentID;
	
	@Override
	public boolean initial(String jdbcDriver, String url, String userName, String password) {
		this.dao = new QuotationDao();
		
		return dao.initial(jdbcDriver, url, userName, password);
	}

	@Override
	public QuotationHeaderDetail getQuotationHeader() {
		return this.quotationHeader;
	}

	@Override
	public Milestone<Step> getMilestone() {
		return this.milestone;
	}

	@Override
	public List<QuotationItemDetail> getQuotationItems() {
		return this.quotationItems;
	}

	@Override
	public String getQuotationItemsCurrentID() {
		return this.quotationItemsCurrentID;
	}
	
}
