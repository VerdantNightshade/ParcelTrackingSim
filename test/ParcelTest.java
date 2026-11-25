// Tests Parcel and its three speed classes
public class ParcelTest {

    public static void testStandardParcel() {
        Parcel p = new StandardParcel("ORD1001");
        TestSupport.assertEquals("id", "ORD1001", p.getTrackingId());
        TestSupport.assertEquals("speed", "Standard", p.getSpeedName());
        TestSupport.assertEquals("days", 5, p.getDeliveryDays());
        TestSupport.assertEquals("base cost", 100.0, p.getBaseCost());
        TestSupport.assertEquals("fee", 100.0, p.calculateTotalFee());
    }

    public static void testExpressParcel() {
        Parcel p = new ExpressParcel("ORD1002");
        TestSupport.assertEquals("speed", "Express", p.getSpeedName());
        TestSupport.assertEquals("days", 2, p.getDeliveryDays());
        TestSupport.assertEquals("base cost", 100.0, p.getBaseCost());
        TestSupport.assertEquals("fee", 150.0, p.calculateTotalFee());
    }

    public static void testOvernightParcel() {
        Parcel p = new OvernightParcel("ORD1003");
        TestSupport.assertEquals("speed", "Overnight", p.getSpeedName());
        TestSupport.assertEquals("days", 1, p.getDeliveryDays());
        TestSupport.assertEquals("base cost", 100.0, p.getBaseCost());
        TestSupport.assertEquals("fee", 250.0, p.calculateTotalFee());
    }

    // One Parcel variable, three different objects: each uses its own fee code
    public static void testPolymorphicFee() {
        Parcel[] parcels = { new StandardParcel("A"), new ExpressParcel("B"), new OvernightParcel("C") };
        double[] expected = { 100.0, 150.0, 250.0 };
        for (int i = 0; i < parcels.length; i++) {
            TestSupport.assertEquals("fee of " + parcels[i].getSpeedName(), expected[i],
                    parcels[i].calculateTotalFee());
        }
    }

    public static void testAdvanceTo() {
        Parcel p = new StandardParcel("ORD1001");
        TestSupport.assertNull("hub before advance", p.getCurrentHub());
        Milestone m = p.advanceTo(new Hub("Dispatch"));
        TestSupport.assertEquals("current hub", "Dispatch", p.getCurrentHub().getName());
        TestSupport.assertEquals("returned milestone hub", "Dispatch", m.getHub().getName());
        TestSupport.assertContains("history has the hub", p.getHistoryText(), "1. Dispatch (");
    }

    public static void testSetCurrentHub() {
        Parcel p = new StandardParcel("ORD1001");
        p.setCurrentHub(new Hub("Delivered"));
        TestSupport.assertEquals("current hub", "Delivered", p.getCurrentHub().getName());
    }

    public static void testAddMilestone() {
        Parcel p = new StandardParcel("ORD1001");
        p.addMilestone(new Milestone(new Hub("Dispatch"), "time-one"));
        p.addMilestone(new Milestone(new Hub("Sorting Facility"), "time-two"));
        String text = p.getHistoryText();
        TestSupport.assertContains("first event", text, "1. Dispatch (time-one)");
        TestSupport.assertContains("second event", text, "2. Sorting Facility (time-two)");
    }

    public static void testHistoryText() {
        Parcel p = new ExpressParcel("ORD1002");
        p.addMilestone(new Milestone(new Hub("Dispatch"), "t1"));
        String text = p.getHistoryText();
        TestSupport.assertContains("title line", text, "Tracking history for ORD1002 (Express)");
        TestSupport.assertContains("days line", text, "Delivery days: 2");
        TestSupport.assertContains("base cost line", text, "Base cost: 100.0");
        TestSupport.assertContains("fee line", text, "Total fee: 150.0");
    }

    public static void main(String[] args) {
        TestSupport.runAll(ParcelTest.class);
    }
}
