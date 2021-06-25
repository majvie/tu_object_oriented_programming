package tree.node;

import java.util.ArrayList;
import java.util.Collection;

public class GenericTreeNode<NODETYPE> implements ITreeNode<NODETYPE>{
	
	private ITreeNode<NODETYPE> leftChild = null;
	private ITreeNode<NODETYPE> rightChild = null;
	private NODETYPE nodeValue = null;
	private String label = null;

	public GenericTreeNode(String label, NODETYPE value) {
		this.label = label;
		this.nodeValue = value;
	}

	@Override
	public boolean checkNodeByValue(NODETYPE value) {
		return this.nodeValue.equals(value);
	}

	@Override
	public ITreeNode<NODETYPE> deepCopy() {
		// TODO Docs say direct child, but does not make sense?
		GenericTreeNode<NODETYPE> newNode = new GenericTreeNode<NODETYPE>(this.label, this.nodeValue);
		if (this.getLeftChild() != null) {
			newNode.setLeftChild(this.getLeftChild().deepCopy());
		}
		if (this.getRightChild() != null) {
			newNode.setRightChild(this.getRightChild().deepCopy());
		}
		
		return newNode;
	}

	@Override
	public ITreeNode<NODETYPE> findNodeByNode(ITreeNode<NODETYPE> searchNode) {
		if (this.equals(searchNode)) {
			return this;
		}
		
		ITreeNode<NODETYPE> leftSearch = null;
		ITreeNode<NODETYPE> rightSearch = null;
		
		if (this.getLeftChild() != null) {
			ITreeNode<NODETYPE> leftChild = this.getLeftChild();
			leftSearch = leftChild.findNodeByNode(searchNode);
		}
		if (this.getRightChild() != null) {
			ITreeNode<NODETYPE> rightChild = this.getRightChild();
			rightSearch = rightChild.findNodeByNode(searchNode);
		}
		
		if (leftSearch != null) {
			return leftSearch;
		}
		else if (rightSearch != null) {
			return rightSearch;
		}
		else {
			return null;
		}
	}

	@Override
	public ITreeNode<NODETYPE> findNodeByValue(NODETYPE searchValue) {
		if (this.checkNodeByValue(searchValue)) {
			return this;
		}
		
		ITreeNode<NODETYPE> leftSearch = null;
		ITreeNode<NODETYPE> rightSearch = null;
		
		if (this.getLeftChild() != null) {
			ITreeNode<NODETYPE> leftChild = this.getLeftChild();
			leftSearch = leftChild.findNodeByValue(searchValue);
		}
		if (this.getRightChild() != null) {
			ITreeNode<NODETYPE> rightChild = this.getRightChild();
			rightSearch = rightChild.findNodeByValue(searchValue);
		}
		
		if (leftSearch != null) {
			return leftSearch;
		}
		else if (rightSearch != null) {
			return rightSearch;
		}
		else {
			return null;
		}
	}

	@Override
	public String generateConsoleView(String spacer, String preamble) {
		String output = "\n" + preamble + spacer;

		if (this.isLeaf()) {
			output += "-" + this; 		// it is a leaf
		}
		else {
			output += "+" + this; // it is not a leaf
			
			if (this.getLeftChild() != null) {
				output += this.getLeftChild().generateConsoleView(spacer, preamble + spacer);
			}
			if (this.getRightChild() != null) {
				output += this.getRightChild().generateConsoleView(spacer, preamble + spacer);
			}
		}
		
		
		return output;
	}

	@Override
	public Collection<ITreeNode<NODETYPE>> getChildren() {
		ArrayList<ITreeNode<NODETYPE>> children = new ArrayList<ITreeNode<NODETYPE>>();
		
		children.add(this.leftChild);
		children.add(this.rightChild);
		
		return children;
	}

	@Override
	public String getLabel() {
		return this.label;
	}

	@Override
	public ITreeNode<NODETYPE> getLeftChild() {
		return this.leftChild;
	}

	@Override
	public ITreeNode<NODETYPE> getRightChild() {
		return this.rightChild;
	}

	@Override
	public ITreeNode<NODETYPE> insertNode(ITreeNode<NODETYPE> treeNode) {
		if (this.getLabel().compareTo(treeNode.getLabel()) < 0) {
			ITreeNode<NODETYPE> leftChild = this.getLeftChild();
			if (leftChild == null) {
				this.setLeftChild(treeNode);
			}
			else {
				leftChild.insertNode(treeNode);
			}
		}
		else {
			ITreeNode<NODETYPE> rightChild = this.getRightChild();
			if (rightChild == null) {
				this.setRightChild(treeNode);
			}
			else {
				rightChild.insertNode(treeNode);
			}
		}
		
		return treeNode;
	}

	@Override
	public boolean isLeaf() {
		return this.leftChild == null && this.rightChild == null;
	}

	@Override
	public NODETYPE nodeValue() {
		return this.nodeValue;
	}

	@Override
	public void setLeftChild(ITreeNode<NODETYPE> leftChild) {
		this.leftChild = leftChild;
	}

	@Override
	public void setRightChild(ITreeNode<NODETYPE> rightChild) {
		this.rightChild = rightChild;
	}
	
	public String toString() {
		return "GenericTreeNode:" + nodeValue();
	}
}
