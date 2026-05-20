package uk.ac.mmu.assignment26.domain.dice;

import uk.ac.mmu.assignment26.domain.config.DiceType;

import java.util.List;

public class FixedDiceShaker implements DiceShaker {
    private final List<Integer> rolls;
    private int currentIndex;

    public FixedDiceShaker(List<Integer> rolls, DiceType diceType) {
        if (diceType == null) {
            throw new IllegalArgumentException("Dice type must not be null.");
        }

        if (rolls == null || rolls.isEmpty()) {
            throw new IllegalArgumentException("Fixed dice sequence must not be empty.");
        }

        for (Integer roll : rolls) {
            validateRoll(roll, diceType);
        }

        this.rolls = List.copyOf(rolls);
    }

    private void validateRoll(Integer roll, DiceType diceType) {
        if (roll == null) {
            throw new IllegalArgumentException(("Fixed dice roll must not be null."));
        }

        if (!diceType.acceptsRoll(roll)) {
            throw new IllegalArgumentException(
                    "Fixed dice rolls must be between "
                            + diceType.getMinimumRoll()
                            + " and "
                            + diceType.getMaximumRoll()
            );
        }
    }

    @Override
    public int shake() {
        if (currentIndex >= rolls.size()) {
            throw new IllegalStateException("No more fixed dice rolls available.");
        }

        return rolls.get(currentIndex++);
    }
}