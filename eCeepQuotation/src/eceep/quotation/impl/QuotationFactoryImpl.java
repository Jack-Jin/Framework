package eceep.quotation.impl;

import eceep.quotation.Quotation;
import eceep.quotation.QuotationFactory;

public class QuotationFactoryImpl implements QuotationFactory {
	private static Quotation quotation;

	public static Quotation getInstance() {
		if (quotation == null)
			quotation = new QuotationService();

		return quotation;
	}

	@Override
	public Quotation getQuotation() {
		return new QuotationService();
	}

}
