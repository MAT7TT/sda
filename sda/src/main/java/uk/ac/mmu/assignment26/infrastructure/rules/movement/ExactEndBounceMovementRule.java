package uk.ac.mmu.assignment26.infrastructure.rules.movement;


import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;
import uk.ac.mmu.assignment26.domain.rules.MovementRule;

@Component
public class ExactEndBounceMovementRule implements MovementRule {

    @Override
    public EndRuleType getType() {
        return EndRuleType.EXACT_END_BOUNCE;
    }

    @Override
    public void move(Player player, int roll) {
        int currentIndex = player.getPathIndex();
        int endIndex = player.getPathLength() - 1;
        int targetIndex = currentIndex + roll;

        if (targetIndex <= endIndex) {
            player.setPathIndex(targetIndex);
        } else {
            int overshoot = targetIndex - endIndex;
            int bouncedIndex = endIndex - overshoot;
            player.setPathIndex(bouncedIndex);
        }
    }
}
