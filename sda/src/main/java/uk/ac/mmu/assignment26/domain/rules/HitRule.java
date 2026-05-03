package uk.ac.mmu.assignment26.domain.rules;

import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;
import uk.ac.mmu.assignment26.domain.config.HitRuleType;

import java.util.List;

public interface HitRule {
    HitRuleType getType();
    void apply(Player currentPlayer, int startTurnPathIndex, List<Player> players);
}
