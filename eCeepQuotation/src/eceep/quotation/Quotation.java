package eceep.quotation;

import java.util.List;

import eceep.milestone.Milestone;
import eceep.milestone.Step;
import eceep.quotation.domain.*;

public interface Quotation {
	boolean initial(String jdbcDriver, String url, String userName, String password);
	
	QuotationHeaderDetail getQuotationHeader();
	
	Milestone<Step> getMilestone();

    // Quotation Items
    List<QuotationItemDetail> getQuotationItems();
    String getQuotationItemsCurrentID();
}
