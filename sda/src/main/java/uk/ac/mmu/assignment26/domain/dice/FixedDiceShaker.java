package uk.ac.mmu.assignment26.domain.dice;

import java.util.ArrayList;
import java.util.List;

public class FixedDiceShaker implements DiceShaker {
    private final List<Integer> rolls;
    private int currentIndex;
    private static final int MAX_ROLL = 12;
    private static final int MIN_ROLL = 1;

    public FixedDiceShaker(List<Integer> rolls) {
        if (rolls == null || rolls.isEmpty()) {
            throw new IllegalArgumentException("Fixed dice sequence must not be empty.");
        }

        for (Integer roll : rolls) {
            validateRoll(roll);
        }

        this.rolls = new ArrayList<>(rolls);
    }

    public FixedDiceShaker(int... rolls) {
        if (rolls == null || rolls.length == 0) {
            throw new IllegalArgumentException("Fixed dice sequence must not be empty.");
        }

        this.rolls = new ArrayList<>();

        for (int roll : rolls) {
            validateRoll(roll);
            this.rolls.add(roll);
        }
    }

    private void validateRoll(Integer roll) {
        if (roll == null) {
            throw new IllegalArgumentException(("Fixed dice roll must not be null"));
        }

        if (roll < MIN_ROLL || roll > MAX_ROLL) {
            throw new IllegalArgumentException(
                    "Fixed dice rolls must be between " + MIN_ROLL + " and " + MAX_ROLL);
        }
    }

    @Override
    public int shake() {
        if (currentIndex >= rolls.size()) {
            throw new IllegalStateException("No more fixed dice rolls available.");
        }

        return rolls.get(currentIndex++);
    }

    public List<Integer> getRolls() {
        return new ArrayList<>(rolls);
    }

    public String describeSequence() {
        return rolls.toString();
    }
}