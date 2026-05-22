package uk.ac.mmu.assignment26.domain.rules.teleport;

import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.TeleportRuleType;
import uk.ac.mmu.assignment26.domain.rules.result.TeleportResult;

/**
 * Teleport rule for ignoring wormholes.
 *
 * <p>Wormholes may exist on the board, but this rule leaves the player on their current position.
 */
public class IgnoreTeleportRule implements TeleportRule {

  @Override
  public TeleportRuleType getType() {
    return TeleportRuleType.IGNORE_WORMHOLES;
  }

  /**
   * Returns a not-teleported result for the supplied player.
   *
   * @param board the board containing configured wormholes
   * @param player the player whose position is being checked
   * @return a result showing that no teleport occurred
   * @throws IllegalArgumentException if the player or board is null
   */
  @Override
  public TeleportResult apply(Board board, Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player must not be null.");
    }

    if (board == null) {
      throw new IllegalArgumentException("Board must not be null.");
    }

    return TeleportResult.notTeleported(player.getCurrentPosition());
  }
}
