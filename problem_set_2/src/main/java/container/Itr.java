package container;

import java.util.Iterator;

public class Itr<E> implements Iterator<E> {
	private IContainerElement<E> next = null;
	
	public Itr(IContainerElement<E> containerElement) {
		this.next = containerElement;
	}
	
	public boolean hasNext() {
		return this.next != null;
	}
	
	public E next() {
		IContainerElement<E> returnElement = this.next;
		this.next = this.next.getNextElement();
		
		return returnElement.getData();
	}
	
	public void remove() {
		// If last returned element == null there is nothing to delete
		if (this.next.getNextElement() == null) {
			return;
		}
		
		IContainerElement<E> afterDeletedElement = this.next.getNextElement().getNextElement();
		this.next.setNextElement(afterDeletedElement);
	}

}
