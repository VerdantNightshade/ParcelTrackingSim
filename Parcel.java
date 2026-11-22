class Parcel {
  Stats stats;
  CurrentStage currentstage;

  Parcel() {
    stats = new Stats();
    currentstage = CurrentStage.OrderConfirmation;
  }

  void advanceStage() {
    if (currentstage == CurrentStage.Delivered) {
      return;
    }

    // Advancement is not guarenteed, and depends on rng
  }
}

enum CurrentStage {
  OrderConfirmation, Pickup, Sorting, Transit, OutForDelivery, Delivered
}
