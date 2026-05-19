package uk.ac.mmu.assignment26.domain.events;

import uk.ac.mmu.assignment26.domain.Player;

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
