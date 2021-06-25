//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

package aircontrol.airline;

public interface IListener<TYPE> {
	public void notifyChange(TYPE object);
}
