public class Stats {
  int HP, handling, fragility;
  double haste;
  int[] delay;

  public final int MAX_HP = 100;
  public final int STAGES_COUNT = 6;

  public Stats() {
    HP = MAX_HP;
    handling = 10;
    fragility = 10;
    haste = 1.0;
    delay = new int[STAGES_COUNT];
  }

  public Stats(int handling, int fragility, double haste, int initialDelay) {
    HP = MAX_HP;
    this.handling = handling;
    this.fragility = fragility;
    this.haste = haste;
    delay[0] = initialDelay;
  }
}
