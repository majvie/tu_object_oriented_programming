package rbs;

import rbs.record.Record;
import rbs.flight.*;
import java.util.List;
import java.util.ArrayList;

//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

public class Booking extends Record {
	private List<IFlight> flights = new ArrayList<IFlight>();
	private TravelAgency agency;
	private BookingState state;
	private static long uniqueBookingId = 0;

	public Booking(long id, TravelAgency agency, List<IFlight> flights) {
		super(id);
		this.agency = agency;
		this.state = BookingState.OPEN;
		
		// Adding copies
		for (IFlight flight : flights) {
			this.flights.add(flight.deepCopy());
		}
	}
	

	public List<IFlight> getFlights() {
		return this.flights;
	}
	
	public void addFlight(IFlight flight) {
		IFlight newFlight = flight.deepCopy();
		flights.add(newFlight);
	}
	
	protected boolean setState(BookingState state) {
		if (this.state == BookingState.OPEN && state == BookingState.PAID) {
			return false;
		}
		
		this.state = state;
		return true;
	}
	
	public BookingState getState() {
		return state;
	}

	public boolean isPaid() {
		return this.state == BookingState.PAID;
	}
	
	public TravelAgency getAgency() {
		return this.agency;
	}
	
	public static long generateBookingId() {
		uniqueBookingId++;
		return uniqueBookingId - 1; // return old value
	}

}
