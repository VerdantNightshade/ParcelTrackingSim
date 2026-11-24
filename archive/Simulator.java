import java.util.Scanner;

class Simulator {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter number of Packages: ");
    int parcelCount = sc.nextInt();

    // Initialising the various packages
    Parcel parcels[] = new Parcel[parcelCount];

    // Initialise Parcel objects
    for (int i = 0; i < parcelCount; i++) {
      parcels[i] = new Parcel();
    }

    // Simulate the journey of every Parcel
    for (Parcel parcel: parcels) {
      shipParcel(parcel);
    }

    // Print Statistics
    printStats(parcels);

    sc.close();
  }

  // Simulate the journey of a single Parcel
  static void shipParcel(Parcel parcel) {
    do {
      parcel.advanceStage();
    } while(parcel.currentStage != CurrentStage.Delivered);

    parcel.checkDamage();
  }

  // Prints the statistics of the delivered parcels
  static void printStats(Parcel parcels[]) {}
}
