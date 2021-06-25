//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

package aircontrol.filter;

import rbs.flight.IFlight;

public class DepartureNotificationFilter implements INotificationFilter<IFlight> {
	private String pattern = "(.*)";
	
	public DepartureNotificationFilter(String pattern) {
		if (pattern == null) {
			this.pattern = "(.*)";
			return;
		}
		this.pattern = pattern;
	}
	
	@Override
	public boolean check(IFlight compareObject) {
		String departure = compareObject.getDeparture();
		return departure.matches(this.pattern);
	}
}
