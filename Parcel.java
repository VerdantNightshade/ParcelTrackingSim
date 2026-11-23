class Parcel {
  Stats stats;
  CurrentStage currentStage;

  Parcel() {
    stats = new Stats();
    currentStage = CurrentStage.OrderConfirmation;
  }

  void advanceStage() {
    // Advancement is not guarenteed, and depends on rng
    switch (currentStage) {
      // Depends on haste only
      case OrderConfirmation:
        
        break;

      // Depend on handling & haste
      case Pickup, Sorting, OutForDelivery:

        break;

      // Depends on fragility & haste
      case Transit:

        break;
    
      default:
        return;
    }
  }
}

enum CurrentStage {
  OrderConfirmation, Pickup, Sorting, Transit, OutForDelivery, Delivered
}
