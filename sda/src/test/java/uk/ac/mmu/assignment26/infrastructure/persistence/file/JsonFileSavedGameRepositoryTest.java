package uk.ac.mmu.assignment26.infrastructure.persistence.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import uk.ac.mmu.assignment26.domain.config.DiceType;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;
import uk.ac.mmu.assignment26.domain.config.GameConfiguration;
import uk.ac.mmu.assignment26.domain.config.HitRuleType;
import uk.ac.mmu.assignment26.domain.config.TeleportRuleType;
import uk.ac.mmu.assignment26.domain.config.Wormhole;
import uk.ac.mmu.assignment26.usecase.SavedGame;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonFileSavedGameRepositoryTest {

    @TempDir
    Path tempDir;

    @Test
    void savesGameToJsonFileAndFindsItById() {
        Path filePath = tempDir.resolve("saved-games.json");
        JsonFileSavedGameRepository repository =
                new JsonFileSavedGameRepository(filePath, new ObjectMapper());

        SavedGame savedGame = createSavedGame();

        int id = repository.save(savedGame);

        assertEquals(1, id);
        assertTrue(Files.exists(filePath));
        assertEquals(savedGame, repository.findById(id).orElseThrow());
    }

    @Test
    void loadsSavedGamesFromExistingJsonFile() {
        Path filePath = tempDir.resolve("saved-games.json");
        ObjectMapper objectMapper = new ObjectMapper();

        JsonFileSavedGameRepository firstRepository =
                new JsonFileSavedGameRepository(filePath, objectMapper);

        SavedGame savedGame = createSavedGame();
        int id = firstRepository.save(savedGame);

        JsonFileSavedGameRepository secondRepository =
                new JsonFileSavedGameRepository(filePath, objectMapper);

        assertEquals(savedGame, secondRepository.findById(id).orElseThrow());
    }

    @Test
    void continuesIdSequenceAfterLoadingExistingJsonFile() {
        Path filePath = tempDir.resolve("saved-games.json");
        ObjectMapper objectMapper = new ObjectMapper();

        JsonFileSavedGameRepository firstRepository =
                new JsonFileSavedGameRepository(filePath, objectMapper);

        firstRepository.save(createSavedGame());

        JsonFileSavedGameRepository secondRepository =
                new JsonFileSavedGameRepository(filePath, objectMapper);

        int secondId = secondRepository.save(createSavedGame());

        assertEquals(2, secondId);
    }

    @Test
    void createsParentDirectoriesWhenSaving() {
        Path filePath = tempDir.resolve("nested").resolve("saved-games.json");

        JsonFileSavedGameRepository repository =
                new JsonFileSavedGameRepository(filePath, new ObjectMapper());

        repository.save(createSavedGame());

        assertTrue(Files.exists(filePath));
    }

    @Test
    void rejectsNullSavedGame() {
        Path filePath = tempDir.resolve("saved-games.json");

        JsonFileSavedGameRepository repository =
                new JsonFileSavedGameRepository(filePath, new ObjectMapper());

        assertThrows(
                IllegalArgumentException.class,
                () -> repository.save(null)
        );
    }

    @Test
    void rejectsInvalidSavedGameId() {
        Path filePath = tempDir.resolve("saved-games.json");

        JsonFileSavedGameRepository repository =
                new JsonFileSavedGameRepository(filePath, new ObjectMapper());

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
                        TeleportRuleType.USE_WORMHOLES,
                        List.of(new Wormhole(4, 9))
                ),
                List.of(12, 10, 12)
        );
    }
}