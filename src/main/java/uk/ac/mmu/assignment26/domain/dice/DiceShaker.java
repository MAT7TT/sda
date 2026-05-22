package uk.ac.mmu.assignment26.domain.dice;

/**
 * Strategy interface for dice rolling
 *
 * <p>The game depends on this abstraction so that random dice and fixed dice
 * can be used interchangeably.</p>
 */
public interface DiceShaker {
  /**
   * Produces the next dice roll value.
   *
   * @return the dice roll value.
   * @throws IllegalArgumentException if a fixed dice sequence has no more rolls available
   */
  int shake();
}
