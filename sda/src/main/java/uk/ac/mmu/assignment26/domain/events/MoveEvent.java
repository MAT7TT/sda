package uk.ac.mmu.assignment26.domain.events;

public record MoveEvent(
        String playerName,
        int roll,
        int from,
        int to,
        int turnCount
) {}
