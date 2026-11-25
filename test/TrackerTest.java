import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// Tracker writes data files in the current folder. run-tests.sh runs this
// class in an empty temporary folder. Each test starts by deleting the files.
public class TrackerTest {

    private static Tracker newTracker() throws IOException {
        TestSupport.deleteDataFiles();
        return new Tracker();
    }

    // Calls trackParcel and returns the exception message, or null if no exception came
    private static String trackError(Tracker tracker, String id) {
        try {
            tracker.trackParcel(id);
            return null;
        } catch (InvalidTrackingIdException e) {
            return e.getMessage();
        }
    }

    public static void testAddParcelMessage() throws Exception {
        Tracker t = newTracker();
        TestSupport.assertEquals("add message", "Created parcel ORD1001 (Standard) at Dispatch.",
                t.addParcel("Standard"));
    }

    public static void testIdsCount() throws Exception {
        Tracker t = newTracker();
        TestSupport.assertContains("first id", t.addParcel("Standard"), "ORD1001");
        TestSupport.assertContains("second id", t.addParcel("Express"), "ORD1002");
        TestSupport.assertContains("third id", t.addParcel("Overnight"), "ORD1003");
    }

    // The manifest has ORD1001 and ORD1003, so ORD1002 is missing.
    // The next ID must be ORD1004. It must not repeat ORD1003.
    public static void testIdAfterGapInManifest() throws Exception {
        TestSupport.deleteDataFiles();
        PrintWriter pw = new PrintWriter(new FileWriter("manifest.txt"));
        pw.println("ORD1001,Standard,Dispatch");
        pw.println("ORD1003,Express,Dispatch");
        pw.close();
        Tracker t = new Tracker();
        TestSupport.assertContains("next id", t.addParcel("Standard"), "ORD1004");
    }

    public static void testNullId() throws Exception {
        Tracker t = newTracker();
        TestSupport.assertEquals("null id", "Invalid tracking ID: null", trackError(t, null));
    }

    public static void testUnknownSpeed() throws Exception {
        Tracker t = newTracker();
        TestSupport.assertEquals("unknown speed message",
                "Unknown speed: Teleport. Use Standard, Express, or Overnight.", t.addParcel("Teleport"));
        // A failed add must not use up an ID
        TestSupport.assertContains("next id still ORD1001", t.addParcel("Standard"), "ORD1001");
    }

    public static void testAdvanceThroughAllHubs() throws Exception {
        Tracker t = newTracker();
        t.addParcel("Standard");
        TestSupport.assertEquals("step 1", "ORD1001 moved to Sorting Facility.", t.advanceParcel("ORD1001"));
        TestSupport.assertEquals("step 2", "ORD1001 moved to Out-for-Delivery.", t.advanceParcel("ORD1001"));
        TestSupport.assertEquals("step 3", "ORD1001 moved to Delivered.", t.advanceParcel("ORD1001"));
        TestSupport.assertEquals("step 4", "ORD1001 is already delivered.", t.advanceParcel("ORD1001"));
    }

    public static void testTrackParcel() throws Exception {
        Tracker t = newTracker();
        t.addParcel("Express");
        t.advanceParcel("ORD1001");
        String text = t.trackParcel("ORD1001");
        TestSupport.assertContains("title", text, "Tracking history for ORD1001 (Express)");
        TestSupport.assertContains("first hub", text, "1. Dispatch (");
        TestSupport.assertContains("second hub", text, "2. Sorting Facility (");
        TestSupport.assertContains("days", text, "Delivery days: 2");
        TestSupport.assertContains("fee", text, "Total fee: 150.0");
    }

    public static void testIdWithoutDigits() throws Exception {
        Tracker t = newTracker();
        TestSupport.assertEquals("ABC", "Invalid tracking ID: ABC", trackError(t, "ABC"));
    }

    public static void testIdWithPrefixOnly() throws Exception {
        Tracker t = newTracker();
        TestSupport.assertEquals("ORD", "Invalid tracking ID: ORD", trackError(t, "ORD"));
    }

    public static void testIdWithLetterAfterDigits() throws Exception {
        Tracker t = newTracker();
        TestSupport.assertEquals("ORD12X", "Invalid tracking ID: ORD12X", trackError(t, "ORD12X"));
    }

    // The empty string does not start with ORD, so it is an invalid ID
    public static void testEmptyId() throws Exception {
        Tracker t = newTracker();
        TestSupport.assertEquals("empty id", "Invalid tracking ID: ", trackError(t, ""));
    }

    public static void testLowerCasePrefix() throws Exception {
        Tracker t = newTracker();
        TestSupport.assertEquals("ord1001", "Invalid tracking ID: ord1001", trackError(t, "ord1001"));
    }

    public static void testIdNotFound() throws Exception {
        Tracker t = newTracker();
        TestSupport.assertEquals("ORD9999", "Parcel not found: ORD9999", trackError(t, "ORD9999"));
    }

    public static void testAdvanceWithBadId() throws Exception {
        Tracker t = newTracker();
        String message = null;
        try {
            t.advanceParcel("ABC");
        } catch (InvalidTrackingIdException e) {
            message = e.getMessage();
        }
        TestSupport.assertEquals("advance with bad id", "Invalid tracking ID: ABC", message);
    }

    public static void main(String[] args) {
        TestSupport.runAll(TrackerTest.class);
    }
}
