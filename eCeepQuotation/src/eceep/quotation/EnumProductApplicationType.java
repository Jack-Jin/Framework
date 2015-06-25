package eceep.quotation;

public enum EnumProductApplicationType {
    SinglePhase(0, "Single Phase"),
    Condenser(1, "Condenser"),
    Evaporator(2, "Evaporator"),
    CondenserEvaporator(3, "Condenser Evaporator"),
    RefrigerationCondenser(4, "Refrigeration Condenser"),
    RefrigerationEvaporator(5, "Refrigeration Evaporator"),
    RefrigerationCondenserEvaporator(6, "Refrigeration Condenser Evaporator"),
    ProcessCondenser(7, "Process Condenser"),
    SinglePhaseVaporBreakOut(8, "Single Phase Vapor Break Out"),
    SinglePhaseBatch(9, "Single Phase Batch"),
    MoistAirEnergyRecovery(10, "MoistAir Energy Recovery"),
    MilkPasteurizer(11, "Milk Pasteurizer"),
    SteamHeater(12, "Steam Heater"),
    Refrigeration(13, "Refrigeration"),
    EnergyBank(14, "Energy Bank"),
    ViexAsia(15, "Viex Asia"),
    OneComCondenser(16, "OneCom Condenser"),
    PartialCondenser(17, "Partial Condenser"),
    ProcessEvaporator(18, "Process Evaporator");
    
    private int id;
    private String label;
    
    EnumProductApplicationType(int id, String label) {
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
