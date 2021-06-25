//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

package aircontrol.airline;

import rbs.flight.IFlight;

public interface IAirlineListener extends IListener<IFlight> {
	public void flightAdded(IFlight flight);
	
	public void	flightRemoved(IFlight flight);
	
	public void notifyChange(IFlight flight);
}
