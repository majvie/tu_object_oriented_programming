package util.searchable;

import rbs.flight.Flight;

public class FlightPriceLessFilter implements ISearchFilter {

	public FlightPriceLessFilter() {
		return;
	}

	@Override
	public boolean searchFilterFunction(Object originalObject, Object compareObject) {
		if (originalObject.getClass() == Flight.class && compareObject.getClass() == Flight.class) {
			Flight firstFlight = (Flight) originalObject;
			Flight secondFlight = (Flight) compareObject;
			
			return firstFlight.getPrice() < secondFlight.getPrice();
		}
		
		return false;
	}

}
