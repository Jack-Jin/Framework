package eceep.quotation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import eceep.milestone.Milestone;
import eceep.milestone.Step;
import eceep.quotation.domain.*;

public interface Quotation {
	boolean initial(String jdbcDriver, String url, String userName, String password);

	boolean newQuotation(boolean generateQuoteNumber, Class<? extends QuotationHeaderDetail> clazzQuotationHeader, Milestone<Step> milestone) throws SQLException, InstantiationException, IllegalAccessException;
	
	boolean newQuotation(Class<? extends QuotationHeaderDetail> clazzQuotationHeader, Milestone<Step> milestone) throws SQLException, InstantiationException, IllegalAccessException;

	QuotationItemDetail newQuotationItem(Class<? extends QuotationItemDetail> clazz, Product product) throws InstantiationException, IllegalAccessException;
	
    boolean saveQuotation(boolean generateQuoteNumber, int byUserID) throws SQLException, IOException;

    boolean saveQuotation(int byUserID) throws SQLException, IOException;
    
    boolean restoreQuotation(int quotationID) throws SQLException, IOException, ClassNotFoundException;
    
    boolean removeQuotation(int quotationID, int byUserID) throws SQLException;
    
    void removeQuotationItem(String quotationItemID) throws SQLException;

    // ---
    QuotationHeaderDetail getQuotationHeader();
    
    Milestone<Step> getMilestone();
    
    List<QuotationItemDetail> getQuotationItems(); // Quotation Items
    
    String getQuotationItemsCurrentID();
    
	QuotationItemDetail getQuotationItem();
}
