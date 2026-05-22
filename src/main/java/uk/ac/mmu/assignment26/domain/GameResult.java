package uk.ac.mmu.assignment26.domain;

import java.util.List;

/**
 * Value object returned when a game has completed.
 *
 * @param winnerName the name of the winning player
 * @param winnerTurns the number of turns taken by the winner
 * @param totalTurns the total number of turns in the game
 * @param diceRolls the dice rolls used during the game
 */
public record GameResult(
    String winnerName, int winnerTurns, int totalTurns, List<Integer> diceRolls) {
  public GameResult {
    diceRolls = List.copyOf(diceRolls);
  }
}
