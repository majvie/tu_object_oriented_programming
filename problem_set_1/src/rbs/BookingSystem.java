package rbs;

import rbs.flight.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

public class BookingSystem {

	private List<TravelAgency> agencies = new ArrayList<TravelAgency>();
	private List<IFlight> flightPlan = new ArrayList<IFlight>();
	private List<Booking> bookings = new ArrayList<Booking>();
	
	public static void main(String[] args) {
		BookingSystem bookingSystem = new BookingSystem();
		List<IFlight> listOfOneWayFlights = BookingSystem.generateOneWayFlights();
		List<IFlight> listOfRoundTrips = BookingSystem.generateRoundTripFlights();
		List<IFlight> listOfMultiStopFlights = BookingSystem.generateMultiStopFlights();
		
		bookingSystem.addFlight(listOfOneWayFlights);
		bookingSystem.addFlight(listOfMultiStopFlights);
		bookingSystem.addFlight(listOfRoundTrips);
		
		System.out.println("These are all the flights in the flight plan that were preconfigured by the system:");
		for (IFlight flight : bookingSystem.flightPlan) {
			System.out.println(flight);
		}
		
		TravelAgency firstAgency = new TravelAgency("First Inc.", 0.2f);
		TravelAgency secondAgency = new TravelAgency("Second Inc.", 0.3f);
		
		
		// Trying to add flights twice, arbitrarily adding listOfMultiStopFlights
		bookingSystem.addFlight(listOfMultiStopFlights);
		System.out.println("\nLet's see whether there are duplicates in the flight plan after adding flights that were already there...");
		for (IFlight flight : bookingSystem.flightPlan) {
			System.out.println(flight);
		}
		
		// Using all the methods capable of booking a flight	
		try {
			for (IFlight flight : listOfOneWayFlights) {
				bookingSystem.bookFlight(flight, firstAgency);
			}
			
			bookingSystem.bookFlight(listOfRoundTrips, secondAgency);
			bookingSystem.bookFlight(listOfMultiStopFlights, firstAgency);
			long singleBookingId = bookingSystem.bookFlight(listOfMultiStopFlights.get(0), secondAgency);
			
			// Adding flight to booking 
			bookingSystem.addFlightToBooking(listOfMultiStopFlights.get(1), singleBookingId);

		}
		catch (NotBookableException e) {
			System.out.println("The flight could not be booked");
		}
		
		// Console based menu
		Scanner in = new Scanner(System.in);
		
		while (true) {
			System.out.println("\nBooking System");
			System.out.println("0 - Search for flight");
			System.out.println("1 - Add a flight");
			System.out.println("2 - Show flight plan");
			System.out.println("9 - Exit");
			
			String userInput = in.nextLine();
			
			// Searching flight
			if (userInput.equals("0")) {
				System.out.print("Flight Id: ");
				String userFlightId = in.nextLine();
				boolean couldBeFound = false;
				
				// No method in the booking system to look for flights by id 
				for (IFlight flight : bookingSystem.flightPlan) {
					if (flight.getFlightId().equals(userFlightId)) {
						System.out.println(flight);
						couldBeFound = true;
					}
				}
				if (!couldBeFound) {
					System.out.println("The flight with the Id " + userFlightId + " could not be found.");
				}
			}
			// Adding new Onewayflight
			else if (userInput.equals("1")) {
				String userFlightId, userDeparture, userDestination;
				float userFlightPrice;
				
				try {
					System.out.print("Flight Id: ");
					userFlightId = in.nextLine();
					System.out.print("Departure: ");
					userDeparture = in.nextLine();
					System.out.print("Destination: ");
					userDestination = in.nextLine();
					System.out.print("Price: ");
					userFlightPrice = in.nextFloat();
					in.nextLine(); // Consuming \n
				}
				catch (InputMismatchException e) {
					System.out.println("The type of your input must match the field. E.g. Price is a float.");
					in.nextLine();
					continue;
				}
				
				
				OneWayFlight newFlight = new OneWayFlight(userFlightId, userDeparture, userDestination, userFlightPrice);
				if (bookingSystem.addFlight(newFlight)) {
					System.out.println("This flight could be added. ");
				}
				else {
					System.out.println("This flight could not be added, since there is already a flight with the same id in the flight plan.");
				}
			}
			else if (userInput.equals("2")) {
				for (IFlight flight : bookingSystem.flightPlan) {
					System.out.println(flight);
				}
			}
			else if (userInput.equals("9")) {
				System.out.println("Exiting...");
				break;
			}
			else {
				System.out.println("The input was not valid. ");
			}
		}
		
		in.close();
	}
	
	public void createAgency(String name) {
		TravelAgency newAgency = new TravelAgency(name);
		this.agencies.add(newAgency);
	}

	public List<String> getAgencyNames() {
		List<String> agencyNames = new ArrayList<String>();
		
		for (TravelAgency agency : this.agencies) {
			agencyNames.add(agency.getName());
		}
		
		return agencyNames;
	}

	public TravelAgency findAgency(String name) {
		for (TravelAgency agency : this.agencies) {
			if (agency.getName().equals(name)) {
				return agency;
			}
		}
		
		return null;
	}
	
	public TravelAgency findAgency(TravelAgency agency) {
		for (TravelAgency currentAgency : this.agencies) {
			if (currentAgency.equals(agency)) {
				return currentAgency;
			}
		}
		
		return null;
	}
	
	public boolean addFlight(IFlight flight) {
		// Only adding unique flights
		for (IFlight thisFlight : this.flightPlan) {
			if (thisFlight.getFlightId().equals(flight.getFlightId())) {
				return false;
			}
		}
		
		this.flightPlan.add(flight);
		return true;
	}
	
	public boolean addFlight(List<IFlight> flights) {
		boolean allCopied = true;
		
		for (IFlight flight : flights) {
			if (!this.addFlight(flight)) {
				allCopied = false;
			}
		}
		
		return allCopied;
	}
	
	public boolean containsFlight(IFlight flight) {
		for (IFlight currentFlight : this.flightPlan) {
			if (currentFlight.equals(flight)) {
				return true;
			}
		}
		
		return false;
	}
	
	public long bookFlight(IFlight flight, TravelAgency agency) throws NotBookableException {
		// Checking if flight in flightPlan
		if (!this.containsFlight(flight)) {
			throw new NotBookableException();
		}
		
		long bookingId = Booking.generateBookingId();
		List<IFlight> newFlightList = new ArrayList<IFlight>();
		newFlightList.add(flight.deepCopy()); // Booking only accepts a list of flights for constructor
		
		Booking newBooking = new Booking(bookingId, agency, newFlightList);
		this.bookings.add(newBooking);
		
		return bookingId;
	}
	
	public long bookFlight(List<IFlight> flights, TravelAgency agency) throws NotBookableException {
		// Checking if all flights in flightPlan, and makes copies of them
		List<IFlight> copiedFlightList = new ArrayList<IFlight>();
		for (IFlight flight : flights) {
			if (!this.containsFlight(flight)) {
				throw new NotBookableException();
			}
			copiedFlightList.add(flight);
		}

		long bookingId = Booking.generateBookingId();
		Booking newBooking = new Booking(bookingId, agency, copiedFlightList);
		this.bookings.add(newBooking);
		
		return bookingId;
	}
	
	public boolean addFlightToBooking(IFlight flight, long bookingId) {
		for (Booking booking : this.bookings) {
			if (booking.getId() == bookingId) {
				if (booking.getState() == BookingState.OPEN) {
					if (this.containsFlight(flight)) {
						booking.addFlight(flight);
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	public List<IFlight> getFlights() {
		return this.flightPlan;
	}
	
	public Booking getBookingById(long id) {
		for (Booking booking : this.bookings) {			
			if (booking.getId() == id) {
				return booking;
			}
		}
		
		return null;
	}
	
	protected IFlight generateReturnFlight(OneWayFlight flight) {
		String newDestination = flight.getDeparture();
		String newDeparture = flight.getDestination();
		String newFlightId = flight.getFlightId() + "R";
		float flightPrice = flight.getPrice();
		
		OneWayFlight newFlight = new OneWayFlight(newFlightId, newDeparture, newDestination, flightPrice);
		this.addFlight(newFlight);
		
		return newFlight;
	}

	private static List<IFlight> generateOneWayFlights() {
		List<IFlight> flightList = new ArrayList<IFlight>();
		
		flightList.add(new OneWayFlight("0", "Wien", "Beijing", 200));
		flightList.add(new OneWayFlight("1", "Beijing", "Hong Kong", 300));
		flightList.add(new OneWayFlight("2", "Hong Kong", "Los Angeles", 400));
		flightList.add(new OneWayFlight("3", "Los Angeles", "New York", 500));
		flightList.add(new OneWayFlight("4", "New York", "London", 600));
		flightList.add(new OneWayFlight("5", "London", "Wien", 100));
		
		return flightList;
	}
	
	private static List<IFlight> generateMultiStopFlights() {
		List<IFlight> multiStopFlightList = new ArrayList<IFlight>();
		
		// Making one Multistopflight
		List<IFlight> firstMultiFlightList = new ArrayList<IFlight>();
		OneWayFlight firstFlight = new OneWayFlight("0", "Wien", "Hong Kong", 200);
		OneWayFlight secondFlight = new OneWayFlight("1", "Hong Kong", "Beijing", 300);
		firstMultiFlightList.add(firstFlight);
		firstMultiFlightList.add(secondFlight);
		MultiStopFlight firstMultiStopFlight;
		try {
			firstMultiStopFlight = new MultiStopFlight("6", firstMultiFlightList);
		}
		catch (FlightsNotConnectedException e) {
			System.out.println(e.getMessage());
			return null;
		}
		
		// Making second Multistopflight
		OneWayFlight thirdFlight = new OneWayFlight("3", "Beijing", "New York", 200);
		MultiStopFlight secondMultiStopFlight = new MultiStopFlight("7", thirdFlight);
		OneWayFlight fourthFlight = new OneWayFlight("4", "New York", "Washington D.C.", 300);
		try {
			secondMultiStopFlight.addFlight(fourthFlight);
		}
		catch (FlightsNotConnectedException e) {
			System.out.println(e.getMessage());
			return null;
		}		
		
		// Making third MultiStopFlight containing the other flights
		MultiStopFlight thirdMultiFlight = new MultiStopFlight("8", firstMultiStopFlight);
		OneWayFlight fifthFlight = new OneWayFlight("2", "Washington D.C.", "Wien", 400);
		
		
		try {
			// only one block for all flights because we abort all if error occurs
			thirdMultiFlight.addFlight(secondMultiStopFlight);
			thirdMultiFlight.addFlight(fifthFlight);
		}
		catch (FlightsNotConnectedException e) {
			System.out.println(e.getMessage());
			return null;
		}
		
		
		multiStopFlightList.add(firstMultiStopFlight);
		multiStopFlightList.add(secondMultiStopFlight);
		multiStopFlightList.add(thirdMultiFlight);
		
		return multiStopFlightList;
	}

	private static List<IFlight> generateRoundTripFlights() {
		List<IFlight> roundTripList = new ArrayList<IFlight>();
		
		// First Round Trip
		List<IFlight> firstRoundTripList = new ArrayList<IFlight>();
		firstRoundTripList.add(new OneWayFlight("0", "Wien", "Beijing", 200));
		firstRoundTripList.add(new OneWayFlight("1", "Beijing", "Wien", 300));
		RoundTripFlight firstRoundTrip = new RoundTripFlight("9", firstRoundTripList);
		
		// Second Round Trip
		List<IFlight> secondRoundTripList = new ArrayList<IFlight>();
		secondRoundTripList.add(new OneWayFlight("2", "Berlin", "New York", 200));
		secondRoundTripList.add(new OneWayFlight("3", "New York", "Berlin", 300));
		RoundTripFlight secondRoundTrip = new RoundTripFlight("10", secondRoundTripList);
		
		
		// Third Round Trip
		List<IFlight> thirdRoundTripList = new ArrayList<IFlight>();
		thirdRoundTripList.add(new OneWayFlight("5", "Paris", "London", 200));
		thirdRoundTripList.add(new OneWayFlight("6", "London", "Paris", 300));
		RoundTripFlight thirdRoundTrip = new RoundTripFlight("11", thirdRoundTripList);
		
		roundTripList.add(firstRoundTrip);
		roundTripList.add(secondRoundTrip);
		roundTripList.add(thirdRoundTrip);
		
		return roundTripList;
	}

}
