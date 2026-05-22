package uk.ac.mmu.assignment26.domain.rules.hit;

import java.util.List;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.HitRuleType;
import uk.ac.mmu.assignment26.domain.rules.result.HitResult;

/**
 * Hit rule for the basic game behaviour.
 *
 * <p>Hits are detected for output, but the current player is not moved back.
 * Multiple players may occupy the same board position.</p>
 */
public class IgnoreHitRule implements HitRule {

  @Override
  public HitRuleType getType() {
    return HitRuleType.IGNORE_HITS;
  }

  /**
   * Checks whether the current player has hit another player without changing
   * the current player's position.
   *
   * @param currentPlayer the player whose turn is being resolved
   * @param startTurnPathIndex the player's path index before the turn started
   * @param players all players in the game
   * @return the hit result
   * @throws IllegalArgumentException if the current player or player list is invalid
   */
  @Override
  public HitResult apply(Player currentPlayer, int startTurnPathIndex, List<Player> players) {
    validateApply(currentPlayer, players);

    int currentPosition = currentPlayer.getCurrentPosition();

    for (Player otherPlayer : players) {
      if (otherPlayer == currentPlayer) {
        continue;
      }

      if (otherPlayer.getCurrentPosition() == currentPosition) {
        return HitResult.hitIgnored(otherPlayer.getName(), currentPosition);
      }
    }

    return HitResult.noHit(currentPosition);
  }

  private void validateApply(Player currentPlayer, List<Player> players) {
    if (currentPlayer == null) {
      throw new IllegalArgumentException("Current player must not be null.");
    }

    if (players == null || players.isEmpty()) {
      throw new IllegalArgumentException("Players must not be empty.");
    }

    for (Player player : players) {
      if (player == null) {
        throw new IllegalArgumentException("Players must not contain null.");
      }
    }
  }
}
