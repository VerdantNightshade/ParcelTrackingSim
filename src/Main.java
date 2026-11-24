import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // System.in is the keyboard. The Scanner reads what you type
        Scanner sc = new Scanner(System.in);

        try {
            Tracker tracker = new Tracker();

            while (true) {
                System.out.println();
                System.out.println("1. Add parcel");
                System.out.println("2. Advance parcel");
                System.out.println("3. Track parcel");
                System.out.println("4. Exit");
                System.out.print("Choose: ");
                String choice = sc.nextLine();

                try {
                    switch (choice) {
                        case "1":
                            System.out.print("Speed (Standard, Express, Overnight): ");
                            System.out.println(tracker.addParcel(sc.nextLine()));
                            break;
                        case "2":
                            System.out.print("Tracking ID: ");
                            System.out.println(tracker.advanceParcel(sc.nextLine()));
                            break;
                        case "3":
                            System.out.print("Tracking ID: ");
                            System.out.print(tracker.trackParcel(sc.nextLine()));
                            break;
                        case "4":
                            System.out.println("Goodbye.");
                            sc.close();
                            return; // return ends main, so it ends the loop too
                        default:
                            System.out.println("Choose 1, 2, 3, or 4.");
                    }
                } catch (InvalidTrackingIdException e) {
                    // A bad ID prints a message and the menu comes back
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }
}
