public class StandardParcel extends Parcel {

    // super(...) calls the Parcel constructor.
    public StandardParcel(String trackingId) {
        super(trackingId, "Standard", 5); // we have used 5 days to keep it simple
    }

    // Standard pays only the base cost
    @Override
    public double calculateTotalFee() {
        return getBaseCost();
    }
}