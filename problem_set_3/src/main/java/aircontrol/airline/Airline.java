//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

package aircontrol.airline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.javatuples.Pair;

import aircontrol.filter.INotificationFilter;
import rbs.flight.IFlight;

public class Airline implements IAirline, IAirlineObservable {
	private Collection<IFlight> flights = new ArrayList<IFlight>();
	protected Collection<Pair<IListener<IFlight>, Collection<INotificationFilter<IFlight>>>> listeners = new ArrayList<Pair<IListener<IFlight>, Collection<INotificationFilter<IFlight>>>>();
	private String name = null;
	
	public Airline(String name)  {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean addFlight(IFlight flight) {
		if (this.flights.contains(flight)) {
			return false;
		}
		this.flights.add(flight);
		
		for (Pair<IListener<IFlight>, Collection<INotificationFilter<IFlight>>> pair : this.listeners) {
			IListener<IFlight> listener = pair.getValue0();
			IFlight flightCopy = flight.deepCopy();
			
			if (listener instanceof IAirlineListener) {
				((IAirlineListener) listener).flightAdded(flightCopy);
			}
			else {
				listener.notifyChange(flightCopy);
			}
		}
		
		return true;
	}
	
	public boolean  addFlights(Collection<IFlight> flights) {
		boolean allAdded = true;
		
		for (IFlight flight : flights) {
			if (!this.addFlight(flight)) {
				allAdded = false;
			}
		}
		
		return allAdded;
	}
	
	public boolean removeFlight(IFlight flight) {
		Iterator<IFlight> iter = this.flights.iterator();
		boolean hasBeenRemoved = false;
		
		while(iter.hasNext()){
			IFlight currentFlight = iter.next();
			
		    if(currentFlight.equals(flight)){
		        iter.remove();
		        hasBeenRemoved = true;
		        IFlight flightCopy = flight.deepCopy();
		        
		        // Notify listeners that flight was deleted
			    for (Pair<IListener<IFlight>, Collection<INotificationFilter<IFlight>>> pair : this.listeners) {
			    	IListener<IFlight> listener = pair.getValue0();
			    	
			    	if (listener instanceof IAirlineListener) {
						((IAirlineListener) listener).flightRemoved(flightCopy);
					}
					else {
						listener.notifyChange(flightCopy);
					}
			    }
		    }
		    
		}
		
		return hasBeenRemoved;
	}
	
	public boolean updateFlight(IFlight flight) {
		// Anhand von id veregleichen und dann replace
		Iterator<IFlight> iter = this.flights.iterator();

		while (iter.hasNext()) {
			IFlight currentFlight = iter.next();
			
			if (currentFlight.getFlightId().equals(flight.getFlightId())) {
				currentFlight.updatePrice(flight.getPrice());
				
				// Notify listeners that flight was updated if all filters check out
			    for (Pair<IListener<IFlight>, Collection<INotificationFilter<IFlight>>> pair : this.listeners) {
			    	IListener<IFlight> listener = pair.getValue0();
					IFlight flightCopy = flight.deepCopy();
					boolean allFiltersTrue = true;
					
					for (INotificationFilter<IFlight> filter : pair.getValue1()) {
						if (filter.check(flight) == false) {
							allFiltersTrue = false;
						}
					}
					
					if (allFiltersTrue) {
						listener.notifyChange(flightCopy);
					}
					
					return true;
			    }
			}
		}
		
		return false;
	}
	
	public void	registerListener(IListener<IFlight> listener) {
		for (Pair<IListener<IFlight>, Collection<INotificationFilter<IFlight>>> pair : this.listeners) {
	    	IListener<IFlight> currentListener = pair.getValue0();
	    	
	    	if (currentListener.equals(listener)) {
	    		return;
	    	}
	    }
		
		ArrayList<INotificationFilter<IFlight>> newCollection = new ArrayList<INotificationFilter<IFlight>>();
		Pair<IListener<IFlight>, Collection<INotificationFilter<IFlight>>> newPair = new Pair<IListener<IFlight>, Collection<INotificationFilter<IFlight>>>(listener, newCollection);
		this.listeners.add(newPair);
		
		// notfiy listener about all current flights
		for (IFlight flight : this.flights) {
			if (listener instanceof IAirlineListener) {
				((IAirlineListener) listener).flightAdded(flight);
			}
			else {
				listener.notifyChange(flight);
			}
		}
	}
	
	public void unregisterListener(IListener<IFlight> listener) {
		Iterator<Pair<IListener<IFlight>, Collection<INotificationFilter<IFlight>>>> iter = this.listeners.iterator();
		
		while (iter.hasNext()) {
			Pair<IListener<IFlight>, Collection<INotificationFilter<IFlight>>> currentPair = iter.next();
			IListener<IFlight> currentListener = currentPair.getValue0();
	    	
	    	if (currentListener.equals(listener)) {
	    		iter.remove();
	    	}
	    }
	}
	
	public void	addNotificationFilter(IAirlineListener listener, INotificationFilter<IFlight> filter) {
		for (Pair<IListener<IFlight>, Collection<INotificationFilter<IFlight>>> pair : this.listeners) {
	    	IListener<IFlight> currentListener = pair.getValue0();
	    	Collection<INotificationFilter<IFlight>> notifications = pair.getValue1();
	    	
	    	if (currentListener.equals(listener)) {
	    		notifications.add(filter);
	    	}
	    }
	}
	
	public void notifyListeners(ActionType type, IFlight flight) throws NullPointerException{
		if (type == null || flight == null) {
			throw new NullPointerException();
		}
		
		for (Pair<IListener<IFlight>, Collection<INotificationFilter<IFlight>>> pair : this.listeners) {
	    	IListener<IFlight> currentListener = pair.getValue0();
	    	Collection<INotificationFilter<IFlight>> notifications = pair.getValue1();
	    	IFlight flightCopy = flight.deepCopy();
	    	
	    	// only sending out notifications for add and update if all notifFilters are fulfilled
	    	boolean allFulfilled = true;
	    	for (INotificationFilter<IFlight> notification : notifications) {
	    		if (!notification.check(flight)) {
	    			allFulfilled = false;
	    		}
	    	}
	    	
	    	// TODO
	    	// Sehr viele ifs, umstrukturierung notwendig?
	    	// oder einfach Angabe falsch verstanden?
	    	
	    	if (currentListener instanceof IAirlineListener) {
	    		if (type.equals(ActionType.DELETE)) {
	    			((IAirlineListener) currentListener).flightRemoved(flight);
				}
	    		else if (allFulfilled) {
	    			if (type.equals(ActionType.ADD)) {
						((IAirlineListener) currentListener).flightAdded(flightCopy);
					}
					else if (type.equals(ActionType.UPDATE)) {
						((IAirlineListener) currentListener).notifyChange(flight);
					}
	    		}
			}
			else {
				if (type.equals(ActionType.ADD) || type.equals(ActionType.UPDATE)) {
					if (allFulfilled) {
						currentListener.notifyChange(flightCopy);
					}
				}
				else {
					currentListener.notifyChange(flightCopy);
				}
			}
	    }
	}
	
	public String toString() {
		String airlineName = "Airline[" + name + "] \n";
		airlineName += "Flights: " + this.flights + "\n";
		airlineName += "Listeners: " + this.listeners;
		
		return airlineName;
	}
	
}
