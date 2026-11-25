public class Route {

	// The four hubs, in travel order
	private Hub[] hubs = new Hub[4];

	// The constructor runs once, when we write: new Route()
	public Route() {
		hubs[0] = new Hub("Dispatch");
		hubs[1] = new Hub("Sorting Facility");
		hubs[2] = new Hub("Out-for-Delivery");
		hubs[3] = new Hub("Delivered");
	}

	public Hub getFirst() {
		return hubs[0];
	}

	// Returns the hub after the given hub, or null if the given hub is the last one
	public Hub getNext(Hub current) {
		for (int i = 0; i < hubs.length - 1; i++) {
			if (hubs[i].equals(current)) {
				return hubs[i + 1];
			}
		}
		return null;
	}

	// Finds a hub by its name. We need this when we read the files. Returns null if
	// not found
	public Hub findByName(String name) {
		for (int i = 0; i < hubs.length; i++) {
			if (hubs[i].getName().equals(name)) {
				return hubs[i];
			}
		}
		return null;
	}
}
