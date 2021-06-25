package tree.node;

import rbs.copy.IDeepCopy;

public class CopyableTreeNode<NODETYPE extends IDeepCopy> extends GenericTreeNode<NODETYPE>{

	public CopyableTreeNode(String label, NODETYPE value) {
		super(label, value);
	}
	
	public CopyableTreeNode<NODETYPE> deepCopy() {
		return new CopyableTreeNode<NODETYPE>(this.getLabel(), this.nodeValue());
	}

}
