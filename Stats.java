class Stats {
  int HP, handling, fragility;
  double haste;
  int[] delay;

  public final int MAX_STAT = 100;
  public final int STAGES_COUNT = CurrentStage.values().length;

  public Stats() {
    HP = MAX_STAT;
    handling = (int) 0.4 * MAX_STAT;
    fragility = (int) 0.4 * MAX_STAT;
    haste = 1.0;
    delay = new int[STAGES_COUNT];
  }

  public Stats(int handling, int fragility, double haste, int initialDelay) {
    HP = MAX_STAT;
    this.handling = handling;
    this.fragility = fragility;
    this.haste = haste;
    delay[0] = initialDelay;
  }
}
