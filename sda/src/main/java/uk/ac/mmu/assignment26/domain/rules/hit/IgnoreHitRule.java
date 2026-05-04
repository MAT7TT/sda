package uk.ac.mmu.assignment26.domain.rules.hit;

import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.HitRuleType;
import uk.ac.mmu.assignment26.domain.rules.result.HitResult;

import java.util.List;

public class IgnoreHitRule implements HitRule {

    @Override
    public HitRuleType getType() {
        return HitRuleType.IGNORE_HITS;
    }

    @Override
    public HitResult apply(Player currentPlayer, int startTurnPathIndex, List<Player> players) {
        int currentPosition = currentPlayer.getCurrentPosition();

        for (Player otherPlayer : players) {
            if (otherPlayer == currentPlayer) {
                continue;
            }

            if (otherPlayer.getCurrentPosition() == currentPosition) {
                return HitResult.hitIgnored(otherPlayer.getName(), currentPosition);
            }
        }

        return HitResult.noHit(currentPosition);
    }
}