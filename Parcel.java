class Parcel {
  Stats stats;
  CurrentStage currentStage;
  int delayIndex;

  Parcel() {
    stats = new Stats();
    currentStage = CurrentStage.OrderConfirmation;
    delayIndex = 0;
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

  void checkDelay() {
    double rngRoll = RNG.getNext();

    // If rngRoll is greater that haste, we suffer a delay
    if (rngRoll > stats.haste) {
      stats.delay[delayIndex]++;
    } else {
    // If not, we proceed to the next stage
      stats.delay[delayIndex + 1] += stats.delay[delayIndex];
      updateCurrentStage();
      delayIndex++;
    }
  }
  
  void updateCurrentStage() {
    switch (currentStage) {
      case OrderConfirmation:
        currentStage = CurrentStage.Pickup;
        break;

      case Pickup:
        currentStage = CurrentStage.Sorting;
        break;

      case Sorting:
        currentStage = CurrentStage.Transit;
        break;

      case Transit:
        currentStage = CurrentStage.OutForDelivery;
        break;

      case OutForDelivery:
        currentStage = CurrentStage.Delivered;
        break;
    
      default:
        break;
    }
  }
}

enum CurrentStage {
  OrderConfirmation, Pickup, Sorting, Transit, OutForDelivery, Delivered
}
