public class ParcelFactoryTest {

    public static void testStandard() {
        Parcel p = ParcelFactory.makeParcel("Standard", "ORD1001");
        TestSupport.assertTrue("is StandardParcel", p instanceof StandardParcel);
        TestSupport.assertEquals("id", "ORD1001", p.getTrackingId());
    }

    public static void testExpress() {
        Parcel p = ParcelFactory.makeParcel("Express", "ORD1002");
        TestSupport.assertTrue("is ExpressParcel", p instanceof ExpressParcel);
    }

    public static void testOvernight() {
        Parcel p = ParcelFactory.makeParcel("Overnight", "ORD1003");
        TestSupport.assertTrue("is OvernightParcel", p instanceof OvernightParcel);
    }

    public static void testUnknownSpeed() {
        TestSupport.assertNull("unknown speed", ParcelFactory.makeParcel("Teleport", "ORD1004"));
    }

    public static void testSpeedIsCaseSensitive() {
        TestSupport.assertNull("lower case speed", ParcelFactory.makeParcel("standard", "ORD1005"));
    }

    public static void testNullSpeed() {
        TestSupport.assertNull("null speed", ParcelFactory.makeParcel(null, "ORD1006"));
    }

    public static void main(String[] args) {
        TestSupport.runAll(ParcelFactoryTest.class);
    }
}
