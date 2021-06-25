package tree;

import rbs.flight.IFlight;
import tree.node.FlightTreeNode;
import tree.node.ITreeNode;

public class FlightTree extends GenericTree<IFlight>{	
	public FlightTree() {
		super(null);
	}
	
	public FlightTree(IFlight flight) {
		super(new FlightTreeNode(flight));
	}
	
	public FlightTree(ITreeNode<IFlight> root) {
		super(root);
	}

}
