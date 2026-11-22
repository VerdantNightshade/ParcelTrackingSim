import java.util.Random;

class RNG {
  private RNG() {}
  
  static Random random = new Random();

  static double getNext() {
    return Stats.MAX_STAT * Math.abs(random.nextGaussian());
  }
}
