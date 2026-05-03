package uk.ac.mmu.assignment26.infrastructure.dice;

import uk.ac.mmu.assignment26.domain.dice.DiceShaker;
import uk.ac.mmu.assignment26.domain.dice.DiceShakerFactory;
import uk.ac.mmu.assignment26.domain.config.DiceType;

public class DoubleDiceShakerFactory implements DiceShakerFactory {
    @Override
    public DiceType getType() {
        return DiceType.DOUBLE;
    }

    @Override
    public DiceShaker create() {
        return new RandomDoubleDiceShaker();
    }
}
