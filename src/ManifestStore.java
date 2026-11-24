import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ManifestStore {

    private String manifestFile = "manifest.txt";
    private String logFile = "status-log.txt";

    // The store needs the route to turn a hub name from a file back into a Hub
    private Route route;

    public ManifestStore(Route route) {
        this.route = route;
    }

    // Writes all parcels to the manifest. The old content is replaced
    public void saveManifest(ArrayList<Parcel> parcels) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(manifestFile));
        for (int i = 0; i < parcels.size(); i++) {
            Parcel p = parcels.get(i);
            pw.println(p.getTrackingId() + "," + p.getSpeedName() + "," + p.getCurrentHub().getName());
        }
        pw.close(); // always close a file when done
    }

    // Adds one line to the end of the status log
    public void appendLog(String trackingId, Milestone milestone) throws IOException {
        // The "true" means: add to the end, do not erase the old content
        PrintWriter pw = new PrintWriter(new FileWriter(logFile, true));
        pw.println(trackingId + "," + milestone.getHub().getName() + "," + milestone.getTime());
        pw.close();
    }

    // Reads the manifest and builds the parcel list. Each parcel also gets its history
    public ArrayList<Parcel> loadManifest() throws IOException {
        ArrayList<Parcel> parcels = new ArrayList<Parcel>();
        File file = new File(manifestFile);

        // No file yet means no parcels yet
        if (!file.exists()) {
            return parcels;
        }

        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String[] parts = sc.nextLine().split(","); // parts[0]=id, parts[1]=speed, parts[2]=hub
            Parcel parcel = ParcelFactory.makeParcel(parts[1], parts[0]);
            parcel.setCurrentHub(route.findByName(parts[2]));
            loadHistory(parcel);
            parcels.add(parcel);
        }
        sc.close();
        return parcels;
    }

    // Reads the status log and gives the parcel its own events
    private void loadHistory(Parcel parcel) throws IOException {
        File file = new File(logFile);
        if (!file.exists()) {
            return;
        }
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String[] parts = sc.nextLine().split(","); // parts[0]=id, parts[1]=hub, parts[2]=time
            if (parts[0].equals(parcel.getTrackingId())) {
                Hub hub = route.findByName(parts[1]);
                parcel.addMilestone(new Milestone(hub, parts[2]));
            }
        }
        sc.close();
    }
}
