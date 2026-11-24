public class OvernightParcel extends Parcel {

    // Only Overnight has a surcharge
    private double surcharge = 50.0;

    public OvernightParcel(String trackingId) {
        super(trackingId, "Overnight", 1);
    }

    // Overnight pays base cost + base cost / delivery days + the surcharge
    @Override
    public double calculateTotalFee() {
        return getBaseCost() + getBaseCost() / getDeliveryDays() + surcharge;
    }
}