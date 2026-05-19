package uk.ac.mmu.assignment26.domain.events;

import java.util.List;

public record GameStartedEvent(
        int rows,
        int columns,
        List<PlayerPathSnapshot> players
) {
    public GameStartedEvent {
        players = List.copyOf(players);
    }
}

