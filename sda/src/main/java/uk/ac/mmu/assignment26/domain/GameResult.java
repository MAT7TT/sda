package uk.ac.mmu.assignment26.domain;

import java.util.List;

public record GameResult(
        String winnerName,
        int winnerTurns,
        int totalTurns,
        List<Integer> diceRolls
) {
    public GameResult {
        diceRolls = List.copyOf(diceRolls);
    }
}