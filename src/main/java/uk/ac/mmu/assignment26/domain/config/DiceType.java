package uk.ac.mmu.assignment26.domain.config;

/**
 * Configuration values for the dice variation.
 *
 * <p>Each dice type defines the valid range of a complete roll. A
 * single die accepts one to six, while two dice accept two to twelve.</p>
 */
public enum DiceType {
  /** A single six-sided die */
  SINGLE(1, 6),
  /** Two six-sided dice */
  DOUBLE(2, 12);

  private final int minimumRoll;
  private final int maximumRoll;

  DiceType(int minimumRoll, int maximumRoll) {
    this.minimumRoll = minimumRoll;
    this.maximumRoll = maximumRoll;
  }

  /**
   * Returns the minimum valid roll for this dice type.
   *
   * @return the minimum roll
   */
  public int getMinimumRoll() {
    return minimumRoll;
  }

  /**
   * Returns the maximum valid roll for this dice type.
   *
   * @return the maximum roll
   */
  public int getMaximumRoll() {
    return maximumRoll;
  }

  /**
   * Checks whether a roll is valid for this dice type.
   *
   * @param roll the roll to check
   * @return true if the roll is within the valid range
   */
  public boolean acceptsRoll(int roll) {
    return roll >= minimumRoll && roll <= maximumRoll;
  }
}
