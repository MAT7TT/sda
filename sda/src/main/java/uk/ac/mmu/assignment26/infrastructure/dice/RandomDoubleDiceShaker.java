package uk.ac.mmu.assignment26.infrastructure.dice;

import uk.ac.mmu.assignment26.domain.dice.DiceShaker;

public class RandomDoubleDiceShaker implements DiceShaker {

    @Override
    public int shake() {
        return new RandomSingleDiceShaker().shake() + new RandomSingleDiceShaker().shake();
    }
}
