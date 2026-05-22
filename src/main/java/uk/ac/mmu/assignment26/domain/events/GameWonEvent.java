package uk.ac.mmu.assignment26.domain.events;

/**
 * Event published when a player wins the game.
 *
 * @param playerName the winning player
 * @param playerTurns the number of turns taken by the winner
 * @param totalTurns the total number of turns in the game
 */
public record GameWonEvent(String playerName, int playerTurns, int totalTurns) {}
