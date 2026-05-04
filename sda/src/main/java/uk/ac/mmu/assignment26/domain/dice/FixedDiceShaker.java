package uk.ac.mmu.assignment26.domain.dice;

import java.util.ArrayList;
import java.util.List;

public class FixedDiceShaker implements DiceShaker {
    private final List<Integer> rolls;
    private int currentIndex;

    public FixedDiceShaker(List<Integer> rolls) {
        if (rolls == null || rolls.isEmpty()) {
            throw new IllegalArgumentException("Fixed dice sequence must not be empty.");
        }

        this.rolls = new ArrayList<>(rolls);
    }

    public FixedDiceShaker(int... rolls) {
        if (rolls == null || rolls.length == 0) {
            throw new IllegalArgumentException("Fixed dice sequence must not be empty.");
        }

        this.rolls = new ArrayList<>();

        for (int roll : rolls) {
            this.rolls.add(roll);
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