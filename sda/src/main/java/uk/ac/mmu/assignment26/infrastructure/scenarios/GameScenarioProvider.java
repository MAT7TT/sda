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
                basicRedWinsInTwoTurns(),
                basicBlueWinsInTwoTurns(),
                basicHitsAreIgnored(),
                basicBlueWinsByOvershootingEnd(),
                gameOverIgnoresExtraDiceRolls(),
                singleDieVariation(),
                randomDoubleDiceVariation(),
                randomSingleDieVariation(),
                largeBoardFourPlayerVariation(),
                exactEndBounceVariation(),
                forfeitOnHitVariation(),
                wormholesIgnoredVariation(),
                teleportThroughWormholesVariation(),
                smallBoardMultipleVariations(),
                largeBoardMultipleVariations()
        );
    }

    private GameScenario basicRedWinsInTwoTurns() {
        GameConfiguration configuration = smallBasicConfiguration(DiceType.DOUBLE);

        return new GameScenario(
                "Basic game: Red wins in 2 turns",
                configuration,
                List.of(12, 10, 12)
        );
    }

    private GameScenario basicBlueWinsInTwoTurns() {
        GameConfiguration configuration = smallBasicConfiguration(DiceType.DOUBLE);

        return new GameScenario(
                "Basic game: Blue wins in 2 turns",
                configuration,
                List.of(5, 12, 2, 12)
        );
    }

    private GameScenario basicHitsAreIgnored() {
        GameConfiguration configuration = smallBasicConfiguration(DiceType.DOUBLE);

        return new GameScenario(
                "Basic game: Red wins, HITs are ignored",
                configuration,
                List.of(12, 12, 6, 6, 6)
        );
    }

    private GameScenario basicBlueWinsByOvershootingEnd() {
        GameConfiguration configuration = smallBasicConfiguration(DiceType.DOUBLE);

        return new GameScenario(
                "Basic game: Blue wins even though it overshot its END position",
                configuration,
                List.of(12, 12, 7, 7, 3, 12)
        );
    }

    private GameScenario gameOverIgnoresExtraDiceRolls() {
        GameConfiguration configuration = smallBasicConfiguration(DiceType.DOUBLE);

        return new GameScenario(
                "Game State variation: Red wins and extra dice rolls are ignored in Game Over state",
                configuration,
                List.of(12, 10, 12, 4, 5)
        );
    }

    private GameScenario singleDieVariation() {
        GameConfiguration configuration = smallBasicConfiguration(DiceType.SINGLE);

        return new GameScenario(
                "Dice roll variation game: Blue wins using a single die",
                configuration,
                List.of(6, 6, 6, 6, 4, 6, 3, 6)
        );
    }

    private GameScenario randomDoubleDiceVariation() {
        GameConfiguration configuration = smallBasicConfiguration(DiceType.DOUBLE);

        return new GameScenario(
                "Dice roll variation game: Random game played with 2 dice",
                configuration
        );
    }

    private GameScenario randomSingleDieVariation() {
        GameConfiguration configuration = smallBasicConfiguration(DiceType.SINGLE);

        return new GameScenario(
                "Dice roll variation game: Random game played with 1 die",
                configuration
        );
    }

    private GameScenario largeBoardFourPlayerVariation() {
        GameConfiguration configuration = new GameConfiguration(
                6,
                6,
                4,
                DiceType.DOUBLE,
                EndRuleType.STANDARD,
                HitRuleType.IGNORE_HITS,
                TeleportRuleType.IGNORE_WORMHOLES,
                List.of()
        );

        return new GameScenario(
                "Board size variation game: Large board, 4 players, basic rules",
                configuration,
                List.of(7, 3, 8, 5, 7, 6, 8, 7, 6, 8, 2, 4, 4, 8, 5, 7, 8, 3, 9, 9, 7)
        );
    }

    private GameScenario exactEndBounceVariation() {
        GameConfiguration configuration = new GameConfiguration(
                5,
                5,
                2,
                DiceType.DOUBLE,
                EndRuleType.EXACT_END_BOUNCE,
                HitRuleType.IGNORE_HITS,
                TeleportRuleType.IGNORE_WORMHOLES,
                List.of()
        );

        return new GameScenario(
                "Exact End variation game: Blue wins after players bounce back from overshooting END",
                configuration,
                List.of(9, 10, 10, 9, 12, 7, 2, 2)
        );
    }

    private GameScenario forfeitOnHitVariation() {
        GameConfiguration configuration = new GameConfiguration(
                5,
                5,
                2,
                DiceType.DOUBLE,
                EndRuleType.STANDARD,
                HitRuleType.FORFEIT_ON_HIT,
                TeleportRuleType.IGNORE_WORMHOLES,
                List.of()
        );

        return new GameScenario(
                "Hit variation game: Red hits Blue and Red's turn is forfeit",
                configuration,
                List.of(12, 6, 6, 12, 5, 6)
        );
    }

    private GameScenario wormholesIgnoredVariation() {
        GameConfiguration configuration = new GameConfiguration(
                5,
                5,
                2,
                DiceType.DOUBLE,
                EndRuleType.STANDARD,
                HitRuleType.IGNORE_HITS,
                TeleportRuleType.IGNORE_WORMHOLES,
                smallBoardWormholes()
        );

        return new GameScenario(
                "Teleport variation game: Wormholes exist but are ignored",
                configuration,
                List.of(3, 2, 12, 4, 3, 10, 4, 5, 2)
        );
    }

    private GameScenario teleportThroughWormholesVariation() {
        GameConfiguration configuration = new GameConfiguration(
                5,
                5,
                2,
                DiceType.DOUBLE,
                EndRuleType.STANDARD,
                HitRuleType.IGNORE_HITS,
                TeleportRuleType.USE_WORMHOLES,
                smallBoardWormholes()
        );

        return new GameScenario(
                "Teleport variation game: 2 players teleport through wormholes",
                configuration,
                List.of(3, 2, 12, 10, 2, 3)
        );
    }

    private GameScenario smallBoardMultipleVariations() {
        GameConfiguration configuration = new GameConfiguration(
                5,
                5,
                2,
                DiceType.DOUBLE,
                EndRuleType.EXACT_END_BOUNCE,
                HitRuleType.FORFEIT_ON_HIT,
                TeleportRuleType.USE_WORMHOLES,
                smallBoardWormholes()
        );

        return new GameScenario(
                "Multiple variations game: Small board, 2 players, exact end, forfeit on hit and teleport",
                configuration,
                List.of(7, 10, 7, 9, 7, 10, 6, 9, 3, 7, 7, 6, 9, 9, 7)
        );
    }

    private GameScenario largeBoardMultipleVariations() {
        GameConfiguration configuration = new GameConfiguration(
                6,
                6,
                4,
                DiceType.DOUBLE,
                EndRuleType.EXACT_END_BOUNCE,
                HitRuleType.FORFEIT_ON_HIT,
                TeleportRuleType.USE_WORMHOLES,
                largeBoardWormholes()
        );

        return new GameScenario(
                "Multiple variations game: Large board, 4 players, exact end, forfeit on hit and teleport",
                configuration,
                List.of(5, 5, 5, 5, 7, 7, 6, 10, 10, 11, 7, 5, 10, 7, 7, 7, 7, 6, 11, 10, 6, 6, 5, 11, 5, 9)
        );
    }

    private GameConfiguration smallBasicConfiguration(DiceType diceType) {
        return new GameConfiguration(
                5,
                5,
                2,
                diceType,
                EndRuleType.STANDARD,
                HitRuleType.IGNORE_HITS,
                TeleportRuleType.IGNORE_WORMHOLES,
                List.of()
        );
    }

    private List<Wormhole> smallBoardWormholes() {
        return List.of(
                new Wormhole(4, 9),
                new Wormhole(23, 19)
        );
    }

    private List<Wormhole> largeBoardWormholes() {
        return List.of(
                new Wormhole(4, 9),
                new Wormhole(35, 21)
        );
    }
}