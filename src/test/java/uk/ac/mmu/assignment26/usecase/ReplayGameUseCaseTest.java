package uk.ac.mmu.assignment26.usecase;

import org.junit.jupiter.api.Test;
import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.Game;
import uk.ac.mmu.assignment26.domain.GameResult;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.DiceType;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;
import uk.ac.mmu.assignment26.domain.config.GameConfiguration;
import uk.ac.mmu.assignment26.domain.config.HitRuleType;
import uk.ac.mmu.assignment26.domain.config.TeleportRuleType;
import uk.ac.mmu.assignment26.domain.dice.FixedDiceShaker;
import uk.ac.mmu.assignment26.domain.path.LeftStartSnakePathStrategy;
import uk.ac.mmu.assignment26.domain.path.RightStartSnakePathStrategy;
import uk.ac.mmu.assignment26.domain.rules.hit.IgnoreHitRule;
import uk.ac.mmu.assignment26.domain.rules.movement.StandardEndMovementRule;
import uk.ac.mmu.assignment26.domain.rules.teleport.IgnoreTeleportRule;
import uk.ac.mmu.assignment26.usecase.ports.GameFactory;
import uk.ac.mmu.assignment26.usecase.ports.SavedGameRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReplayGameUseCaseTest {

    @Test
    void rejectsNullGameFactory() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ReplayGameUseCase(null, new TestSavedGameRepository(null))
        );
    }

    @Test
    void rejectsNullSavedGameRepository() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ReplayGameUseCase(new TestGameFactory(), null)
        );
    }

    @Test
    void rejectsInvalidGameId() {
        ReplayGameUseCase replayGameUseCase = new ReplayGameUseCase(
                new TestGameFactory(),
                new TestSavedGameRepository(null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> replayGameUseCase.findSavedGame(0)
        );
    }

    @Test
    void replaysSavedGameAndReturnsResult() {
        SavedGame savedGame = new SavedGame(
                createConfiguration(),
                List.of(8)
        );

        ReplayGameUseCase replayGameUseCase = new ReplayGameUseCase(
                new TestGameFactory(),
                new TestSavedGameRepository(savedGame)
        );

        GameResult result = replayGameUseCase.replay(1);

        assertEquals("Red", result.winnerName());
        assertEquals(1, result.winnerTurns());
        assertEquals(1, result.totalTurns());
        assertEquals(List.of(8), result.diceRolls());
    }

    @Test
    void rejectsReplayWhenSavedGameDoesNotExist() {
        ReplayGameUseCase replayGameUseCase = new ReplayGameUseCase(
                new TestGameFactory(),
                new TestSavedGameRepository(null)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> replayGameUseCase.replay(1)
        );
    }

    private GameConfiguration createConfiguration() {
        return new GameConfiguration(
                3,
                3,
                2,
                DiceType.DOUBLE,
                EndRuleType.STANDARD,
                HitRuleType.IGNORE_HITS,
                TeleportRuleType.IGNORE_WORMHOLES,
                List.of()
        );
    }

    private static class TestGameFactory implements GameFactory {
        @Override
        public Game createGame(GameConfiguration configuration) {
            throw new UnsupportedOperationException("Replay should use fixed dice rolls.");
        }

        @Override
        public Game createGame(GameConfiguration configuration, List<Integer> fixedDiceRolls) {
            Board board = new Board(configuration.rows(), configuration.columns());

            return new Game(
                    board,
                    List.of(
                            new Player("Red", board, new LeftStartSnakePathStrategy()),
                            new Player("Blue", board, new RightStartSnakePathStrategy())
                    ),
                    new FixedDiceShaker(fixedDiceRolls, configuration.diceType()),
                    new StandardEndMovementRule(),
                    new IgnoreTeleportRule(),
                    new IgnoreHitRule(),
                    event -> {
                    }
            );
        }
    }

    private static class TestSavedGameRepository implements SavedGameRepository {
        private final SavedGame savedGame;

        private TestSavedGameRepository(SavedGame savedGame) {
            this.savedGame = savedGame;
        }

        @Override
        public int save(SavedGame savedGame) {
            throw new UnsupportedOperationException("Replay should not save games.");
        }

        @Override
        public Optional<SavedGame> findById(int id) {
            return Optional.ofNullable(savedGame);
        }
    }
}