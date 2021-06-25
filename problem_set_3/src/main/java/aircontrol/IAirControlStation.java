//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

package aircontrol;

import aircontrol.airline.IAirlineListener;
import aircontrol.airline.IAirlineObservable;
import aircontrol.filter.INotificationFilter;
import rbs.flight.IFlight;

public interface IAirControlStation {
	public void addNotificationFilter(IAirlineListener airlineListener, IAirlineObservable airlineObservable, INotificationFilter<IFlight> filter);
	
	public void registerAirlineListenerAtAirlineObservable(IAirlineListener airlineListener, IAirlineObservable airlineObservable);
}
