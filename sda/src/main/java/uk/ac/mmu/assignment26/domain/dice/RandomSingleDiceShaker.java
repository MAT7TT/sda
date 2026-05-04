package uk.ac.mmu.assignment26.domain.dice;

import java.util.Random;

public class RandomSingleDiceShaker implements DiceShaker {
    private final Random random = new Random();

    @Override
    public int shake() {
        return random.nextInt(6) + 1;
    }
}
