public class MilestoneTest {

    public static void testConstructorWithHub() {
        Hub hub = new Hub("Dispatch");
        Milestone milestone = new Milestone(hub);
        TestSupport.assertEquals("hub", "Dispatch", milestone.getHub().getName());
        TestSupport.assertNotNull("time", milestone.getTime());
        TestSupport.assertFalse("time is not empty", milestone.getTime().isEmpty());
    }

    public static void testConstructorWithHubAndTime() {
        Hub hub = new Hub("Delivered");
        Milestone milestone = new Milestone(hub, "Mon Jan 01 10:00:00 2024");
        TestSupport.assertEquals("hub", "Delivered", milestone.getHub().getName());
        TestSupport.assertEquals("time is kept", "Mon Jan 01 10:00:00 2024", milestone.getTime());
    }

    public static void main(String[] args) {
        TestSupport.runAll(MilestoneTest.class);
    }
}
