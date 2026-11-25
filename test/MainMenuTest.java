import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

// One test for the menu in Main. We type lines into System.in and read System.out.
// Runs in an empty temporary folder (see run-tests.sh).
public class MainMenuTest {

    public static void testMenuSession() throws Exception {
        TestSupport.deleteDataFiles();

        // The lines a user would type: add, advance, track, bad ID, bad choice, exit
        String typed = "1\nExpress\n"
                + "2\nORD1001\n"
                + "3\nORD1001\n"
                + "3\nABC\n"
                + "9\n"
                + "4\n";

        InputStream oldIn = System.in;
        PrintStream oldOut = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        try {
            System.setIn(new ByteArrayInputStream(typed.getBytes()));
            System.setOut(new PrintStream(captured));
            Main.main(new String[0]);
        } finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }
        String output = captured.toString();

        TestSupport.assertContains("menu is shown", output, "1. Add parcel");
        TestSupport.assertContains("add", output, "Created parcel ORD1001 (Express) at Dispatch.");
        TestSupport.assertContains("advance", output, "ORD1001 moved to Sorting Facility.");
        TestSupport.assertContains("track title", output, "Tracking history for ORD1001 (Express)");
        TestSupport.assertContains("track second hub", output, "2. Sorting Facility (");
        TestSupport.assertContains("bad ID", output, "Invalid tracking ID: ABC");
        TestSupport.assertContains("bad menu choice", output, "Choose 1, 2, 3, or 4.");
        TestSupport.assertContains("exit", output, "Goodbye.");
    }

    public static void main(String[] args) {
        TestSupport.runAll(MainMenuTest.class);
    }
}
