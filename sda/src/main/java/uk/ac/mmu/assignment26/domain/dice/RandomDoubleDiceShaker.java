package uk.ac.mmu.assignment26.domain.dice;

public class RandomDoubleDiceShaker implements DiceShaker {

    @Override
    public int shake() {
        return new RandomSingleDiceShaker().shake() + new RandomSingleDiceShaker().shake();
    }
}
