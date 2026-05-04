package uk.ac.mmu.assignment26.domain.rules.result;

public record MoveResult(
        int from,
        int to,
        boolean overshotEnd
) {}