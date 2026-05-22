package uk.ac.mmu.assignment26.domain.rules.teleport;

import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.TeleportRuleType;
import uk.ac.mmu.assignment26.domain.rules.result.TeleportResult;

/**
 * Teleport rule for active wormholes.
 *
 * <p>If the player lands on a wormhole endpoint, the player is moved to the other endpoint of the
 * same wormhole.
 */
public class WormholeTeleportRule implements TeleportRule {

  @Override
  public TeleportRuleType getType() {
    return TeleportRuleType.USE_WORMHOLES;
  }

  /**
   * Apples the wormhole teleporting to the supplied player.
   *
   * @param board the board containing configured wormholes
   * @param player the player whose position is being checked
   * @return the teleport result
   * @throws IllegalArgumentException if the board or player is null
   */
  @Override
  public TeleportResult apply(Board board, Player player) {
    validateApply(board, player);

    int from = player.getCurrentPosition();

    if (!board.hasWormholeAt(from)) {
      return TeleportResult.notTeleported(from);
    }

    int to = board.getWormholeExit(from);
    player.setPathIndex(player.findPathIndexOfPosition(to));

    return TeleportResult.teleported(from, to);
  }

  private void validateApply(Board board, Player player) {
    if (board == null) {
      throw new IllegalArgumentException("Board must not be null.");
    }

    if (player == null) {
      throw new IllegalArgumentException("Player must not be null.");
    }
  }
}
