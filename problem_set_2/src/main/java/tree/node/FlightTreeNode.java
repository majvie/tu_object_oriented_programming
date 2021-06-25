package tree.node;

import rbs.flight.DualFlight;
import rbs.flight.IFlight;

public class FlightTreeNode extends GenericTreeNode<IFlight> {

	private FlightTreeNode(DualFlight value) {
		super(value.getFlightId(), value);
	}
	
	public FlightTreeNode(IFlight value) {
		super(value.getFlightId(), value);
		if (value.getClass() == DualFlight.class) {
			DualFlight dualFlight = (DualFlight) value;
			
			for (IFlight flight : dualFlight.getFlights()) {
				this.initialize(flight);
			}
		}
	}
	
	public FlightTreeNode(String label, IFlight value) {
		super(label, value);
		this.initialize(value);
	}
	
	@Override
	public FlightTreeNode deepCopy() {
		// TODO Docs say direct child, but does not make sense?
		FlightTreeNode newNode = new FlightTreeNode(this.nodeValue());
		if (this.getLeftChild() != null) {
			newNode.setLeftChild(this.getLeftChild().deepCopy());
		}
		if (this.getRightChild() != null) {
			newNode.setRightChild(this.getRightChild().deepCopy());
		}
		
		return newNode;
	}
	
	private void initialize(IFlight item) {	
		if (item.getClass().equals(DualFlight.class)) {
			DualFlight dualFlight = (DualFlight) item;
			FlightTreeNode dualFlightNode = new FlightTreeNode(dualFlight);
			this.insertNode(dualFlightNode);
			
			for (IFlight flight : dualFlight.getFlights()) {
				this.initialize(flight);
			}
		}
		else {
			this.insertNode(new FlightTreeNode(item));
		}
	}
	
	@Override
	public ITreeNode<IFlight> insertNode(ITreeNode<IFlight> treeNode) {
		return super.insertNode(treeNode);
	}
	
	@Override 
	public String toString() {
		return "node:" + nodeValue();
	}

}
