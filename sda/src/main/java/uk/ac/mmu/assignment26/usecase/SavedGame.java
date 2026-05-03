package uk.ac.mmu.assignment26.usecase;

import uk.ac.mmu.assignment26.domain.config.GameConfiguration;

import java.util.List;

public record SavedGame(
        GameConfiguration configuration,
        List<Integer> diceRolls
) {
    public SavedGame {
        diceRolls = List.copyOf(diceRolls);
    }
}