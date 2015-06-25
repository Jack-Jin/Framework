package eceep.quotation;

public enum EnumIndustryType {
	AHRIStandard(1, "AHRI Standard"), 
	HVACCommercial(2, "HVAC Commercial"), 
	Industrial(3, "Industrial"), 
	Marine(4, "Marine"), 
	Food(5, "Food");

	private int id;
	private String label;

	EnumIndustryType(int id, String label) {
		this.id = id;
		this.label = label;
	}

	public int getId() {
		return id;
	}

	public String getLabel() {
		return this.label;
	}
}
