package eceep.quotation.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eceep.milestone.Milestone;
import eceep.milestone.Step;
import eceep.milestone.impl.MilestoneService;
import eceep.quotation.Quotation;
import eceep.quotation.dao.QuotationDao;
import eceep.quotation.domain.QuotationHeaderDetail;
import eceep.quotation.domain.QuotationItemDetail;

public class QuotationService implements Quotation {
	/* Fields */
	/* ----------------------------------------------------------------- */
	private QuotationDao dao;

	private QuotationHeaderDetail quotationHeader;

	private Milestone<Step> milestone;

	private List<QuotationItemDetail> quotationItems;
	private String quotationItemsCurrentID;

	/* Methods */
	/* ----------------------------------------------------------------- */
	@Override
	public boolean initial(String jdbcDriver, String url, String userName, String password) {
		quotationItems = new ArrayList<QuotationItemDetail>();
		quotationItemsCurrentID = "";

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

	@Override
	public boolean newQuotation(boolean hasQuoteNumber, QuotationHeaderDetail quotationHeader, Milestone<Step> milestone)
			throws SQLException {
		final int quotationNumberLength = 5;

		this.quotationHeader = quotationHeader;

		this.milestone = milestone;

		if (hasQuoteNumber) {
			String number = generateQuoteNumber(quotationNumberLength);
			this.quotationHeader.setQuotationNo(number);
		}

		return true;
	}

	@Override
	public boolean newQuotation(boolean hasQuoteNumber, QuotationHeaderDetail quotationHeader) throws SQLException {
		Milestone<Step> milestone = MilestoneService.getInstance();

		return newQuotation(hasQuoteNumber, quotationHeader, milestone);
	}

	@Override
	public boolean newQuotation(boolean hasQuoteNumber) throws SQLException {
		QuotationHeaderDetail quotationHeader = new QuotationHeaderDetail();

		Milestone<Step> milestone = MilestoneService.getInstance();

		return newQuotation(hasQuoteNumber, quotationHeader, milestone);
	}

	@Override
	public boolean newQuotation() throws SQLException {
		QuotationHeaderDetail quotationHeader = new QuotationHeaderDetail();

		Milestone<Step> milestone = MilestoneService.getInstance();

		return newQuotation(true, quotationHeader, milestone);
	}

	/* Methods */
	/* ----------------------------------------------------------------- */
	private String generateQuoteNumber(int length) throws SQLException {
		return this.dao.generateQuoteNumber(length);
	}

	@Override
	public boolean saveQuotation(int byUserID) throws SQLException, IOException {
		return this.dao.saveQuotation(byUserID, this.quotationHeader, this.milestone, this.quotationItems, this.quotationItemsCurrentID);
	}

	@Override
	public boolean restoreQuotation(int quotationID) throws SQLException {
		this.quotationHeader = this.dao.getQuotationHeader(quotationID);
		
		this.milestone = this.dao.getMilestone(quotationID);
		
		this.quotationItems = this.dao.getQuotationItems(quotationID);
		
		this.quotationItemsCurrentID = this.dao.getQuotationItemsCurrentID(quotationID);
		
		return true;
	}

}
