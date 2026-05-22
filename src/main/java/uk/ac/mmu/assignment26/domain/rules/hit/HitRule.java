package uk.ac.mmu.assignment26.domain.rules.hit;

import java.util.List;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.HitRuleType;
import uk.ac.mmu.assignment26.domain.rules.result.HitResult;

/**
 * Strategy interface for hit rules.
 *
 * <p>A hit rule decides what happens after movement and teleporting when a player occupies the same
 * board position as another player.
 */
public interface HitRule {
  /**
   * Returns the configuration type represented by this rule.
   *
   * @return the hit rule type
   */
  HitRuleType getType();

  /**
   * @param currentPlayer the player whose turn is being resolved
   * @param startTurnPathIndex the player's path index before the turn started
   * @param players all players in the game
   * @return the result of applying the hit rule
   * @throws IllegalArgumentException if the current player, player list or start-turn path index
   *     breaks the rule contract
   */
  HitResult apply(Player currentPlayer, int startTurnPathIndex, List<Player> players);
}
