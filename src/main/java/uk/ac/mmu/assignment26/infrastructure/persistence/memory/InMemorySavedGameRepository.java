package uk.ac.mmu.assignment26.infrastructure.persistence.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import uk.ac.mmu.assignment26.usecase.SavedGame;
import uk.ac.mmu.assignment26.usecase.ports.SavedGameRepository;

/**
 * In-memory adapter for the saved game repository port.
 * <p>This adapter stores saved games only for the lifetime of the application.</p>
 */
public class InMemorySavedGameRepository implements SavedGameRepository {
  private final Map<Integer, SavedGame> savedGames = new HashMap<>();
  private int nextId = 1;

  /**
   * Saves a game in memory.
   *
   * @param savedGame the saved game id
   * @return the assigned id
   * @throws IllegalArgumentException if the saved game is null
   */
  @Override
  public int save(SavedGame savedGame) {
    if (savedGame == null) {
      throw new IllegalArgumentException("Saved game must not be null.");
    }

    int id = nextId;
    savedGames.put(id, savedGame);
    nextId++;
    return id;
  }

  /**
   * Finds a saved game in memory.
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
}
