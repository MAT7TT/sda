package uk.ac.mmu.assignment26.domain.rules.result;

public record TurnResult(
    String playerName,
    int roll,
    int turnCount,
    int homePosition,
    int endPosition,
    MoveResult moveResult,
    TeleportResult teleportResult,
    HitResult hitResult) {}
