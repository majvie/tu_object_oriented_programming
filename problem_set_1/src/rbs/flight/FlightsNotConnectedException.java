package rbs.flight;
import java.util.List;

//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

public class FlightsNotConnectedException extends Exception {
	private List<IFlight> flights;
	static final long serialVersionUID = 0;

	public FlightsNotConnectedException(List<IFlight> flights) {
		this.flights = flights;
	}
	
	public String getMessage() {
		String errorMessage = "There was an error with the flight list: Flights destinations and departures do not match. \n";
		
		for (IFlight flight : flights) {
			errorMessage += flight + "\n";
		}
		
		return errorMessage;
		
	}
}
