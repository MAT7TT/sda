package uk.ac.mmu.assignment26.domain.rules.teleport;

import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.TeleportRuleType;
import uk.ac.mmu.assignment26.domain.rules.result.TeleportResult;

/**
 * Strategy interface for teleport rules.
 *
 * <p>A teleport rule decides whether a player should move when they land on a wormhole
 */
public interface TeleportRule {

  /**
   * Returns the configuration type represented by this rule.
   *
   * @return the teleport rule type
   */
  TeleportRuleType getType();

  /**
   * Applies teleport behaviour to the supplied player.
   *
   * @param board the board containing configured wormholes
   * @param player the player whose position is being checked
   * @return the result of applying the teleport rule
   * @throws IllegalArgumentException if the board or player breaks the rule contract
   */
  TeleportResult apply(Board board, Player player);
}
