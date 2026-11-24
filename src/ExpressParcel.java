public class ExpressParcel extends Parcel {

    public ExpressParcel(String trackingId) {
        super(trackingId, "Express", 2); // we have used 2 days to keep it simple
    }

    // Express pays base cost + base cost / delivery days
    @Override
    public double calculateTotalFee() {
        return getBaseCost() + getBaseCost() / getDeliveryDays();
    }
}