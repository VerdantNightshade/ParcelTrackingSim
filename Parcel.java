class Parcel {
  Stats stats;
  CurrentStage currentstage;

  Parcel() {
    stats = new Stats();
    currentstage = CurrentStage.OrderConfirmation;
  }
}

enum CurrentStage {
  OrderConfirmation, Pickup, Sorting, Transit, OutForDelivery, Delivered
}
