package uk.ac.mmu.assignment26.domain.events;

import java.util.List;

/**
 * Immutable snapshot of a player's path at game start.
 *
 * <p>This prevents output code from depending on mutable player objects after the event has been
 * published.
 *
 * @param playerName the player name
 * @param pathPositions the ordered path positions
 * @param homePosition the player's home position
 * @param endPosition the player's end position
 */
public record PlayerPathSnapshot(
    String playerName, List<Integer> pathPositions, int homePosition, int endPosition) {
  public PlayerPathSnapshot {
    if (playerName == null || playerName.isBlank()) {
      throw new IllegalArgumentException("Player name must not be blank.");
    }

    if (pathPositions == null || pathPositions.isEmpty()) {
      throw new IllegalArgumentException("Path positions must not be empty.");
    }

    pathPositions = List.copyOf(pathPositions);
  }
}
