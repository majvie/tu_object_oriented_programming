//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

package aircontrol.airline;

public interface IObservable<TYPE> {
	public void	registerListener(IListener<TYPE> listener);
	
	public void	unregisterListener(IListener<TYPE> listener);
}
