//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

package aircontrol.filter;

import rbs.flight.IFlight;

public class FlightFacadeFilter implements INotificationFilter<IFlight> {
	private DepartureNotificationFilter departureFilter = null;
	private DestinationNotificationFilter destinationFilter = null;
	private PriceNotificationFilter priceFilter = null;
	
	public FlightFacadeFilter(String departure, String destination, double priceLimit) {
		this.departureFilter = new DepartureNotificationFilter(departure);
		this.destinationFilter = new DestinationNotificationFilter(destination);
		this.priceFilter = new PriceNotificationFilter(priceLimit);
	}
	
	@Override
	public boolean check(IFlight compareObject) {
		boolean departureCheck = departureFilter.check(compareObject);
		boolean destinationCheck = destinationFilter.check(compareObject);
		boolean priceCheck = priceFilter.check(compareObject);
		
		return departureCheck && destinationCheck && priceCheck;
	}
}
