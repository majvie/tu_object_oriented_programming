package rbs.flight;
import java.lang.Float;

//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

public abstract class Flight implements IFlight {
	private String flightId;
	protected String departure;
	protected String destination;
	private float price;
	
	public Flight(String id, String departure, String destination, float price) {
		this.flightId = id;
		this.departure = departure;
		this.destination = destination;
		this.price = price;
	}
	
	public Flight(String id, String departure, String destination) {
		this.flightId = id;
		this.departure = departure;
		this.destination = destination;
		this.price = 100;
	}
	
	public Flight(String id) {
		this.flightId = id;
		this.price = 100;
		this.destination = "";
		this.departure = "";
	}

	public String getFlightId() {
		return this.flightId;
	}
	
	public float getPrice() {
		return this.price;
	}
	
	public String getDestination() {
		return this.destination;
	}
	
	public String getDeparture() {
		return this.departure;
	}
	
	public String toString() {
		return String.format("Flight[id = '%s', price = %.1f, departure = '%s', destination = '%s']", this.flightId, this.price, this.departure, this.destination);
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	// Added on my own for flight comparison
	public boolean equals(Object obj) {
		// Is it of the right class?
		if (!(obj instanceof Flight)) {
			return false;
		}
		
		Flight flightObj = (Flight) obj; // Cast to Flight in order to use Flight.methods()
		
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
		if (Float.compare(flightObj.getPrice(), this.getPrice()) != 0) {
			return false;
		}
		
		return true;
	}
	
}
