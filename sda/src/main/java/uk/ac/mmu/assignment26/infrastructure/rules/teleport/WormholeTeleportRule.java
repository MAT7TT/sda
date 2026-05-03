package uk.ac.mmu.assignment26.infrastructure.rules.teleport;


import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.TeleportRuleType;
import uk.ac.mmu.assignment26.domain.rules.TeleportRule;

@Component
public class WormholeTeleportRule implements TeleportRule {

    @Override
    public TeleportRuleType getType() {
        return TeleportRuleType.USE_WORMHOLES;
    }

    @Override
    public void apply(Board board, Player player) {
        int currentPosition = player.getCurrentPosition();

        if (board.hasWormholeAt(currentPosition)) {
            int exitPosition = board.getWormholeExit(currentPosition);
            player.setPathIndex(player.findPathIndexOfPosition(exitPosition));

            System.out.println(player.getName() + " is teleported.");
            System.out.println(player.getName() +
                    " moves to " + player.describePosition());
        }
    }
}
