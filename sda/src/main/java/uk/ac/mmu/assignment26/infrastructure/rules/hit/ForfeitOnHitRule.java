package uk.ac.mmu.assignment26.infrastructure.rules.hit;


import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.HitRuleType;
import uk.ac.mmu.assignment26.domain.rules.HitRule;

import java.util.List;

@Component
public class ForfeitOnHitRule implements HitRule {

    @Override
    public HitRuleType getType() {
        return HitRuleType.FORFEIT_ON_HIT;
    }

    @Override
    public void apply(Player currentPlayer, int startTurnPathIndex, List<Player> players) {
        int currentPosition = currentPlayer.getCurrentPosition();

        for (Player otherPlayer : players) {
            if (otherPlayer == currentPlayer) {
                continue;
            }

            if (otherPlayer.getCurrentPosition() == currentPosition) {
                System.out.println(currentPlayer.getName()
                        + " hit "
                        + otherPlayer.getName()
                        + " at position Position "
                        + currentPosition);

                currentPlayer.setPathIndex(startTurnPathIndex);

                System.out.println(currentPlayer.getName()
                        + " moves back to " + currentPlayer.describePosition());
                return;
            }
        }
    }
}