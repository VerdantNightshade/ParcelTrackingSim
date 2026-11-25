// Checks that a second Tracker loads what the first Tracker saved.
// Runs in an empty temporary folder (see run-tests.sh).
public class PersistenceTest {

    public static void testStateAndHistoryAreLoaded() throws Exception {
        TestSupport.deleteDataFiles();

        Tracker first = new Tracker();
        first.addParcel("Standard");
        first.addParcel("Overnight");
        first.advanceParcel("ORD1001");
        first.advanceParcel("ORD1001");

        // A new Tracker reads manifest.txt and status-log.txt
        Tracker second = new Tracker();
        String text = second.trackParcel("ORD1001");
        TestSupport.assertContains("history 1", text, "1. Dispatch (");
        TestSupport.assertContains("history 2", text, "2. Sorting Facility (");
        TestSupport.assertContains("history 3", text, "3. Out-for-Delivery (");
        TestSupport.assertContains("speed", text, "(Standard)");

        TestSupport.assertContains("other parcel loaded", second.trackParcel("ORD1002"),
                "Tracking history for ORD1002 (Overnight)");

        // The current hub is loaded too: the next step goes to Delivered
        TestSupport.assertEquals("next step", "ORD1001 moved to Delivered.", second.advanceParcel("ORD1001"));
    }

    // The ID counter must continue after a restart
    public static void testIdCounterContinues() throws Exception {
        TestSupport.deleteDataFiles();
        Tracker first = new Tracker();
        first.addParcel("Standard");
        Tracker second = new Tracker();
        TestSupport.assertContains("next id", second.addParcel("Express"), "ORD1002");
    }

    public static void main(String[] args) {
        TestSupport.runAll(PersistenceTest.class);
    }
}
