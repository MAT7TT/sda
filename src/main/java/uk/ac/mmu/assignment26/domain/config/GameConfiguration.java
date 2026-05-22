package uk.ac.mmu.assignment26.domain.config;

import java.util.List;

/**
 * Value object describing one game configuration
 *
 * <p>The configuration is used by the use case and factory layer to build a complete game.
 * It records the board size, player count, dice variation, rule variations and configured wormholes.</p>
 *
 * @param rows rows the number of board rows
 * @param columns columns the number of board columns
 * @param numberOfPlayers the number of players, limited to two or four
 * @param diceType the dice variation
 * @param endRuleType the end-position rule variation
 * @param hitRuleType the hit rule variation
 * @param teleportRuleType the teleport rule variation
 * @param wormholes the configured wormholes
 * @throws IllegalArgumentException if any configured value breaks the game setup contract
 */
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
