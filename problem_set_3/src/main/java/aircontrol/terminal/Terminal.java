//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

package aircontrol.terminal;

import java.util.ArrayList;
import java.util.Collection;

import aircontrol.airline.IAirlineListener;
import aircontrol.airline.IListener;
import rbs.flight.IFlight;

public class Terminal implements IListener<IFlight>, IAirlineListener, ITerminal {
	private long id;
	private Collection<IFlight> flights = new ArrayList<IFlight>();
	private  Collection<String> logentries = new ArrayList<String>();
	
	public Terminal(long id) {
		this.id = id;
	}
	
	public void flightAdded(IFlight flight) {
		this.flights.add(flight);
		
		String displayLine = "Terminal[" + id + "] " + "The flight " + flight + " was added. \n";
		System.out.println(displayLine);
		logentries.add(displayLine);
	}
	
	public void flightRemoved(IFlight flight) {
		this.flights.remove(flight);
		
		String displayLine = "Terminal[" + id + "] " + "The flight " + flight + " was removed. \n";
		System.out.println(displayLine);
		logentries.add(displayLine);
	}
	
	public void notifyChange(IFlight flight) {
		for (IFlight currentFlight : this.flights) {
			if (currentFlight.equals(flight)) {
				currentFlight.updatePrice(flight.getPrice());
			}
		}
		
		String displayLine = "Terminal[" + id + "] " + "The flight " + flight + " has been updated: Its new price is " + flight.getPrice() + "\n";
		System.out.println(displayLine);
		logentries.add(displayLine);
	}
	
	public void outputLogEntries() {
		for (String entry : this.logentries) {
			System.out.println(entry);
		}
	}
	
	public long getId() {
		return this.id;
	}
	
	public String toString() {
		String terminalName = "Terminal[" + id + "]  ";
		terminalName += this.flights;
		
		return terminalName;
	}
}
