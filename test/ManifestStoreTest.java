import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

// Runs in an empty temporary folder (see run-tests.sh). Each test deletes the data files first.
public class ManifestStoreTest {

    private static String readFile(String name) throws Exception {
        String text = "";
        Scanner sc = new Scanner(new File(name));
        while (sc.hasNextLine()) {
            text = text + sc.nextLine() + "\n";
        }
        sc.close();
        return text;
    }

    public static void testLoadWithNoFile() throws Exception {
        TestSupport.deleteDataFiles();
        ManifestStore store = new ManifestStore(new Route());
        TestSupport.assertEquals("parcel count", 0, store.loadManifest().size());
    }

    public static void testSaveAndLoadRoundTrip() throws Exception {
        TestSupport.deleteDataFiles();
        Route route = new Route();
        ManifestStore store = new ManifestStore(route);

        Parcel a = new StandardParcel("ORD1001");
        a.setCurrentHub(route.findByName("Dispatch"));
        Parcel b = new OvernightParcel("ORD1002");
        b.setCurrentHub(route.findByName("Out-for-Delivery"));
        ArrayList<Parcel> parcels = new ArrayList<Parcel>();
        parcels.add(a);
        parcels.add(b);
        store.saveManifest(parcels);

        TestSupport.assertEquals("manifest text",
                "ORD1001,Standard,Dispatch\nORD1002,Overnight,Out-for-Delivery\n", readFile("manifest.txt"));

        ArrayList<Parcel> loaded = store.loadManifest();
        TestSupport.assertEquals("parcel count", 2, loaded.size());
        TestSupport.assertEquals("first id", "ORD1001", loaded.get(0).getTrackingId());
        TestSupport.assertTrue("first is StandardParcel", loaded.get(0) instanceof StandardParcel);
        TestSupport.assertEquals("first hub", "Dispatch", loaded.get(0).getCurrentHub().getName());
        TestSupport.assertEquals("second id", "ORD1002", loaded.get(1).getTrackingId());
        TestSupport.assertTrue("second is OvernightParcel", loaded.get(1) instanceof OvernightParcel);
        TestSupport.assertEquals("second hub", "Out-for-Delivery", loaded.get(1).getCurrentHub().getName());
    }

    public static void testSaveReplacesOldContent() throws Exception {
        TestSupport.deleteDataFiles();
        Route route = new Route();
        ManifestStore store = new ManifestStore(route);
        Parcel a = new StandardParcel("ORD1001");
        a.setCurrentHub(route.getFirst());
        ArrayList<Parcel> parcels = new ArrayList<Parcel>();
        parcels.add(a);
        store.saveManifest(parcels);
        store.saveManifest(parcels); // second save must not double the line
        TestSupport.assertEquals("one line only", "ORD1001,Standard,Dispatch\n", readFile("manifest.txt"));
    }

    public static void testAppendLogKeepsOldLines() throws Exception {
        TestSupport.deleteDataFiles();
        Route route = new Route();
        ManifestStore store = new ManifestStore(route);
        store.appendLog("ORD1001", new Milestone(route.findByName("Dispatch"), "time-one"));
        store.appendLog("ORD1001", new Milestone(route.findByName("Sorting Facility"), "time-two"));
        TestSupport.assertEquals("log text",
                "ORD1001,Dispatch,time-one\nORD1001,Sorting Facility,time-two\n", readFile("status-log.txt"));
    }

    public static void testHistoryIsReloaded() throws Exception {
        TestSupport.deleteDataFiles();
        Route route = new Route();
        ManifestStore store = new ManifestStore(route);

        Parcel a = new ExpressParcel("ORD1001");
        a.setCurrentHub(route.findByName("Sorting Facility"));
        Parcel b = new StandardParcel("ORD1002");
        b.setCurrentHub(route.getFirst());
        ArrayList<Parcel> parcels = new ArrayList<Parcel>();
        parcels.add(a);
        parcels.add(b);
        store.saveManifest(parcels);
        store.appendLog("ORD1001", new Milestone(route.findByName("Dispatch"), "time-one"));
        store.appendLog("ORD1002", new Milestone(route.findByName("Dispatch"), "time-other"));
        store.appendLog("ORD1001", new Milestone(route.findByName("Sorting Facility"), "time-two"));

        ArrayList<Parcel> loaded = store.loadManifest();
        String textA = loaded.get(0).getHistoryText();
        TestSupport.assertContains("A event 1", textA, "1. Dispatch (time-one)");
        TestSupport.assertContains("A event 2", textA, "2. Sorting Facility (time-two)");
        TestSupport.assertFalse("A has no event of B", textA.contains("time-other"));
        TestSupport.assertContains("B event", loaded.get(1).getHistoryText(), "1. Dispatch (time-other)");
    }

    public static void main(String[] args) {
        TestSupport.runAll(ManifestStoreTest.class);
    }
}
