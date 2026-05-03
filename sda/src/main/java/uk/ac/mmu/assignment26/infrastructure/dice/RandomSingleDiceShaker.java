package uk.ac.mmu.assignment26.infrastructure.dice;

import uk.ac.mmu.assignment26.domain.dice.DiceShaker;

import java.util.Random;

public class RandomSingleDiceShaker implements DiceShaker {
    private final Random random = new Random();

    @Override
    public int shake() {
        return random.nextInt(6) + 1;
    }
}
