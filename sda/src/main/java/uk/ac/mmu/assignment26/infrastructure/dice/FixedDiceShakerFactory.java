package uk.ac.mmu.assignment26.infrastructure.dice;

import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.config.DiceType;
import uk.ac.mmu.assignment26.domain.dice.DiceShaker;
import uk.ac.mmu.assignment26.domain.dice.FixedDiceShaker;

import java.util.List;

@Component
public class FixedDiceShakerFactory implements DiceShakerFactory {
    @Override
    public DiceType getType() {
        return DiceType.FIXED;
    }

    @Override
    public DiceShaker create() {
        throw new UnsupportedOperationException("Fixed dice requires a sequence of rolls.");
    }

    @Override
    public DiceShaker create(List<Integer> fixedRolls) {
        return new FixedDiceShaker(fixedRolls);
    }
}
