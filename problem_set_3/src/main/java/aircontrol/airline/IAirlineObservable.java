//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

package aircontrol.airline;

import aircontrol.filter.INotificationFilter;
import rbs.flight.IFlight;

public interface IAirlineObservable extends IObservable<IFlight>{
	public void	addNotificationFilter(IAirlineListener listener, INotificationFilter<IFlight> filter);
}
