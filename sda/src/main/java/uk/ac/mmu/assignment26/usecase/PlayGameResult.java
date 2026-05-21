package uk.ac.mmu.assignment26.usecase;

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
