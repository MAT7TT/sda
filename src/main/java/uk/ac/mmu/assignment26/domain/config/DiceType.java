package uk.ac.mmu.assignment26.domain.config;

public enum DiceType {
  SINGLE(1, 6),
  DOUBLE(2, 12);

  private final int minimumRoll;
  private final int maximumRoll;

  DiceType(int minimumRoll, int maximumRoll) {
    this.minimumRoll = minimumRoll;
    this.maximumRoll = maximumRoll;
  }

  public int getMinimumRoll() {
    return minimumRoll;
  }

  public int getMaximumRoll() {
    return maximumRoll;
  }

  public boolean acceptsRoll(int roll) {
    return roll >= minimumRoll && roll <= maximumRoll;
  }
}
