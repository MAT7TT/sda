package uk.ac.mmu.assignment26.domain.rules.movement;

import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;
import uk.ac.mmu.assignment26.domain.rules.result.MoveResult;

public interface MovementRule {
  EndRuleType getType();

  MoveResult move(Player player, int roll);
}
