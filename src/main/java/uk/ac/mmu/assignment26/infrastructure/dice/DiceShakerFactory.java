package uk.ac.mmu.assignment26.infrastructure.dice;

import uk.ac.mmu.assignment26.domain.config.DiceType;
import uk.ac.mmu.assignment26.domain.dice.DiceShaker;

import java.util.List;

/** Factory interface for creating random dice shakers. */
public interface DiceShakerFactory {
  /**
   * Returns the dice type created by this factory.
   *
   * @return the dice type
   */
  DiceType getType();

  /**
   * Creates a dice shaker.
   *
   * @return the created dice shaker
   */
  DiceShaker create();
}
