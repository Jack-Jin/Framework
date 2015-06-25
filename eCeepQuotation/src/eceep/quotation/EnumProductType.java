package eceep.quotation;

public enum EnumProductType {
	BrazePak(1, "Braze"), 
	GasketedPHE(2, "Gasketed PHE"), 
	GasketedPHEMulti(3, "Gasketed PHE Multi"), 
	TwistedTube(4, "Twisted Tube"), 
	ShellTube(5, "Shell and Tube"), 
	PlateAndShell(6, "Plate and Shell"), 
	WideGap(7, "Wide Gap"), 
	CrossFlow(8, "Cross Flow"), 
	PlateFin(9, "Plate Fin");

	private int id;
	private String label;

	private EnumProductType(int id, String label) {
		this.id = id;
		this.label = label;
	}

	public int getId() {
		return this.id;
	}

	public String getLabel() {
		return this.label;
	}
}
