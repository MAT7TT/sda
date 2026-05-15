package uk.ac.mmu.assignment26.infrastructure.registry;

import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.dice.DiceShaker;
import uk.ac.mmu.assignment26.infrastructure.dice.DiceShakerFactory;
import uk.ac.mmu.assignment26.domain.config.DiceType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class DiceShakerFactoryRegistry {
    private final Map<DiceType, DiceShakerFactory> diceFactories =
            new EnumMap<>(DiceType.class);

    public DiceShakerFactoryRegistry(List<DiceShakerFactory> diceFactories) {
        for (DiceShakerFactory factory : diceFactories) {
            this.diceFactories.put(factory.getType(), factory);
        }
    }

    public DiceShaker createDiceShaker(DiceType type) {
        DiceShakerFactory factory = diceFactories.get(type);

        if (factory == null) {
            throw new IllegalArgumentException("No dice shaker factory registered for " + type);
        }

        return factory.create();
    }

    public DiceShaker createFixedDiceShaker(DiceType diceType, List<Integer> fixedRolls) {
        validateFixedRollsForDiceType(diceType, fixedRolls);

        DiceShakerFactory factory = diceFactories.get(DiceType.FIXED);

        if (factory == null) {
            throw new IllegalArgumentException("No fixed dice shaker factory registered.");
        }

        return factory.create(fixedRolls);
    }

    private void validateFixedRollsForDiceType(DiceType diceType, List<Integer> fixedRolls) {
        if (diceType == DiceType.SINGLE) {
            validateRollRange(fixedRolls, 1, 6);
            return;
        }

        if (diceType == DiceType.DOUBLE) {
            validateRollRange(fixedRolls, 2, 12);
            return;
        }

        throw new IllegalArgumentException("Fixed dice rolls require SINGLE or DOUBLE dice type");
    }

    private void validateRollRange(List<Integer> fixedRolls, int min, int max) {
        if (fixedRolls == null || fixedRolls.isEmpty()) {
            throw new IllegalArgumentException("Fixed dice sequence must not be empty");
        }

        for (Integer roll: fixedRolls) {
            if (roll == null || roll < min || roll > max) {
                throw new IllegalArgumentException(
                        "Fixed dice roll must be between " + min + " and " + max + "."
                );
            }
        }
    }
}