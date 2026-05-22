package uk.ac.mmu.assignment26.domain.rules.result;

/**
 * Value object describing the complete result of one turn.
 *
 * @param playerName the player who took the turn
 * @param roll the dice roll for the turn
 * @param turnCount the number of turns taken by the player
 * @param homePosition the player's home position
 * @param endPosition the player's end position
 * @param moveResult the movement result
 * @param teleportResult the teleport result
 * @param hitResult the hit result
 */
public record TurnResult(
    String playerName,
    int roll,
    int turnCount,
    int homePosition,
    int endPosition,
    MoveResult moveResult,
    TeleportResult teleportResult,
    HitResult hitResult) {}
