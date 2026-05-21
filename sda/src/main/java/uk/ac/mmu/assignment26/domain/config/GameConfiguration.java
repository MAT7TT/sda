package uk.ac.mmu.assignment26.domain.config;

import java.util.List;

public record GameConfiguration(
    int rows,
    int columns,
    int numberOfPlayers,
    DiceType diceType,
    EndRuleType endRuleType,
    HitRuleType hitRuleType,
    TeleportRuleType teleportRuleType,
    List<Wormhole> wormholes) {
  public GameConfiguration {
    if (rows <= 0 || columns <= 0) {
      throw new IllegalArgumentException("Board rows and columns must be positive.");
    }

    if (numberOfPlayers != 2 && numberOfPlayers != 4) {
      throw new IllegalArgumentException("Number of players must be 2 or 4.");
    }

    if (diceType == null) {
      throw new IllegalArgumentException("Dice type must not be null.");
    }

    if (endRuleType == null) {
      throw new IllegalArgumentException("End rule type must not be null.");
    }

    if (hitRuleType == null) {
      throw new IllegalArgumentException("Hit rule type must not be null.");
    }

    if (teleportRuleType == null) {
      throw new IllegalArgumentException("Teleport rule type must not be null.");
    }

    if (wormholes == null) {
      throw new IllegalArgumentException("Wormholes must not be null");
    }

    for (Wormhole wormhole : wormholes) {
      if (wormhole == null) {
        throw new IllegalArgumentException("Wormholes must not be null.");
      }
    }

    wormholes = List.copyOf(wormholes);
  }
}
