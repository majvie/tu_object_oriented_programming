package rbs;

//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

public class TravelAgency {

	private String name;
	private float discount;
	
	public TravelAgency(String name, float discount) {
		this.name = name;
		this.discount = discount;
	}

	public TravelAgency(String name) {
		this.name = name;
		this.discount = 0;
	}
	
	public float getDiscount() {
		return this.discount;
	}
	
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return String.format("TravelAgency[name = %s, discount = %f", this.getName(), this.getDiscount());
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof TravelAgency)) {
			return false;
		}
		TravelAgency newAgency = (TravelAgency) obj;
		
		if (! newAgency.getName().equals(this.getName())) {
			return false;
		}
		
		return true;
	}

}
