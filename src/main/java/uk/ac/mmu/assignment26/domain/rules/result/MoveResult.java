package uk.ac.mmu.assignment26.domain.rules.result;

/**
 * Value object describing the movement part of a turn.
 *
 * @param from the board position before movement
 * @param to the board position after movement
 * @param overshotEnd true if the roll went beyond the end position
 */
public record MoveResult(int from, int to, boolean overshotEnd) {}
