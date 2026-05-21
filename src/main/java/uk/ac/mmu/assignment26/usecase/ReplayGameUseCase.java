package uk.ac.mmu.assignment26.usecase;

import uk.ac.mmu.assignment26.domain.Game;
import uk.ac.mmu.assignment26.domain.GameResult;
import uk.ac.mmu.assignment26.usecase.ports.GameFactory;
import uk.ac.mmu.assignment26.usecase.ports.ReplayGame;
import uk.ac.mmu.assignment26.usecase.ports.SavedGameRepository;

import java.util.Optional;

public class ReplayGameUseCase implements ReplayGame {
  private final GameFactory gameFactory;
  private final SavedGameRepository savedGameRepository;

  public ReplayGameUseCase(GameFactory gameFactory, SavedGameRepository savedGameRepository) {
    if (gameFactory == null) {
      throw new IllegalArgumentException("Game factory must not be null.");
    }

    if (savedGameRepository == null) {
      throw new IllegalArgumentException("Saved game repository must not be null.");
    }

    this.gameFactory = gameFactory;
    this.savedGameRepository = savedGameRepository;
  }

  @Override
  public Optional<SavedGame> findSavedGame(int gameId) {
    if (gameId <= 0) {
      throw new IllegalArgumentException("Game id must be positive.");
    }

    return savedGameRepository.findById(gameId);
  }

  public GameResult replay(int gameId) {
    SavedGame savedGame =
        findSavedGame(gameId)
            .orElseThrow(
                () -> new IllegalArgumentException("No saved game found for id " + gameId));

    Game game = gameFactory.createGame(savedGame.configuration(), savedGame.diceRolls());

    GameResult result = game.play();
    attemptExtraTurnsAfterGameOver(game, savedGame.diceRolls().size() - result.diceRolls().size());

    return result;
  }

  private void attemptExtraTurnsAfterGameOver(Game game, int extraTurnAttempts) {
    for (int i = 0; i < extraTurnAttempts; i++) {
      game.playTurn();
    }
  }
}
