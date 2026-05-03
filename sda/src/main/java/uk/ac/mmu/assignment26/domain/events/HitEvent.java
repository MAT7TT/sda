package uk.ac.mmu.assignment26.domain.events;

public record HitEvent(
        String currentPlayerName,
        String hitPlayerName,
        int position,
        int returnPosition
) {
}