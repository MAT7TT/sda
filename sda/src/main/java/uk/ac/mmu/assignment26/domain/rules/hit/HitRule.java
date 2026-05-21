package uk.ac.mmu.assignment26.domain.rules.hit;

import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.HitRuleType;
import uk.ac.mmu.assignment26.domain.rules.result.HitResult;

import java.util.List;

public interface HitRule {
  HitRuleType getType();

  HitResult apply(Player currentPlayer, int startTurnPathIndex, List<Player> players);
}
