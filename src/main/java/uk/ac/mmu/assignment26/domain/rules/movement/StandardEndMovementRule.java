package uk.ac.mmu.assignment26.domain.rules.movement;

import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;
import uk.ac.mmu.assignment26.domain.rules.result.MoveResult;

public class StandardEndMovementRule implements MovementRule {

  @Override
  public EndRuleType getType() {
    return EndRuleType.STANDARD;
  }

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
