package container;

public class ContainerElement<E> implements IContainerElement<E> {
	private IContainerElement<E> nextElement = null;;
	private E data = null;

	public ContainerElement(E data) {
		this.data = data;
		this.nextElement = null;
	}
	
	public ContainerElement(E data, IContainerElement<E> containerElement) {
		this.data = data;
		this.nextElement = containerElement;
	}

	@Override
	public E getData() {
		return this.data;
	}

	@Override
	public IContainerElement<E> getNextElement() {
		return this.nextElement;
	}

	@Override
	public boolean hasNextElement() {
		// TODO Auto-generated method stub
		if (this.nextElement != null) {
			return true;
		}
		
		return false;
	}

	@Override
	public void setNextElement(IContainerElement<E> containerElement) {
		this.nextElement = containerElement;
	}
	
	@Override
	public String toString() {
		return "["+ (String) this.getData() + "]";
	}
	

}
