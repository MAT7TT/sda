package uk.ac.mmu.assignment26.domain.rules.hit;

import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.HitRuleType;
import uk.ac.mmu.assignment26.domain.rules.result.HitResult;

import java.util.List;

public class ForfeitOnHitRule implements HitRule {

    @Override
    public HitRuleType getType() {
        return HitRuleType.FORFEIT_ON_HIT;
    }

    @Override
    public HitResult apply(Player currentPlayer, int startTurnPathIndex, List<Player> players) {
        int from = currentPlayer.getCurrentPosition();

        for (Player otherPlayer : players) {
            if (otherPlayer == currentPlayer) {
                continue;
            }

            if (otherPlayer.getCurrentPosition() == from) {
                currentPlayer.setPathIndex(startTurnPathIndex);
                int to = currentPlayer.getCurrentPosition();

                return HitResult.hitAndMovedBack(
                        otherPlayer.getName(),
                        from,
                        to
                );
            }
        }

        return HitResult.noHit(from);
    }
}