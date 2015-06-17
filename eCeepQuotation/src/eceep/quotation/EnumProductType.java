package eceep.quotation;

public enum EnumProductType {
	BrazePak(1), GasketedPHE(2), GasketedPHEMulti(3), TwistedTube(4), ShellTube(5), PlateAndShell(6), WideGap(7), CrossFlow(8), PlateFin(9);

	private int id;

	private EnumProductType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
