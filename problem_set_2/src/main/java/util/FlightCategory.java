package util;

public enum FlightCategory {
	DEFAULT("DEFAULT"), 
	DUAL("DUAL"), 
	ONEWAY("ONEWAY");
	
	private final String label;
	
	private FlightCategory(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
}
