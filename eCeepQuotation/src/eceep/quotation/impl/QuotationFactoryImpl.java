package eceep.quotation.impl;

import eceep.quotation.Quotation;
import eceep.quotation.QuotationFactory;

public class QuotationFactoryImpl implements QuotationFactory {

	@Override
	public Quotation getQuotation() {
		return new QuotationService();
	}

}
