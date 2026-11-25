public class RouteTest {

    public static void testGetFirst() {
        Route route = new Route();
        TestSupport.assertEquals("first hub", "Dispatch", route.getFirst().getName());
    }

    public static void testGetNextChain() {
        Route route = new Route();
        Hub hub = route.getFirst();
        hub = route.getNext(hub);
        TestSupport.assertEquals("after Dispatch", "Sorting Facility", hub.getName());
        hub = route.getNext(hub);
        TestSupport.assertEquals("after Sorting Facility", "Out-for-Delivery", hub.getName());
        hub = route.getNext(hub);
        TestSupport.assertEquals("after Out-for-Delivery", "Delivered", hub.getName());
    }

    public static void testGetNextOfDeliveredIsNull() {
        Route route = new Route();
        TestSupport.assertNull("after Delivered", route.getNext(route.findByName("Delivered")));
    }

    public static void testFindByNameFound() {
        Route route = new Route();
        TestSupport.assertEquals("find Sorting Facility", "Sorting Facility",
                route.findByName("Sorting Facility").getName());
    }

    public static void testFindByNameNotFound() {
        Route route = new Route();
        TestSupport.assertNull("find unknown hub", route.findByName("Moon Base"));
    }

    public static void main(String[] args) {
        TestSupport.runAll(RouteTest.class);
    }
}
