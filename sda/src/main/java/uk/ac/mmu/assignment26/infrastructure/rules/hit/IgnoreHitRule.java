package uk.ac.mmu.assignment26.infrastructure.rules.hit;

import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.HitRuleType;
import uk.ac.mmu.assignment26.domain.rules.HitRule;

import java.util.List;

@Component
public class IgnoreHitRule implements HitRule {
    @Override
    public HitRuleType getType() {
        return HitRuleType.IGNORE_HITS;
    }

    @Override
    public void apply(Player currentPlayer, int startTurnPathIndex, List<Player> players) {
        // Do nothing
    }
}
