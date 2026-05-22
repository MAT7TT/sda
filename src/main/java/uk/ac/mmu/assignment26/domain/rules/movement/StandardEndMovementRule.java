package uk.ac.mmu.assignment26.domain.rules.movement;

import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;
import uk.ac.mmu.assignment26.domain.rules.result.MoveResult;

/**
 * Movement rule for the basic end-position behaviour.
 *
 * <p>A player wins by landing on or overshooting the end position. If the roll would move the
 * player beyond the end, the player is placed on the end position.
 */
public class StandardEndMovementRule implements MovementRule {

  @Override
  public EndRuleType getType() {
    return EndRuleType.STANDARD;
  }

  /**
   * Moves the player forward and caps movement at the end position.
   *
   * @param player the player to move
   * @param roll the dice roll for the turn
   * @return the movement result, including whether the end was overshot
   * @throws IllegalArgumentException if the player is null or the roll is not positive
   */
  @Override
  public MoveResult move(Player player, int roll) {
    validateMove(player, roll);

    int from = player.getCurrentPosition();

    int currentIndex = player.getPathIndex();
    int endIndex = player.getPathLength() - 1;
    int targetIndex = currentIndex + roll;
    boolean overshotEnd = targetIndex > endIndex;

    player.setPathIndex(Math.min(targetIndex, endIndex));

    int to = player.getCurrentPosition();

    return new MoveResult(from, to, overshotEnd);
  }

  private void validateMove(Player player, int roll) {
    if (player == null) {
      throw new IllegalArgumentException("Player must not be null.");
    }

    if (roll <= 0) {
      throw new IllegalArgumentException("Roll must be greater than zero.");
    }
  }
}
