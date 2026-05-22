package uk.ac.mmu.assignment26.domain.dice;

/**
 * Dice strategy for two six-sided dice.
 *
 * <p>The roll is calculated by rolling two single dice and adding the results together.
 */
public class RandomDoubleDiceShaker implements DiceShaker {
  private final DiceShaker firstDie = new RandomSingleDiceShaker();
  private final DiceShaker secondDie = new RandomSingleDiceShaker();

  /**
   * Rolls two six-sided die
   *
   * @return a random value from two to twelve
   */
  @Override
  public int shake() {
    return firstDie.shake() + secondDie.shake();
  }
}
