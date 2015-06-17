package eceep.quotation;

public enum EnumIndustryType {
	AHRIStandard(1), HVACCommercial(2), Industrial(3), Marine(4), Food(5);

	private int id;

	EnumIndustryType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
