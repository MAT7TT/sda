package uk.ac.mmu.assignment26.infrastructure.registry;

import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.dice.DiceShaker;
import uk.ac.mmu.assignment26.domain.dice.FixedDiceShaker;
import uk.ac.mmu.assignment26.infrastructure.dice.DiceShakerFactory;
import uk.ac.mmu.assignment26.domain.config.DiceType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class DiceShakerFactoryRegistry {
  private final Map<DiceType, DiceShakerFactory> diceFactories = new EnumMap<>(DiceType.class);

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

  public DiceShaker createFixedDiceShaker(DiceType diceType, List<Integer> fixedRolls) {
    if (diceType == null) {
      throw new IllegalArgumentException("Dice type must not be null.");
    }

    return new FixedDiceShaker(fixedRolls, diceType);
  }
}
