package uk.ac.mmu.assignment26.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.Game;
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

class PlayGameUseCaseTest {

    @Test
    void rejectsNullGameFactory() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PlayGameUseCase(null, new TestSavedGameRepository())
        );
    }

    @Test
    void rejectsNullSavedGameRepository() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PlayGameUseCase(new TestGameFactory(), null)
        );
    }

    @Test
    void rejectsNullConfiguration() {
        PlayGameUseCase playGameUseCase = new PlayGameUseCase(
                new TestGameFactory(),
                new TestSavedGameRepository()
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> playGameUseCase.play(null)
        );
    }

    @Test
    void rejectsNullFixedDiceRolls() {
        PlayGameUseCase playGameUseCase = new PlayGameUseCase(
                new TestGameFactory(),
                new TestSavedGameRepository()
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> playGameUseCase.play(createConfiguration(), null)
        );
    }

    @Test
    void rejectsEmptyFixedDiceRolls() {
        PlayGameUseCase playGameUseCase = new PlayGameUseCase(
                new TestGameFactory(),
                new TestSavedGameRepository()
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> playGameUseCase.play(createConfiguration(), List.of())
        );
    }

    @Test
    void savesPlayedGameWithConfigurationAndDiceRolls() {
        TestSavedGameRepository repository = new TestSavedGameRepository();

        PlayGameUseCase playGameUseCase = new PlayGameUseCase(
                new TestGameFactory(),
                repository
        );

        PlayGameResult result = playGameUseCase.play(createConfiguration(), List.of(8));

        assertEquals(1, result.gameId());
        assertEquals(createConfiguration(), result.savedGame().configuration());
        assertEquals(List.of(8), result.savedGame().diceRolls());
        assertEquals(result.savedGame(), repository.findById(1).orElseThrow());
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
            return createGame(configuration, List.of(8));
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
        private SavedGame savedGame;

        @Override
        public int save(SavedGame savedGame) {
            this.savedGame = savedGame;
            return 1;
        }

        @Override
        public Optional<SavedGame> findById(int id) {
            return Optional.ofNullable(savedGame);
        }
    }
}