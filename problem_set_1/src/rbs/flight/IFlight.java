package rbs.flight;

import ict.basics.IDeepCopy;

//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

public interface IFlight extends IDeepCopy {
	
	public String getFlightId();
	
	public float getPrice();
	
	public String getDestination();
	
	public String getDeparture();
	
	public IFlight deepCopy();
	
}
