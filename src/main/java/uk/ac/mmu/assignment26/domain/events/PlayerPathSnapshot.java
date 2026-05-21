package uk.ac.mmu.assignment26.domain.events;

import java.util.List;

public record PlayerPathSnapshot(
    String playerName, List<Integer> pathPositions, int homePosition, int endPosition) {
  public PlayerPathSnapshot {
    if (playerName == null || playerName.isBlank()) {
      throw new IllegalArgumentException("Player name must not be blank.");
    }

    if (pathPositions == null || pathPositions.isEmpty()) {
      throw new IllegalArgumentException("Path positions must not be empty.");
    }

    // Makes copy of this.pathPositions as snapshot may still be pointing at original list and may
    // be modifiable.
    pathPositions = List.copyOf(pathPositions);
  }
}
