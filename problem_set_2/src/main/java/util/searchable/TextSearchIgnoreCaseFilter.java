package util.searchable;


public class TextSearchIgnoreCaseFilter implements ISearchFilter {

	public TextSearchIgnoreCaseFilter() {
		return;
	}

	@Override
	public boolean searchFilterFunction(Object originalObject, Object compareObject) {
		String originalString = (String) originalObject;
		String compareString = (String) compareObject;
		
		return originalString.equalsIgnoreCase(compareString);
	}

}
