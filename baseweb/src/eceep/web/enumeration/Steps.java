package eceep.web.enumeration;

import eceep.milestone.Step;
import eceep.milestone.impl.StepDefault;

public enum Steps {
	QuotationInfo("QuotationInfo", "Quotation Info", "/QuotationInfo"), 
	Operating("Operating", "Operating", "/Operating"), 
	Product1("Product1", "Product1", ""), 
	Product2("Product2", "Product2", "");

	private Step step;

	private Steps(String name, String title, String uri) {
		this.step = new StepDefault(name, title, uri, false);
	}

	public Step getStep() {
		return this.getStep("");
	}

	public Step getStep(String quotationItemID) {
		if (quotationItemID==null || quotationItemID.isEmpty())
			return this.step;

		String name = this.step.getName() + quotationItemID;
		String title = this.step.getTitle();
		String uri = this.step.getURI();
		boolean active = this.step.isActive();

		this.step = new StepDefault(name, title, uri, active);

		return this.step;
	}
}
