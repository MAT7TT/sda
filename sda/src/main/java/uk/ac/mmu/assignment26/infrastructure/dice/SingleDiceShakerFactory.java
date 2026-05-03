package uk.ac.mmu.assignment26.infrastructure.dice;

import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.dice.DiceShaker;
import uk.ac.mmu.assignment26.domain.dice.DiceShakerFactory;
import uk.ac.mmu.assignment26.domain.config.DiceType;

@Component
public class SingleDiceShakerFactory implements DiceShakerFactory {
    @Override
    public DiceType getType() {
        return DiceType.SINGLE;
    }

    @Override
    public DiceShaker create() {
        return new RandomSingleDiceShaker();
    }
}
