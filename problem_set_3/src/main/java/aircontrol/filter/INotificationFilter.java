//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

package aircontrol.filter;

public interface INotificationFilter<TYPE> {
	public boolean check(TYPE compareObject);
}
