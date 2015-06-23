package eceep.quotation.impl;

import eceep.quotation.Quotation;
import eceep.quotation.QuotationFactory;

public class QuotationFactoryImpl implements QuotationFactory {
	private static QuotationFactory quotationFactory;

	public static Quotation getInstance() {
		if (quotationFactory == null)
			quotationFactory = new QuotationFactoryImpl();

		return quotationFactory.getQuotation();
	}

	@Override
	public Quotation getQuotation() {
		return new QuotationService();
	}

}
