package rbs.flight;

import java.util.List;
import java.util.ArrayList;

//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

public class RoundTripFlight extends Flight {
	private List<IFlight> flights = null;

	public RoundTripFlight(String id, List<IFlight> flights) {
		super(id, flights.get(0).getDeparture(), flights.get(0).getDestination());
		
		// only accept flights if departure first flight is destination of second
		// if not do nothing (laut Angabe?) Might throw exception otherwise
		if (flights.get(0).getDeparture().equals(flights.get(1).getDestination())) {
			this.flights = flights;
		}
	}

	public List<IFlight> getFlights() {
		return this.flights;
	}

	public float getPrice() {
		float totalPrice = 0;
		
		for (IFlight flight : this.flights) {
			totalPrice += flight.getPrice();
		}
		
		return 0.9f * totalPrice; // Roundtrip Discount of 10 %
	}
	
	public boolean equals(Object obj) {
		// Is it of the right class?
		if (!(obj instanceof RoundTripFlight)) {
			return false;
		}
		
		RoundTripFlight flightObj = (RoundTripFlight) obj; // Cast to Flight in order to use RoundTripFlight.methods()
		
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
		String descriptor = "RoundTripFlight[id = " + this.getFlightId() + " (";
		for (IFlight flight : this.flights) {
			descriptor += flight + ", ";
		}
		descriptor += ")]";
		
		return descriptor;
	}
	
	public RoundTripFlight deepCopy() {
		List<IFlight> newFlights = new ArrayList<IFlight>();
		
		for (IFlight flight : this.flights) {
			newFlights.add(flight.deepCopy());
		}
	
		RoundTripFlight newRoundTripFlight = new RoundTripFlight(this.getFlightId(), newFlights);
		return newRoundTripFlight;
		
	}

}
