package uk.ac.mmu.assignment26.domain.rules.teleport;

import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.TeleportRuleType;
import uk.ac.mmu.assignment26.domain.rules.result.TeleportResult;

public class WormholeTeleportRule implements TeleportRule {

  @Override
  public TeleportRuleType getType() {
    return TeleportRuleType.USE_WORMHOLES;
  }

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
