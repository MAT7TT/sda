package uk.ac.mmu.assignment26.usecase.ports;

import java.util.Optional;
import uk.ac.mmu.assignment26.usecase.SavedGame;

/**
 * Output port for saving and retrieving completed games.
 *
 * <p>Concrete persistence adapters can store games in memory, in a file, or in another data store
 * without changing the use case code.</p>
 */
public interface SavedGameRepository {
  /**
   * Saves a completed game.
   *
   * @param savedGame the saved game data
   * @return the assigned game id
   * @throws IllegalArgumentException if the saved game is null
   */
  int save(SavedGame savedGame);

  /**
   * Finds a saved game by id.
   *
   * @param id the saved game id
   * @return the saved game if one exists
   * @throws IllegalArgumentException if the id is not positive
   */
  Optional<SavedGame> findById(int id);
}
