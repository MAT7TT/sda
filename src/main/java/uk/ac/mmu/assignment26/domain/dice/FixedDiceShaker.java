package uk.ac.mmu.assignment26.domain.dice;

import uk.ac.mmu.assignment26.domain.config.DiceType;

import java.util.List;

/**
 * Dice strategy that returns a fixed sequence of rolls.
 *
 * <p>This is used for deterministic demonstrations, automated tests and replay. Each roll is validated against the
 * configured dice type when the shaker is created.</p>
 */
public class FixedDiceShaker implements DiceShaker {
  private final List<Integer> rolls;
  private int currentIndex;

  /**
   * Creates a fixed dice shaker.
   *
   * @param rolls the fixed dice rolls to return
   * @param diceType the dice type used to validate the roll range
   * @throws IllegalArgumentException if the dice type is null, the roll list is empty, or any roll is invalid
   * for the dice type.
   */
  public FixedDiceShaker(List<Integer> rolls, DiceType diceType) {
    if (diceType == null) {
      throw new IllegalArgumentException("Dice type must not be null.");
    }

    if (rolls == null || rolls.isEmpty()) {
      throw new IllegalArgumentException("Fixed dice sequence must not be empty.");
    }

    for (Integer roll : rolls) {
      validateRoll(roll, diceType);
    }

    this.rolls = List.copyOf(rolls);
  }

  private void validateRoll(Integer roll, DiceType diceType) {
    if (roll == null) {
      throw new IllegalArgumentException("Fixed dice roll must not be null.");
    }

    if (!diceType.acceptsRoll(roll)) {
      throw new IllegalArgumentException(
          "Fixed dice rolls must be between "
              + diceType.getMinimumRoll()
              + " and "
              + diceType.getMaximumRoll());
    }
  }

  /**
   * Returns the next fixed dice roll.
   *
   * @return the next roll in the sequence
   * @throws IllegalStateException if all fixed rolls have already been used
   */
  @Override
  public int shake() {
    if (currentIndex >= rolls.size()) {
      throw new IllegalStateException("No more fixed dice rolls available.");
    }

    return rolls.get(currentIndex++);
  }
}
