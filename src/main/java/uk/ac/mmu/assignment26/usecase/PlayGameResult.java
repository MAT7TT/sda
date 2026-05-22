package uk.ac.mmu.assignment26.usecase;

/**
 * Value object returned by the play use case.
 *
 * @param gameId the id assigned to the saved game
 * @param savedGame the saved game data
 * @throws IllegalArgumentException if the id or saved game is invalid
 */
public record PlayGameResult(int gameId, SavedGame savedGame) {

  public PlayGameResult {
    if (gameId <= 0) {
      throw new IllegalArgumentException("Game id must be positive.");
    }

    if (savedGame == null) {
      throw new IllegalArgumentException("Saved game must not be null.");
    }
  }
}
