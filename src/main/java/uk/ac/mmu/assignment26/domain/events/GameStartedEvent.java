package uk.ac.mmu.assignment26.domain.events;

import java.util.List;

public record GameStartedEvent(int rows, int columns, List<PlayerPathSnapshot> players) {
  public GameStartedEvent {
    if (players == null || players.isEmpty()) {
      throw new IllegalArgumentException("Players must not be empty.");
    }

    players = List.copyOf(players);
  }
}
