package uk.ac.mmu.assignment26.domain.rules.movement;

import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;
import uk.ac.mmu.assignment26.domain.rules.result.MoveResult;

/**
 * Strategy interface for movement rules.
 *
 * <p>A movement rule is responsible for moving a player along their path after a dice roll.
 * Concrete implementations decide how the end position is handled </p>
 */
public interface MovementRule {
  /**
   * Returns the configured type represented by this rule.
   *
   * @return the end rule type
   */
  EndRuleType getType();

  /**
   * Moves the supplied player using the supplied dice roll.
   *
   * @param player the player to move
   * @param roll the dice roll for the turn
   * @return the result of the movement
   * @throws IllegalArgumentException if the player is null or the roll
   * does not satisfy the movement rule preconditions
   */
  MoveResult move(Player player, int roll);
}
