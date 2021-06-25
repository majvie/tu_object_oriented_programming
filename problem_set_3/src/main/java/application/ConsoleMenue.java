//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

package application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import aircontrol.AirControlStation;
import aircontrol.airline.ActionType;
import aircontrol.airline.AirlineAlreadyExists;
import aircontrol.airline.IAirline;
import aircontrol.airline.IAirlineListener;
import aircontrol.airline.IAirlineObservable;
import aircontrol.filter.DepartureNotificationFilter;
import aircontrol.filter.DestinationNotificationFilter;
import aircontrol.filter.INotificationFilter;
import aircontrol.filter.PriceNotificationFilter;
import aircontrol.terminal.ITerminal;
import rbs.flight.Flight;
import rbs.flight.IFlight;
import rbs.flight.OneWayFlight;
import aircontrol.airline.*;
import aircontrol.filter.*;



public class ConsoleMenue {

	public static void main(String[] args) {
		// TODO: implement your console based menue here
		
		System.out.println("Application running...");
		
		AirControlStation aircontrol =  AirControlStation.GET_INSTANCE();
	    Scanner scanner = new Scanner(System.in);  // Create a Scanner object
	    
	    ArrayList<ITerminal> terminals = new ArrayList<ITerminal>();
	    ArrayList<IAirline> airlines = new ArrayList<IAirline>();
	    
		
		// aircontrol.registerAirlineListenerAtAirlineObservable(terminal, firstAirline);
		
		// aircontrol.addNotificationFilter(terminal, firstAirline, null);
		/*
		try {
			airlines.add(aircontrol.createAirline("A"));
			System.out.println("Airline wurde hinzugefügt.");
			
			terminals.add(aircontrol.createTerminal());
			terminals.add(aircontrol.createTerminal());
    		System.out.println("Terminals created.");
    		
    		aircontrol.registerAirlineListenerAtAirlineObservable((IAirlineListener) terminals.get(0), (IAirlineObservable) airlines.get(0));
    		aircontrol.registerAirlineListenerAtAirlineObservable((IAirlineListener) terminals.get(1), (IAirlineObservable) airlines.get(0));
    		
    		INotificationFilter<IFlight> chosenFilter = new PriceNotificationFilter(100);
    		INotificationFilter<IFlight> chosenFilter2 = new PriceNotificationFilter(20);
    		aircontrol.addNotificationFilter((IAirlineListener) terminals.get(0), (IAirlineObservable) airlines.get(0), chosenFilter);
    		aircontrol.addNotificationFilter((IAirlineListener) terminals.get(1), (IAirlineObservable) airlines.get(0), chosenFilter2);
    		
    		IFlight newFlight1 = new OneWayFlight("ID1", "From1", "To1", 200);
    		IFlight newFlight2 = new OneWayFlight("ID1", "From2", "To2", 50);
    		airlines.get(0).addFlight(newFlight1);
    		airlines.get(0).addFlight(newFlight2);
    		
    		//airlines.get(0).removeFlight(newFlight1);
    		airlines.get(0).updateFlight(newFlight2);
    		
    		System.out.println("notifyListeners() \n");
    		((Airline) airlines.get(0)).notifyListeners(ActionType.UPDATE, newFlight1);
    		
    		FlightFacadeFilter filter = new FlightFacadeFilter("From1", "To1", 300);
    		System.out.println("Check: " + filter.check(newFlight2));
    		
    		
    		
			
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		*/
	    
	    while (true) {
	    	System.out.println(
	    			  "\n"
	    			+ "1) Anlegen einer Fluglinie\n"
	    			+ "2) Anlegen eines Terminals\n"
	    			+ "3) Terminal bei Fluglinie registrieren\n"
	    			+ "4) Filter zu Terminal hinzufügen\n"
	    			+ "5) Alle Fluglinien ausgeben\n"
	    			+ "6) Alle Terminals ausgeben\n"
	    			+ "7) Flug zu einer Fluglinie hinzufügen\n"
	    			);
	    	
	    	System.out.print("Auswahl: ");
	    	String choice = scanner.nextLine();
	    	
	    	switch(choice) {
	    	case "1": {
	    		System.out.print("Name der Airline: ");
	    		String airlineName = scanner.nextLine();
	    		
	    		try {
	    			airlines.add(aircontrol.createAirline(airlineName));
	    			System.out.println("Airline wurde hinzugefügt.");
	    		}
	    		catch (Exception e) {
	    			System.out.println(e);
	    		}
	    		
	    		break;
	    	}
	    	case "2": {
	    		terminals.add(aircontrol.createTerminal());
	    		System.out.println("Terminal created.");
	    		break;
	    	}
	    	case "3": {
	    		System.out.print("Name of Airline: ");
	    		String airlineName = scanner.nextLine();
	    		System.out.print("ID of terminal: ");
	    		
	    		long terminalId;
	    		try {
		    		terminalId = Long.parseLong(scanner.nextLine()); // nextLong didnt get \n
	    		}
	    		catch(Exception e) {
	    			System.out.println("The input you entered was not a number. \n");
	    			break;
	    		}
	    		IAirline airline = null;
	    		ITerminal terminal = null;
	    		
	    		for (IAirline a : airlines) {
	    			if (a.getName().equals(airlineName)) {
	    				airline = a;
	    			}
	    		}
	    		
	    		for (ITerminal t : terminals) {
	    			if (t.getId() == ((long) terminalId)) {
	    				terminal = t;
	    			}
	    		}
	    		
	    		if (airline == null || terminal == null) {
	    			System.out.println("The terminal could not be added to the airline. Was there a typo? \n");
	    			break;
	    		}
	    		
	    		aircontrol.registerAirlineListenerAtAirlineObservable((IAirlineListener) terminal, (IAirlineObservable) airline);
	    		
	    		System.out.println("The terminal could be added");
	    		break;
	    	}
	    	case "4": {
	    		System.out.print("Name of Airline: ");
	    		String airlineName = scanner.nextLine();
	    		System.out.print("ID of terminal: ");
	    		
	    		long terminalId;
	    		try {
	    			terminalId = Long.parseLong(scanner.nextLine());
	    		}
	    		catch(Exception e) {
	    			System.out.println("The input you entered was not a number. \n");
	    			break;
	    		}
	    		
	    		// Validate Inputt
	    		IAirline airline = null;
	    		ITerminal terminal = null;
	    		
	    		for (IAirline a : airlines) {
	    			if (a.getName().equals(airlineName)) {
	    				airline = a;
	    			}
	    		}
	    		
	    		for (ITerminal t : terminals) {
	    			if (t.getId() == ((long) terminalId)) {
	    				terminal = t;
	    			}
	    		}
	    		
	    		if (airline == null || terminal == null) {
	    			System.out.println("The terminal could not be added to the airline. Was there a typo? \n");
	    			break;
	    		}
	    		
	    		// Choosing which filter
	    		System.out.print("Type of Filter: \n 1) Price \n 2) Destination \n 3) Departure \n");
	    		String filterChoice = scanner.nextLine();
	    		
	    		double price;
	    		String destination = null;
	    		String departure = null;
	    		
	    		INotificationFilter<IFlight> chosenFilter = null;
	    		
	    		if (filterChoice.equals("1")) {
	    			System.out.print("Upper Price limit: ");

	    			try {
		    			price = Long.parseLong(scanner.nextLine());
		    		}
		    		catch(Exception e) {
		    			System.out.println("The input you entered was not a number. \n");
		    			break;
		    		}
	    			
	    			chosenFilter = new PriceNotificationFilter(price);
	    			System.out.println("The price filter of " + price + " could be added. \n");
	    		}
	    		else if (filterChoice.equals("2")) {
	    			System.out.print("Destination: ");
	    			destination = scanner.nextLine();
	    			
	    			chosenFilter = new DestinationNotificationFilter(destination);
	    			System.out.println("The destination filter of " + destination + " could be added. \n");
	    			
	    		}
	    		else if (filterChoice.equals("3")) {
	    			System.out.print("Departure: ");
	    			departure = scanner.nextLine();
	    			
	    			chosenFilter = new DepartureNotificationFilter(departure);
	    			System.out.println("The departure filter of " + departure + " could be added. \n");
	    			
	    		}
	    		else {
	    			System.out.println("Invalid Input");
	    			break;
	    		}
	    		
	    		aircontrol.addNotificationFilter((IAirlineListener) terminal, (IAirlineObservable) airline, chosenFilter);
	    		break;
	    	}
	    	case "5": {
	    		System.out.println("Liste aller Airlines: ");
	    		for (IAirline airline : airlines) {
	    			System.out.println(airline);
	    		}
	    		break;
	    	}
	    	case "6": {
	    		System.out.println("Liste aller Terminals");
	    		for (ITerminal terminal : terminals) {
	    			System.out.println(terminal);
	    		}
	    		break;
	    	}
	    	case "7": {
	    		System.out.print("Name of Airline: ");
	    		String airlineName = scanner.nextLine();
	    		System.out.print("ID of new flight: ");
	    		String newFlightId = scanner.nextLine();
	    		System.out.print("Departure of new flight: ");
	    		String newFlightDep = scanner.nextLine();
	    		System.out.print("Destination of new flight: ");
	    		String newFlightDest = scanner.nextLine();
	    		System.out.print("Price of new flight: ");
	    		float newFlightPrice = (float) Double.parseDouble(scanner.nextLine());
	    		
	    		IFlight newFlight = new OneWayFlight(newFlightId, newFlightDep, newFlightDest, newFlightPrice);
	    		IAirline newAirline = null;
	    		
	    		for (IAirline a : airlines) {
	    			if (a.getName().equals(airlineName)) {
	    				newAirline = a;
	    			}
	    		}
	    		
	    		if (newAirline == null) {
	    			System.out.println("The airline is not currently registered. \n");
	    			break;
	    		}
	    		
				newAirline.addFlight(newFlight);	    		
	    		break;
	    	}
	    	default: {
	    		System.out.println("Invalid Input");
	    		break;
	    	}

	    	}
	    }
	    
	    //scanner.close();

	}
}
