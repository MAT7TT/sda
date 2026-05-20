package uk.ac.mmu.assignment26.infrastructure.persistence.memory;

import org.junit.jupiter.api.Test;
import uk.ac.mmu.assignment26.domain.config.DiceType;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;
import uk.ac.mmu.assignment26.domain.config.GameConfiguration;
import uk.ac.mmu.assignment26.domain.config.HitRuleType;
import uk.ac.mmu.assignment26.domain.config.TeleportRuleType;
import uk.ac.mmu.assignment26.usecase.SavedGame;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemorySavedGameRepositoryTest {

    @Test
    void savesGameAndReturnsGeneratedId() {
        InMemorySavedGameRepository repository = new InMemorySavedGameRepository();
        SavedGame savedGame = createSavedGame();

        int id = repository.save(savedGame);

        assertEquals(1, id);
        assertEquals(savedGame, repository.findById(id).orElseThrow());
    }

    @Test
    void generatesDifferentIdsForDifferentSavedGames() {
        InMemorySavedGameRepository repository = new InMemorySavedGameRepository();

        int firstId = repository.save(createSavedGame());
        int secondId = repository.save(createSavedGame());

        assertEquals(1, firstId);
        assertEquals(2, secondId);
    }

    @Test
    void returnsEmptyWhenSavedGameDoesNotExist() {
        InMemorySavedGameRepository repository = new InMemorySavedGameRepository();

        assertTrue(repository.findById(99).isEmpty());
    }

    @Test
    void rejectsNullSavedGame() {
        InMemorySavedGameRepository repository = new InMemorySavedGameRepository();

        assertThrows(
                IllegalArgumentException.class,
                () -> repository.save(null)
        );
    }

    @Test
    void rejectsInvalidSavedGameId() {
        InMemorySavedGameRepository repository = new InMemorySavedGameRepository();

        assertThrows(
                IllegalArgumentException.class,
                () -> repository.findById(0)
        );
    }

    private SavedGame createSavedGame() {
        return new SavedGame(
                new GameConfiguration(
                        5,
                        5,
                        2,
                        DiceType.DOUBLE,
                        EndRuleType.STANDARD,
                        HitRuleType.IGNORE_HITS,
                        TeleportRuleType.IGNORE_WORMHOLES,
                        List.of()
                ),
                List.of(12, 10, 12)
        );
    }
}