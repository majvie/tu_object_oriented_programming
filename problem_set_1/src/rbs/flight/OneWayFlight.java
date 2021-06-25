package rbs.flight;

//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

public class OneWayFlight extends Flight {

	public OneWayFlight(String id, String departure, String destination, float price) {
		super(id, departure, destination, price);
	}

	public OneWayFlight(String id, String departure, String destination) {
		super(id, departure, destination);
	}


	public boolean equals(Object obj) {
		// Is it of the right class?
		if (!(obj instanceof OneWayFlight)) {
			return false;
		}
		
		Flight flightObj = (OneWayFlight) obj; // Cast to Flight in order to use Flight.methods()
		
		// Does it have the same flight id
		if (!flightObj.getFlightId().equals(this.getFlightId())) {
			return false;
		}
		// Is the destination the same?
		if (!flightObj.getDestination().equals(this.getDestination())) {
			return false;
		}
		// Is the departure the same?
		if (!flightObj.getDeparture().equals(this.getDeparture())) {
			return false;
		}
		
		return true;
	}

	@Override
	public OneWayFlight deepCopy() {
		// TODO Extend later for other objects that may also need to get copied
		OneWayFlight newFlight = new OneWayFlight(this.getFlightId(), this.getDeparture(), this.getDestination(), this.getPrice());
		return newFlight;
	}

}
