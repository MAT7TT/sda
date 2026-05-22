package uk.ac.mmu.assignment26.domain.dice;

import java.util.Random;

/**
 * Dice strategy for a single six-sided die.
 *
 * <p>Each call returns a random value between one and sizx.</p>
 */
public class RandomSingleDiceShaker implements DiceShaker {
  private final Random random = new Random();

  /**
   * Rolls one six-sided die.
   *
   * @return a random value from one to six
   */
  @Override
  public int shake() {
    return random.nextInt(6) + 1;
  }
}
