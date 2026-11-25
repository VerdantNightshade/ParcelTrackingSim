public class ParcelFactory {

    // Returns the right parcel for a speed name, or null if the speed is unknown
    public static Parcel makeParcel(String speed, String id) {
        if (speed == null) {
            return null;
        }
        if (speed.equals("Standard")) {
            return new StandardParcel(id);
        }
        if (speed.equals("Express")) {
            return new ExpressParcel(id);
        }
        if (speed.equals("Overnight")) {
            return new OvernightParcel(id);
        }
        return null;
    }
}