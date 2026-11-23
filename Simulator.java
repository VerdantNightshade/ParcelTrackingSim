import java.util.Scanner;

class Simulator {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter number of Packages: ");
    int parcelCount = sc.nextInt();

    // Initialising the various packages
    Parcel parcels[] = new Parcel[parcelCount];

    // Initialise parcel objects
    for (int i = 0; i < parcelCount; i++) {
      parcels[i] = new Parcel();
    }

    sc.close();
  }
}
