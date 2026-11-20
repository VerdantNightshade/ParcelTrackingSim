abstract class Parcel {
  Stats stats;
  CurrentStage currentstage;
}

enum CurrentStage {
  OrderConfirmation, Pickup, Sorting, Transit, OutForDelivery, Delivered
}
