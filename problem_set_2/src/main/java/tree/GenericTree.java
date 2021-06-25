package tree;

import java.util.ArrayList;
import java.util.Collection;

import tree.node.ITreeNode;
import util.searchable.ISearchFilter;

public class GenericTree<TREETYPE> implements ITree<TREETYPE> {
	private ITreeNode<TREETYPE> root;

	public GenericTree() {
		this.root = null;
	}
	
	public GenericTree(ITreeNode<TREETYPE> root) {
		this.root = root;
	}
	
	@Override
	public void setRoot(ITreeNode<TREETYPE> root) {
		this.root = root;
	}
	
	@Override
	public ITreeNode<TREETYPE> getRoot() {
		return this.root;
	}
	
	@Override
	public ITreeNode<TREETYPE> findNode(ITreeNode<TREETYPE> searchNode) {
		return this.root.findNodeByNode(searchNode);
	}
	
	@Override
	public ITreeNode<TREETYPE> findNode(TREETYPE searchValue) {
		return this.root.findNodeByValue(searchValue);
	}
	
	// TODO trees are ordered, change search algo
	@Override
	public ITreeNode<TREETYPE> insertNode(ITreeNode<TREETYPE> treeNode) {
		return this.root.insertNode(treeNode);
	}
	
	@Override
	public String generateConsoleView(String spacer) {
		return this.root.generateConsoleView(spacer, "__");
	}
	
	@Override
	public Collection<ITreeNode<TREETYPE>> searchByFilter(ISearchFilter filter, Object compareObject) {
		// TODO Docs describe two checks, which should be present in the filter function itself, only do one?
		ArrayList<ITreeNode<TREETYPE>> nodeList = new ArrayList<ITreeNode<TREETYPE>>();

		if (this.root == null) {
			return nodeList;
		}
		
		if (filter.searchFilterFunction(this, compareObject)) {
			nodeList.add(this.getRoot());
		}
		
		if (this.root.getLeftChild() != null) {
			ITreeNode<TREETYPE> leftChild = this.root.getLeftChild();
			GenericTree<TREETYPE> currentTree = new GenericTree<TREETYPE>(leftChild);
			nodeList.addAll(currentTree.searchByFilter(filter, compareObject));
		}
		if (this.root.getRightChild() != null) {
			ITreeNode<TREETYPE> rightChild = this.root.getRightChild();
			GenericTree<TREETYPE> currentTree = new GenericTree<TREETYPE>(rightChild);
			nodeList.addAll(currentTree.searchByFilter(filter, compareObject));
		}
		
		return nodeList;
	}
	
	@Override
	public ITree<TREETYPE> deepCopy() {
		return new GenericTree<TREETYPE>(this.root.deepCopy());
	}

	

}
