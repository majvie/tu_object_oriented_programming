package rbs.record;

//Maximilian Vieweg, e11806443
//TU Wien, Objektorientiertes Programmieren

public abstract class Record implements IRecord {
	private long id;

	public Record(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return this.id;
	}

}
