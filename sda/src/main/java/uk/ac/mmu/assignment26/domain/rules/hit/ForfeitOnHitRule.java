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
        validateApply(currentPlayer, startTurnPathIndex, players);

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

    private void validateApply(Player currentPlayer, int startTurnPathIndex, List<Player> players) {
        if (currentPlayer == null) {
            throw new IllegalArgumentException("Current player must not be null.");
        }

        if (players == null || players.isEmpty()) {
            throw new IllegalArgumentException("Players must not be empty.");
        }

        if (startTurnPathIndex < 0 || startTurnPathIndex >= currentPlayer.getPathLength()) {
            throw new IllegalArgumentException("Start turn path index is outside the player's path.");
        }

        for (Player player : players) {
            if (player == null) {
                throw new IllegalArgumentException("Players must not contain null.");
            }
        }
    }
}