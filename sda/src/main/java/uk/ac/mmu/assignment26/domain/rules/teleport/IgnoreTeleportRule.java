package uk.ac.mmu.assignment26.domain.rules.teleport;

import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.TeleportRuleType;
import uk.ac.mmu.assignment26.domain.rules.result.TeleportResult;

public class IgnoreTeleportRule implements TeleportRule {

  @Override
  public TeleportRuleType getType() {
    return TeleportRuleType.IGNORE_WORMHOLES;
  }

  @Override
  public TeleportResult apply(Board board, Player player) {
    if (player == null) {
      throw new IllegalArgumentException("Player must not be null.");
    }

    return TeleportResult.notTeleported(player.getCurrentPosition());
  }
}
