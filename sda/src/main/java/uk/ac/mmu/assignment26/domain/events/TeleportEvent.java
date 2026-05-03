package uk.ac.mmu.assignment26.domain.events;

public record TeleportEvent(
        String playerName,
        int fromPosition,
        int toPosition
) {
}