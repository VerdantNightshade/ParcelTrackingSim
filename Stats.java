class Stats {
  int HP, handling, fragility, haste;
  int[] delay;

  public static final int MAX_STAT = 100;
  public static final int STAGES_COUNT = CurrentStage.values().length;

  public Stats() {
    HP = MAX_STAT;
    handling = (int) 0.4 * MAX_STAT;
    fragility = (int) 0.4 * MAX_STAT;
    haste = (int) 0.9 * MAX_STAT;
    delay = new int[STAGES_COUNT];
  }

  public Stats(int handling, int fragility, int haste, int initialDelay) {
    HP = MAX_STAT;
    this.handling = handling;
    this.fragility = fragility;
    this.haste = haste;
    delay[0] = initialDelay;
  }
}
