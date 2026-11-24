class AirMailedParcel extends Parcel {
  AirMailedParcel() {
    stats = new Stats(85, 95, 96, 0);
    currentStage = CurrentStage.OrderConfirmation;
    delayIndex = 0;
    isBeatUp = false;
    isDamaged = false;
  }
}

class ExpressParcel extends Parcel {
  ExpressParcel() {
    stats = new Stats(60, 60, 93, 0);
    currentStage = CurrentStage.OrderConfirmation;
    delayIndex = 0;
    isBeatUp = false;
    isDamaged = false;
  }
}

class OvernightParcel extends Parcel {
  OvernightParcel() {
    stats = new Stats(60, 50, 97, 0);
    currentStage = CurrentStage.OrderConfirmation;
    delayIndex = 0;
    isBeatUp = false;
    isDamaged = false;
  }
}

class HeavyParcel extends Parcel {
  HeavyParcel() {
    stats = new Stats(70, 70, 30, 0);
    currentStage = CurrentStage.OrderConfirmation;
    delayIndex = 0;
    isBeatUp = false;
    isDamaged = false;
  }
}
