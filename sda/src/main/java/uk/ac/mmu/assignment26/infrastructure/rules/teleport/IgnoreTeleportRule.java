package uk.ac.mmu.assignment26.infrastructure.rules.teleport;


import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.TeleportRuleType;
import uk.ac.mmu.assignment26.domain.rules.TeleportRule;

@Component
public class IgnoreTeleportRule implements TeleportRule {
    @Override
    public TeleportRuleType getType() {
        return TeleportRuleType.IGNORE_WORMHOLES;
    }

    @Override
    public void apply(Board board, Player player) {
        // Do nothing
    }
}
