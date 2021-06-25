package util.searchable;

import rbs.flight.Flight;

public class FlightSameRouteFilter implements ISearchFilter {

	public FlightSameRouteFilter() {
		return;
	}

	@Override
	public boolean searchFilterFunction(Object originalObject, Object compareObject) {
		if (originalObject.getClass() == Flight.class && compareObject.getClass() == Flight.class) {
			Flight firstFlight = (Flight) originalObject;
			Flight secondFlight = (Flight) compareObject;
			
			if (firstFlight.getDeparture().equals(secondFlight.getDeparture())) {
				if (firstFlight.getDestination().equals(secondFlight.getDestination())) {
					return true;
				}
			}
		}
		
		return false;
	}

}
