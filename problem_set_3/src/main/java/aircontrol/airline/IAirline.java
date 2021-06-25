//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

package aircontrol.airline;

import rbs.flight.IFlight;

public interface IAirline {
	public boolean	addFlight(IFlight flight);
	
	public boolean	addFlights(java.util.Collection<IFlight> flights);
	
	public boolean	removeFlight(IFlight flight);
	
	public boolean updateFlight(IFlight flight);
	
	public String getName();
}
