package uk.ac.mmu.assignment26.infrastructure.rules.movement;


import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;
import uk.ac.mmu.assignment26.domain.rules.MovementRule;

@Component
public class StandardEndMovementRule implements MovementRule {

    @Override
    public EndRuleType getType() {
        return EndRuleType.STANDARD;
    }

    @Override
    public void move(Player player, int roll) {
        int currentIndex = player.getPathIndex();
        int endIndex = player.getPathLength() - 1;
        int targetIndex = currentIndex + roll;

        player.setPathIndex(Math.min(targetIndex, endIndex));
    }
}
