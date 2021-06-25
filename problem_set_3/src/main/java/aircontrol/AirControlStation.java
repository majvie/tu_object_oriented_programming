//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

package aircontrol;

import java.util.ArrayList;
import java.util.Collection;

import aircontrol.airline.Airline;
import aircontrol.airline.AirlineAlreadyExists;
import aircontrol.airline.IAirline;
import aircontrol.airline.IAirlineListener;
import aircontrol.airline.IAirlineObservable;
import aircontrol.filter.INotificationFilter;
import aircontrol.terminal.ITerminal;
import aircontrol.terminal.Terminal;
import rbs.flight.IFlight;

public class AirControlStation implements IAirControlStation, IAirlineFactory, ITerminalFactory {
	private static AirControlStation INSTANCE = null;
	private static long TERMINAL_ID = 0;
	private Collection<ITerminal> terminals = new ArrayList<ITerminal>();
	private Collection<IAirline> airlines = new ArrayList<IAirline>();
	private Collection<IAirlineListener> listeners = new ArrayList<IAirlineListener>();
	private Collection<IAirlineObservable> observables = new ArrayList<IAirlineObservable>();
	
	@Override
	public IAirline createAirline(String name) throws AirlineAlreadyExists {
		// Check if airline already exists
		for (IAirline a : this.airlines) {
			if (a.getName().equals(name)) {
				throw new AirlineAlreadyExists();
			}
		}
		
		Airline newAirline = new Airline(name);
		this.airlines.add(newAirline);
		
		if (newAirline instanceof IAirlineObservable) {
			this.observables.add((IAirlineObservable) newAirline);
		}
		
		return newAirline;
	}
	
	@Override
	public ITerminal createTerminal() {
		ITerminal newTerminal = new Terminal(TERMINAL_ID);
		TERMINAL_ID++;
		
		this.terminals.add(newTerminal);
		
		if (newTerminal instanceof IAirlineListener) {
			this.listeners.add((IAirlineListener) newTerminal);
		}
		return newTerminal;
	}
	
	public static AirControlStation	GET_INSTANCE() {
		if (INSTANCE == null) {
			INSTANCE = new AirControlStation();
		}
		
		return INSTANCE;
	}
	
	@Override
	public void registerAirlineListenerAtAirlineObservable(IAirlineListener airlineListener, IAirlineObservable airlineObservable) {
		if (this.listeners.contains(airlineListener)) {
			if (this.observables.contains(airlineObservable)) {
				airlineObservable.registerListener(airlineListener);
			}
		}
	}
	
	@Override
	public void	addNotificationFilter(IAirlineListener airlineListener, IAirlineObservable airlineObservable, INotificationFilter<IFlight> filter) {		
		if (this.listeners.contains(airlineListener)) {
			if (this.observables.contains(airlineObservable)) {
				airlineObservable.addNotificationFilter(airlineListener, filter);
			}
		}
	}
}
