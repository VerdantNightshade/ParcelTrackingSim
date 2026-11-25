public class Hub {

	// Private field: only this class can change it
	private String name;

	public Hub(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	// Two hubs are equal when their names are equal
	@Override
	public boolean equals(Object candidate) {
		if (!(candidate instanceof Hub)) {
			return false; // this also covers null
		}
		return name.equals(((Hub) candidate).getName());
	}

	// Equal hubs must have the same hash code
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
