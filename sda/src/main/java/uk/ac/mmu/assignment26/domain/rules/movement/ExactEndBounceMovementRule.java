package uk.ac.mmu.assignment26.domain.rules.movement;

import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;
import uk.ac.mmu.assignment26.domain.rules.result.MoveResult;

public class ExactEndBounceMovementRule implements MovementRule {

  @Override
  public EndRuleType getType() {
    return EndRuleType.EXACT_END_BOUNCE;
  }

  @Override
  public MoveResult move(Player player, int roll) {
    validateMove(player, roll);
    int from = player.getCurrentPosition();

    int currentIndex = player.getPathIndex();
    int endIndex = player.getPathLength() - 1;
    int targetIndex = currentIndex + roll;

    boolean overshotEnd = targetIndex > endIndex;

    if (targetIndex <= endIndex) {
      player.setPathIndex(targetIndex);
    } else {
      player.setPathIndex(calculateBouncedIndex(targetIndex, endIndex));
    }

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

  // Accomodates smaller boards or bigger dice rolls by allowing multiple bounces.
  private int calculateBouncedIndex(int targetIndex, int endIndex) {
    int pathCycleLength = endIndex * 2;
    int positionInCycle = targetIndex % pathCycleLength;

    if (positionInCycle <= endIndex) {
      return positionInCycle;
    }

    return pathCycleLength - positionInCycle;
  }
}
