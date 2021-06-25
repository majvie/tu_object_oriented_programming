//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

package aircontrol.terminal;

import aircontrol.ITerminalFactory;

public class TerminalFactory implements ITerminalFactory {
	private long current_id = 0;
	
	@Override
	public ITerminal createTerminal() {
		Terminal newTerminal = new Terminal(current_id);
		current_id++;
		
		return newTerminal;
	}
}
