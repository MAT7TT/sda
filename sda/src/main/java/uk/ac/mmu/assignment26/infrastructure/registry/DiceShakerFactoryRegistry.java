package uk.ac.mmu.assignment26.infrastructure.registry;

import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.dice.DiceShaker;
import uk.ac.mmu.assignment26.domain.dice.DiceShakerFactory;
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

    public DiceShaker createFixedDiceShaker(List<Integer> fixedRolls) {
        DiceShakerFactory factory = diceFactories.get(DiceType.FIXED);

        if (factory == null) {
            throw new IllegalArgumentException("No fixed dice shaker factory registered.");
        }

        return factory.create(fixedRolls);
    }
}