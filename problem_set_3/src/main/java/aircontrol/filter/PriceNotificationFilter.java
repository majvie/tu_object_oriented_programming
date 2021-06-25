//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

package aircontrol.filter;

import rbs.flight.IFlight;

public class PriceNotificationFilter implements INotificationFilter<IFlight> {
	private double priceLimit = 0;
	
	public PriceNotificationFilter() {
		this.priceLimit = Double.MAX_VALUE;
	}
	
	public PriceNotificationFilter(double priceLimit) {
		if (priceLimit < 0) {
			this.priceLimit = Double.MAX_VALUE;
		}
		this.priceLimit = priceLimit;
	}
	
	@Override
	public boolean check(IFlight compareObject) {
		double priceOfFlight = compareObject.getPrice();
		return priceOfFlight <= this.priceLimit;
	}
}
