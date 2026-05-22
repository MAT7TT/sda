package uk.ac.mmu.assignment26.infrastructure.persistence.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import uk.ac.mmu.assignment26.usecase.SavedGame;
import uk.ac.mmu.assignment26.usecase.ports.SavedGameRepository;

/**
 * JSON file adapter for the saved game repository port.
 *
 * <p>This adapter persists saved games to a JSON file using Jackson. It is a driven adapter in the
 * ports-and-adapters architecture.
 */
public class JsonFileSavedGameRepository implements SavedGameRepository {
  private final Path filePath;
  private final ObjectMapper objectMapper;
  private final Map<Integer, SavedGame> savedGames;
  private int nextId;

  /**
   * Creates a JSON file saved game repository.
   *
   * @param filePath the JSON file path
   * @param objectMapper the Jackson object mapper
   * @throws IllegalArgumentException if the file path or object mapper is null
   * @throws IllegalStateException if existing saved games cannot be read
   */
  public JsonFileSavedGameRepository(Path filePath, ObjectMapper objectMapper) {
    if (filePath == null) {
      throw new IllegalArgumentException("File path must not be null.");
    }

    if (objectMapper == null) {
      throw new IllegalArgumentException("Object mapper must not be null.");
    }

    this.filePath = filePath;
    this.objectMapper = objectMapper;

    SavedGameStore savedGameStore = readSavedGameStore();
    this.savedGames = new HashMap<>(savedGameStore.savedGames());
    this.nextId = savedGameStore.nextId();
  }

  /**
   * Saves a game to the JSON file.
   *
   * @param savedGame the saved game data
   * @return the assigned id
   * @throws IllegalArgumentException if the saved game is null
   * @throws IllegalStateException if the file cannot be written
   */
  @Override
  public int save(SavedGame savedGame) {
    if (savedGame == null) {
      throw new IllegalArgumentException("Saved game must not be null.");
    }

    int id = nextId;
    savedGames.put(id, savedGame);
    nextId++;

    writeSavedGameStore();

    return id;
  }

  /**
   * Finds a saved game loaded from the JSON file.
   *
   * @param id the saved game id
   * @return the saved game if it exists
   * @throws IllegalArgumentException if the id is not positive
   */
  @Override
  public Optional<SavedGame> findById(int id) {
    if (id <= 0) {
      throw new IllegalArgumentException("Saved game id must be positive.");
    }

    return Optional.ofNullable(savedGames.get(id));
  }

  private SavedGameStore readSavedGameStore() {
    try {
      if (!Files.exists(filePath) || Files.size(filePath) == 0) {
        return new SavedGameStore(1, Map.of());
      }

      return objectMapper.readValue(filePath.toFile(), SavedGameStore.class);
    } catch (IOException e) {
      throw new IllegalStateException("Could not read saved games file.", e);
    }
  }

  private void writeSavedGameStore() {
    try {
      Path parent = filePath.getParent();

      if (parent != null) {
        Files.createDirectories(parent);
      }

      objectMapper
          .writerWithDefaultPrettyPrinter()
          .writeValue(filePath.toFile(), new SavedGameStore(nextId, savedGames));
    } catch (IOException e) {
      throw new IllegalStateException("Could not write saved games file.", e);
    }
  }

  /**
   * Value object representing the JSON file contents.
   *
   * @param nextId the next id to assign
   * @param savedGames saved games indexed by id
   * @throws IllegalArgumentException if the next id or saved game map is invalid
   */
  public record SavedGameStore(int nextId, Map<Integer, SavedGame> savedGames) {
    public SavedGameStore {
      if (nextId <= 0) {
        throw new IllegalArgumentException("Next id must be positive.");
      }

      if (savedGames == null) {
        throw new IllegalArgumentException("Saved games must not be null.");
      }

      savedGames = Map.copyOf(savedGames);
    }
  }
}
