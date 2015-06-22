package eceep.quotation.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eceep.milestone.Milestone;
import eceep.milestone.Step;
import eceep.milestone.impl.MilestoneService;
import eceep.quotation.Product;
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

	private final int quotationNumberLength = 5;

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
	public boolean newQuotation(boolean generateQuoteNumber, QuotationHeaderDetail quotationHeader,
			Milestone<Step> milestone) throws SQLException {
		this.quotationHeader = quotationHeader;

		this.milestone = milestone;

		if (generateQuoteNumber) {
			String number = generateQuoteNumber();
			this.quotationHeader.setQuotationNo(number);
		}

		return true;
	}

	@Override
	public boolean newQuotation(QuotationHeaderDetail quotationHeader, Milestone<Step> milestone) throws SQLException {
		return newQuotation(true, quotationHeader, milestone);
	}

	@Override
	public QuotationItemDetail newQuotationItem(Class<? extends QuotationItemDetail> clazz, Product product)
			throws InstantiationException, IllegalAccessException {
		if (this.quotationHeader == null)
			return null;

		// Create quotation item.
		QuotationItemDetail item = clazz.newInstance();
		item.setSequence(this.quotationItems.size());

		// Set product
		item.setProduct(product);
		item.setUnitID(this.quotationHeader.getUnitID());
		item.setCurrencyID(this.quotationHeader.getCurrencyID());

		// Insert item
		this.quotationItems.add(item);

		// Set new item as current.
		this.quotationItemsCurrentID = item.getId();

		return item;
	}

	@Override
	public boolean saveQuotation(boolean generateQuoteNumber, int byUserID) throws SQLException, IOException {
		if (generateQuoteNumber) {
			String number = generateQuoteNumber();
			this.quotationHeader.setQuotationNo(number);
		}

		return this.dao.saveQuotation(byUserID, this.quotationHeader, this.milestone, this.quotationItems,
				this.quotationItemsCurrentID);
	}

	@Override
	public boolean saveQuotation(int byUserID) throws SQLException, IOException {
		return saveQuotation(false, byUserID);
	}

	@Override
	public boolean restoreQuotation(int quotationID) throws SQLException, IOException, ClassNotFoundException {
		boolean ok = false;

		Object[] result = this.dao.restoreQuotation(quotationID);

		if (result[0] != null) {
			this.quotationHeader = (QuotationHeaderDetail) result[0];
			ok = true;
		}

		if (result[1] != null) {
			this.milestone = (Milestone) result[1];
			ok &= true;
		}

		this.quotationItems = (List<QuotationItemDetail>) result[2];
		ok &= true;

		if (result[1] != null) {
			this.quotationItemsCurrentID = (String) result[3];
			ok &= true;
		}

		return true;
	}

	@Override
	public boolean removeQuotation(int quotationID, int byUserID) throws SQLException {
		return this.dao.removeQuotation(quotationID, byUserID);
	}

	@Override
	public void removeQuotationItem(String quotationItemID) throws SQLException {
		this.quotationItems.removeIf(A -> A.getId().equalsIgnoreCase(quotationItemID));

		for (int i = 0; i < this.quotationItems.size(); i++) {
			this.quotationItems.get(i).setSequence(i);
		}
		
		this.quotationItemsCurrentID ="";
		if(this.quotationItems.size()>0)
			this.quotationItemsCurrentID = this.quotationItems.get(0).getId();
	}

	/* Methods */
	/* ----------------------------------------------------------------- */
	private String generateQuoteNumber() throws SQLException {
		return this.dao.generateQuoteNumber(this.quotationNumberLength);
	}

}
