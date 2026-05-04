package uk.ac.mmu.assignment26.domain.rules.movement;

import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;
import uk.ac.mmu.assignment26.domain.rules.result.MoveResult;

public class ExactEndBounceMovementRule implements MovementRule {

    @Override
    public EndRuleType getType() {
        return EndRuleType.EXACT_END_BOUNCE;
    }

    @Override
    public MoveResult move(Player player, int roll) {
        int from = player.getCurrentPosition();

        int currentIndex = player.getPathIndex();
        int endIndex = player.getPathLength() - 1;
        int targetIndex = currentIndex + roll;

        boolean overshotEnd = targetIndex > endIndex;

        if (targetIndex <= endIndex) {
            player.setPathIndex(targetIndex);
        } else {
            int overshoot = targetIndex - endIndex;
            int bouncedIndex = endIndex - overshoot;
            player.setPathIndex(bouncedIndex);
        }

        int to = player.getCurrentPosition();

        return new MoveResult(from, to, overshotEnd);
    }
}