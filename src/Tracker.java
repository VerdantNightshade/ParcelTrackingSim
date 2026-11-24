import java.io.IOException;
import java.util.ArrayList;

public class Tracker {

    private Route route;
    private ManifestStore store;
    private ArrayList<Parcel> parcels;

    // Builds the route and the store, then loads the saved parcels
    public Tracker() throws IOException {
        route = new Route();
        store = new ManifestStore(route);
        parcels = store.loadManifest();
    }

    // Command: add. Creates a parcel at the first hub
    public String addParcel(String speed) throws IOException {
        int number = 1000 + parcels.size() + 1; // the first ID is ORD1001
        String id = "ORD" + number; // ORD1001, ORD1002, and so on

        Parcel parcel = ParcelFactory.makeParcel(speed, id);
        if (parcel == null) {
            return "Unknown speed: " + speed + ". Use Standard, Express, or Overnight.";
        }

        Hub first = route.getFirst();
        Milestone milestone = parcel.advanceTo(first);
        parcels.add(parcel);

        store.saveManifest(parcels);
        store.appendLog(id, milestone);

        return "Created parcel " + id + " (" + parcel.getSpeedName() + ") at " + first.getName() + ".";
    }

    // Command: advance. Moves a parcel to the next hub
    public String advanceParcel(String id) throws IOException, InvalidTrackingIdException {
        Parcel parcel = findParcel(id);
        Hub next = route.getNext(parcel.getCurrentHub());
        if (next == null) {
            return id + " is already delivered.";
        }

        Milestone milestone = parcel.advanceTo(next);
        store.saveManifest(parcels);
        store.appendLog(id, milestone);

        return id + " moved to " + next.getName() + ".";
    }

    // Command: track. Returns the history text of one parcel
    public String trackParcel(String id) throws InvalidTrackingIdException {
        Parcel parcel = findParcel(id);
        return parcel.getHistoryText();
    }

    // Checks the ID format, then looks for the parcel in the list
    private Parcel findParcel(String id) throws InvalidTrackingIdException {
        checkId(id);
        for (int i = 0; i < parcels.size(); i++) {
            if (parcels.get(i).getTrackingId().equals(id)) {
                return parcels.get(i);
            }
        }
        throw new InvalidTrackingIdException("Parcel not found: " + id);
    }

    // Rule: the ID starts with ORD and has at least one digit after it, and only digits
    private void checkId(String id) throws InvalidTrackingIdException {
        if (!id.startsWith("ORD") || id.length() == 3) {
            throw new InvalidTrackingIdException("Invalid tracking ID: " + id);
        }
        for (int i = 3; i < id.length(); i++) {
            if (!Character.isDigit(id.charAt(i))) {
                throw new InvalidTrackingIdException("Invalid tracking ID: " + id);
            }
        }
    }
}
