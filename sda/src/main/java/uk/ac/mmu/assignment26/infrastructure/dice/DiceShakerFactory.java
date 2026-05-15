package uk.ac.mmu.assignment26.infrastructure.dice;

import uk.ac.mmu.assignment26.domain.config.DiceType;
import uk.ac.mmu.assignment26.domain.dice.DiceShaker;

import java.util.List;

public interface DiceShakerFactory {
    DiceType getType();

    DiceShaker create();

    default DiceShaker create(List<Integer> fixedRolls) {
        throw new UnsupportedOperationException(
                getType() + " dice does not support fixed rolls."
        );
    }
}
