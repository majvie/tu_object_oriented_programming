package container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import util.searchable.ISearchFilter;
import util.searchable.ISearchableByFilter;


public class Container<E> implements Collection<E>, ISearchableByFilter<E> {
	private IContainerElement<E> firstElement;
	
	public Container() {
		this.firstElement = null;
	}
	
	@Override
	public boolean add(E e) throws NullPointerException {
		if (e == null) {
			throw new NullPointerException();
		}
		
		ContainerElement<E> newElement =  new ContainerElement<E>(e);
		
		if (firstElement == null) {
			this.firstElement = (IContainerElement<E>) newElement;
			
			return true;
		}
		
		// Go to the end of the list
		IContainerElement<E> currentElement = this.firstElement;
		while (currentElement.getNextElement() != null) {
			currentElement = currentElement.getNextElement();
		}
		currentElement.setNextElement(newElement);
		
		return true;

	}
	
	@Override
	public boolean addAll(Collection<? extends E> c) {
		for (E element : c) {
			this.add(element);
		}
		
		return true;
	}
	
	@Override
	public void clear() {
		this.firstElement = null;
	}
	
	@Override
	public boolean contains(Object object) {
		Iterator<E> iterator = this.iterator();

		while (iterator.hasNext()) {
			if (object.equals(iterator.next())) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public boolean containsAll(Collection<?> c) {
		Iterator<E> iterator = this.iterator();

		while (iterator.hasNext()) {
			for (Object object : c) {
				if (object.equals(iterator.next())) {
					return true;
				}
			}
		}
		
		return true;
	}
	
	public E get(int index) throws IndexOutOfBoundsException{
		// Assuming indices from 0->N-1
		
		E currentValue = this.firstElement.getData();
		Iterator<E> iter = this.iterator();
		
		while (index > 0) {
			if (!iter.hasNext()) {
				throw new IndexOutOfBoundsException();
			}
			currentValue = iter.next();
			index--;
		}
		
		return currentValue;
		
	}
	
	@Override
	public boolean isEmpty() {
		return this.firstElement == null;
		
	}
	
	@Override
	public Iterator<E> iterator() {
		return new Itr<E>(this.firstElement);
	}
	
	@Override
	public boolean remove(Object object) throws NullPointerException{
		// Removes all values of object that are saved in ContainerElements.data
		Iterator<E> iter = this.iterator();
		
		// Do not allow removal of elements with value null
		if (object == null) {
			throw new NullPointerException();
		}
		
		boolean removedSomething = false;
		
		while(iter.hasNext()) {
			if (object.equals(iter.next())) {
				iter.remove();
				removedSomething = true;
			}
		}
		
		return removedSomething;
		
	}
	
	@Override
	public boolean removeAll(Collection<?> collection) {
		boolean removedAnything = false;
		
		for (Object object : collection) {
			if (this.remove(object)) {
				removedAnything = true;
			}
		}
		
		return removedAnything;
	}
	
	@Override
	public boolean retainAll(Collection<?> c) {
		Iterator<E> iter = this.iterator();
		boolean removedSomething = false;
		
		while(iter.hasNext()) {
			boolean inCollection = false;
			
			for (Object object : c) {
				if (object.equals(iter.next())) {
					inCollection = true;
					break;
				}
			}
			
			if (!inCollection) {
				iter.remove();
				removedSomething = true;
			}
		}
		
		return removedSomething;
	}
	
	@Override
	public Collection<E> searchByFilter(ISearchFilter filter, Object compareObject) {
		Iterator<E> iter = this.iterator();
		ArrayList<E> matchingElements = new ArrayList<E>();
		
		while (iter.hasNext()) {
			E currentValue = iter.next();
			
			if (filter.searchFilterFunction(currentValue, compareObject)) {
				matchingElements.add(currentValue);
			}
		}
		
		return matchingElements;
	}
	
	@Override
	public int size() {
		int size = 0;
		Iterator<E> iter = this.iterator();
		
		while(iter.hasNext()) {
			size++;
			iter.next();
		}
		
		return size;
		
	}
	
	@Override
	public Object[] toArray() {
		Object[] newArray = new Object[this.size()];
		Iterator<E> iterator = this.iterator();
		
		for (int i = 0; i < newArray.length; i++) {
			newArray[i] = iterator.next();
		}
		
		return newArray;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		Iterator<E> iterator = this.iterator();
		int size = this.size();
		
		T[] newArray = a.length > this.size() ? a : (T[]) new Object[size];
		for (int i = 0; i < newArray.length; i++) {
			newArray[i] = (T) iterator.next();
		}
		
		return newArray;
	}
	
	@Override
	public String toString() {
		String name = "Container[" + this.size() + "] ";
		Iterator<E> iter = this.iterator();
		
		while (iter.hasNext()) {
			name += " " + iter.next().toString() + " ";
		}
		
		return name;
	}
	
}
