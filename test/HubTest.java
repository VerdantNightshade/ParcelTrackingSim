public class HubTest {

    public static void testGetName() {
        Hub hub = new Hub("Dispatch");
        TestSupport.assertEquals("hub name", "Dispatch", hub.getName());
    }

    public static void testEqualsSameName() {
        TestSupport.assertTrue("same name", new Hub("Dispatch").equals(new Hub("Dispatch")));
    }

    public static void testEqualsDifferentName() {
        TestSupport.assertFalse("different name", new Hub("Dispatch").equals(new Hub("Delivered")));
    }

    public static void testEqualsNullAndOtherType() {
        Hub hub = new Hub("Dispatch");
        TestSupport.assertFalse("null", hub.equals(null));
        TestSupport.assertFalse("a String", hub.equals("Dispatch"));
    }

    // Equal hubs must have the same hash code
    public static void testHashCode() {
        TestSupport.assertEquals("hash code", new Hub("Dispatch").hashCode(), new Hub("Dispatch").hashCode());
    }

    public static void main(String[] args) {
        TestSupport.runAll(HubTest.class);
    }
}
