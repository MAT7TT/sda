package uk.ac.mmu.assignment26.infrastructure.scenarios;

import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.config.DiceType;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;
import uk.ac.mmu.assignment26.domain.config.GameConfiguration;
import uk.ac.mmu.assignment26.domain.config.HitRuleType;
import uk.ac.mmu.assignment26.domain.config.TeleportRuleType;
import uk.ac.mmu.assignment26.domain.config.Wormhole;

import java.util.List;

@Component
public class GameScenarioProvider {

    public List<GameScenario> getScenarios() {
        return List.of(
                basicRedWinsInTwoTurns()
        );
    }

    private GameScenario basicRedWinsInTwoTurns() {
        GameConfiguration configuration = new GameConfiguration(
                5,
                5,
                2,
                DiceType.DOUBLE,
                EndRuleType.STANDARD,
                HitRuleType.IGNORE_HITS,
                TeleportRuleType.IGNORE_WORMHOLES,
                List.of()
        );

        return new GameScenario(
                "Basic game: Red wins in 2 turns",
                configuration,
                List.of(12, 10, 12)
        );
    }

    private GameScenario teleportThroughWormholes() {
        GameConfiguration configuration = new GameConfiguration(
                5,
                5,
                2,
                DiceType.DOUBLE,
                EndRuleType.STANDARD,
                HitRuleType.IGNORE_HITS,
                TeleportRuleType.USE_WORMHOLES,
                List.of(
                        new Wormhole(4, 9),
                        new Wormhole(23, 19)
                )
        );

        return new GameScenario(
                "Teleport variation game: 2 Players, Teleport through Wormhole",
                configuration,
                List.of(3, 2, 12, 10, 2, 3)
        );
    }
}