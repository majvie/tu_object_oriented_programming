package rbs.flight;
import java.util.ArrayList;
import java.util.List;

//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

public class MultiStopFlight extends Flight {
	private List<IFlight> flights = new ArrayList<IFlight>();

	public MultiStopFlight(String id, IFlight flight) {
		super(id, flight.getDeparture(), flight.getDestination(), flight.getPrice());
		IFlight newFlight = flight.deepCopy();
		this.flights.add(newFlight);
	}
	
	public MultiStopFlight(String id, List<IFlight> flights) throws FlightsNotConnectedException {
		super(id, flights.get(0).getDeparture(), flights.get(flights.size() - 1).getDestination());
		
		for (IFlight flight : flights) {
			this.flights.add(flight.deepCopy());
		}
		
		// Check if flights are linked
		for (int i = 0; i < flights.size() - 1; i++) {
			IFlight currentFlight = flights.get(i);
			IFlight nextFlight = flights.get(i + 1);
			
			if (!currentFlight.getDestination().equals(nextFlight.getDeparture())) {
				throw new FlightsNotConnectedException(flights);
			}
		}
		
	}
	
	public List<IFlight>getFlights() {
		return this.flights;
	}

	public float getPrice() {
		float totalPrice = 0;
		
		for (IFlight flight : flights) {
			totalPrice += flight.getPrice();
		}
		
		return totalPrice;
	}

	public void addFlight(IFlight flight) throws FlightsNotConnectedException {
		// Check if flight may be added
		IFlight lastFlight = flights.get(flights.size() - 1);
		if (!lastFlight.getDestination().equals(flight.getDeparture())) {
			throw new FlightsNotConnectedException(this.getFlights());
		}
		
		// Add copy of flight and update destination
		IFlight newFlight = flight.deepCopy();
		flights.add(newFlight);
		this.destination = flight.getDestination();
		
	}

	public boolean equals(Object obj) {
		// Is it of the right class?
		if (!(obj instanceof MultiStopFlight)) {
			return false;
		}
		
		MultiStopFlight flightObj = (MultiStopFlight) obj; // Cast to Flight in order to use MultiStopFlight.methods()
		
		// Does it have the same flight id?
		if (!flightObj.getFlightId().equals(this.getFlightId())) {
			return false;
		}
		
		// Are the flight lists the same?
		List<IFlight> objFlights = flightObj.getFlights();
		List<IFlight> ownFlights = this.getFlights();
		
		// for not having to worry about different sizes later during comparison
		if (objFlights.size() != ownFlights.size()) { 
			return false;
		}
		
		// iterate over the flights and compare them one by one (attrib. compare (dest, price, etc)
		for (int i = 0; i < this.getFlights().size(); i++) {
			if (!objFlights.get(i).equals(ownFlights.get(i))) {
				return false;
			}
		}
		
		return true;
	}
	
	public String toString() {
		String descriptor = "MultiStopFlight[id = '" + this.getFlightId() + "' (";
		for (IFlight flight : this.flights) {
			descriptor += flight + ", ";
		}
		descriptor += ")]";
		
		return descriptor;
	}
	

	@Override
	public MultiStopFlight deepCopy() {
		List<IFlight> newFlights = new ArrayList<IFlight>();
		
		for (IFlight flight : this.flights) {
			newFlights.add(flight.deepCopy());
		}
		
		try {
			MultiStopFlight newMultiStopFlight = new MultiStopFlight(this.getFlightId(), newFlights);
			return newMultiStopFlight;
		}
		catch (FlightsNotConnectedException e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}

}
