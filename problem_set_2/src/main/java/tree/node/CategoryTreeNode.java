package tree.node;

import util.FlightCategory;

public class CategoryTreeNode<NODETYPE, CATEGORY> extends GenericTreeNode<NODETYPE> {
	private CATEGORY category = null;
	
	public CategoryTreeNode(CATEGORY category) {
		super(((FlightCategory) category).getLabel(), null);
		this.category = category;
	}
	
	@Override
	public CategoryTreeNode<NODETYPE, CATEGORY> deepCopy() {
		return new CategoryTreeNode<NODETYPE, CATEGORY>(this.getCategory());
	}
	
	public CATEGORY getCategory() {
		return this.category;
	}
	
	public String getLabel() {
		return super.getLabel();
	}
	
	public NODETYPE nodeValue() {
		return super.nodeValue();
	}
	

}
