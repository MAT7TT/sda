package uk.ac.mmu.assignment26.infrastructure.scenarios;

import uk.ac.mmu.assignment26.domain.config.GameConfiguration;

import java.util.List;

public record GameScenario(
        String title,
        GameConfiguration configuration,
        List<Integer> fixedDiceRolls
) {
    public boolean usesFixedDice() {
        return fixedDiceRolls != null && !fixedDiceRolls.isEmpty();
    }
}