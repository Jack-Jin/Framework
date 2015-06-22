package eceep.quotation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import eceep.milestone.Milestone;
import eceep.milestone.Step;
import eceep.quotation.domain.*;

public interface Quotation {
	QuotationHeaderDetail getQuotationHeader();

	Milestone<Step> getMilestone();

	List<QuotationItemDetail> getQuotationItems(); // Quotation Items

	String getQuotationItemsCurrentID();

	boolean initial(String jdbcDriver, String url, String userName, String password);

	boolean newQuotation(boolean hasQuoteNumber, QuotationHeaderDetail quotationHeader, Milestone<Step> milestone) throws SQLException;

	boolean newQuotation(boolean hasQuoteNumber, QuotationHeaderDetail quotationHeader) throws SQLException;

	boolean newQuotation(boolean hasQuoteNumber) throws SQLException;
	
	boolean newQuotation() throws SQLException;
	
    boolean saveQuotation(int byUserID) throws SQLException, IOException;

    boolean restoreQuotation(int quotationID) throws SQLException;
}
