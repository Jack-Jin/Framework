package eceep.quotation;

public enum EnumProductApplicationType {
    SinglePhase(0),
    Condenser(1),
    Evaporator(2),
    CondenserEvaporator(3),
    RefrigerationCondenser(4),
    RefrigerationEvaporator(5),
    RefrigerationCondenserEvaporator(6),
    ProcessCondenser(7),
    SinglePhaseVaporBreakOut(8),
    SinglePhaseBatch(9),
    MoistAirEnergyRecovery(10),
    MilkPasteurizer(11),
    SteamHeater(12),
    Refrigeration(13),
    EnergyBank(14),
    ViexAsia(15),
    OneComCondenser(16),
    PartialCondenser(17),
    ProcessEvaporator(18);
    
    private int id;
    
    EnumProductApplicationType(int id) {
    	this.id = id;
    }
    
	public int getId() {
		return id;
	}
}
