//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

package aircontrol;

import aircontrol.airline.AirlineAlreadyExists;
import aircontrol.airline.IAirline;

public interface IAirlineFactory {
	public IAirline createAirline(String name) throws AirlineAlreadyExists;
}
