package eceep.web.enumeration;

import eceep.milestone.Step;
import eceep.milestone.impl.StepDefault;

public enum Steps {
	QuotationInfo("QuotationInfo", "Quotation Info", "/QuotationInfo", true), 
	Step1("Step1", "Step1", "/Step1"), 
	Step2("Step2", "Step2", "/Step2"), 
	Step3("Step3", "Step3", "/Step3");

	private Step step;

	private Steps(String name, String title, String uri) {
		this.step = new StepDefault(name, title, uri, false);
	}

	private Steps(String name, String title, String uri, boolean active) {
		this.step = new StepDefault(name, title, uri, active);
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
