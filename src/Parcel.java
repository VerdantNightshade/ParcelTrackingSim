import java.util.ArrayList;

public abstract class Parcel {

    private String trackingId;
    private String speedName;
    private int deliveryDays;
    private double baseCost = 100.0; // the same for every parcel. We set it to 100 to keep it simple.
    private Hub currentHub;
    private ArrayList<Milestone> history = new ArrayList<Milestone>();

    // The speed classes pass their own speed name and delivery days to this constructor
    public Parcel(String trackingId, String speedName, int deliveryDays) {
        this.trackingId = trackingId;
        this.speedName = speedName;
        this.deliveryDays = deliveryDays;
    }

    // No body here. Each speed class must write its own version of this method
    public abstract double calculateTotalFee();

    public String getTrackingId() {
        return trackingId;
    }

    public String getSpeedName() {
        return speedName;
    }

    public int getDeliveryDays() {
        return deliveryDays;
    }

    public double getBaseCost() {
        return baseCost;
    }

    public Hub getCurrentHub() {
        return currentHub;
    }

    public void setCurrentHub(Hub hub) {
        currentHub = hub;
    }

    // Adds an old event. We use it when we read the status log
    public void addMilestone(Milestone milestone) {
        history.add(milestone);
    }

    // Moves the parcel to a hub and records the event. Returns the event so it can be logged
    public Milestone advanceTo(Hub hub) {
        Milestone milestone = new Milestone(hub); // constructor 1: the time is now
        currentHub = hub;
        history.add(milestone);
        return milestone;
    }

    // Builds the text for the track command
    public String getHistoryText() {
        String text = "Tracking history for " + trackingId + " (" + getSpeedName() + ")\n";
        for (int i = 0; i < history.size(); i++) {
            Milestone m = history.get(i);
            text = text + (i + 1) + ". " + m.getHub().getName() + " (" + m.getTime() + ")\n";
        }
        text = text + "Delivery days: " + getDeliveryDays() + "\n";
        text = text + "Base cost: " + getBaseCost() + "\n";
        // calculateTotalFee runs the code of the real speed class
        text = text + "Total fee: " + calculateTotalFee() + "\n";
        return text;
    }
}