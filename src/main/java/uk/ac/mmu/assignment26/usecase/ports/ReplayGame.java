package uk.ac.mmu.assignment26.usecase.ports;

import uk.ac.mmu.assignment26.domain.GameResult;
import uk.ac.mmu.assignment26.usecase.SavedGame;

import java.util.Optional;

/**
 * Input port for replaying saved games.
 *
 * <p>This port represents the application use case for finding and replaying games that were previously saved.</p>
 */
public interface ReplayGame {
  /**
   * Finds a saved game by id.
   *
   * @param gameId the saved game id
   * @return the saved game if one exists
   * @throws IllegalArgumentException if the id is not positive
   */
  Optional<SavedGame> findSavedGame(int gameId);

  /**
   * Replays a saved game using its stored configuration and dice rolls.
   *
   * @param gameId the saved game id
   * @return the result produced by replaying the game
   * @throws IllegalArgumentException if the id is not positive or no saved game exists for the id
   */
  GameResult replay(int gameId);
}
