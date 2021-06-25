//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

package aircontrol;

import aircontrol.terminal.ITerminal;

public interface ITerminalFactory {
	public ITerminal createTerminal();
}
