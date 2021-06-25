//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

package aircontrol.filter;

import rbs.flight.IFlight;

public class DestinationNotificationFilter implements INotificationFilter<IFlight> {
	private String pattern = "(.*)";
	
	public DestinationNotificationFilter(String pattern) {
		if (pattern == null) {
			this.pattern = "(.*)";
			return;
		}
		this.pattern = pattern;
	}
	
	@Override
	public boolean check(IFlight compareObject) {
		String destination = compareObject.getDestination();
		return destination.matches(this.pattern);
	}
}
