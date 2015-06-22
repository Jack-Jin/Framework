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

	boolean newQuotation(boolean generateQuoteNumber, QuotationHeaderDetail quotationHeader, Milestone<Step> milestone) throws SQLException;
	
	boolean newQuotation(QuotationHeaderDetail quotationHeader, Milestone<Step> milestone) throws SQLException;

	QuotationItemDetail newQuotationItem(Class<? extends QuotationItemDetail> clazz, Product product) throws InstantiationException, IllegalAccessException;
	
    boolean saveQuotation(boolean generateQuoteNumber, int byUserID) throws SQLException, IOException;

    boolean saveQuotation(int byUserID) throws SQLException, IOException;
    
    boolean restoreQuotation(int quotationID) throws SQLException, IOException, ClassNotFoundException;
    
    boolean removeQuotation(int quotationID, int byUserID) throws SQLException;
    
    void removeQuotationItem(String quotationItemID) throws SQLException;
}
