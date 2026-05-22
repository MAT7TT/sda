package uk.ac.mmu.assignment26.infrastructure.registry;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.config.DiceType;
import uk.ac.mmu.assignment26.domain.dice.DiceShaker;
import uk.ac.mmu.assignment26.domain.dice.FixedDiceShaker;
import uk.ac.mmu.assignment26.infrastructure.dice.DiceShakerFactory;

/**
 * Registry for dice shaker factories.
 *
 * <p>Spring supplies the concrete dice factories, and this registry maps each dice type to the
 * factory that creates the matching dice strategy.
 */
@Component
public class DiceShakerFactoryRegistry {
  private final Map<DiceType, DiceShakerFactory> diceFactories = new EnumMap<>(DiceType.class);

  /**
   * Create a dice shaker factory registry.
   *
   * @param diceFactories the available dice factories
   * @throws IllegalArgumentException if the factory list is empty, contains null, or contains
   *     duplicate dice types
   */
  public DiceShakerFactoryRegistry(List<DiceShakerFactory> diceFactories) {
    if (diceFactories == null || diceFactories.isEmpty()) {
      throw new IllegalArgumentException("Dice shaker factories must not be empty.");
    }

    for (DiceShakerFactory factory : diceFactories) {
      if (factory == null) {
        throw new IllegalArgumentException("Dice shaker factories must not contain null.");
      }

      DiceType type = factory.getType();

      if (type == null) {
        throw new IllegalArgumentException("Dice shaker factory type must not be null");
      }

      if (this.diceFactories.containsKey(type)) {
        throw new IllegalArgumentException("Duplicate dice shaker factory for " + type + ".");
      }

      this.diceFactories.put(type, factory);
    }
  }

  /**
   * Creates a random dice shaker for the supplied dice type.
   *
   * @param type the dice type
   * @return the created dice shaker
   * @throws IllegalArgumentException if the type is null or unregistered
   */
  public DiceShaker createDiceShaker(DiceType type) {
    if (type == null) {
      throw new IllegalArgumentException("Dice type must not be null.");
    }

    DiceShakerFactory factory = diceFactories.get(type);

    if (factory == null) {
      throw new IllegalArgumentException("No dice shaker factory registered for " + type + ".");
    }

    return factory.create();
  }

  /**
   * Creates a fixed dice shaker for the supplied dice types and rolls.
   *
   * @param diceType the dice type used to validate the rolls
   * @param fixedRolls the fixed dice rolls
   * @return the created fixed dice shaker
   * @throws IllegalArgumentException if the dice type or rolls are invalid
   */
  public DiceShaker createFixedDiceShaker(DiceType diceType, List<Integer> fixedRolls) {
    if (diceType == null) {
      throw new IllegalArgumentException("Dice type must not be null.");
    }

    return new FixedDiceShaker(fixedRolls, diceType);
  }
}
