package uk.ac.mmu.assignment26.domain.events;

import java.util.List;

/**
 * Event published when a game starts.
 *
 * @param rows the number of board rows
 * @param columns the number of board columns
 * @param players snapshots of the players and their paths.
 */
public record GameStartedEvent(int rows, int columns, List<PlayerPathSnapshot> players) {
  public GameStartedEvent {
    if (players == null || players.isEmpty()) {
      throw new IllegalArgumentException("Players must not be empty.");
    }

    players = List.copyOf(players);
  }
}
