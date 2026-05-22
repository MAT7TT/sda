package uk.ac.mmu.assignment26.usecase;

import uk.ac.mmu.assignment26.domain.Game;
import uk.ac.mmu.assignment26.domain.GameResult;
import uk.ac.mmu.assignment26.usecase.ports.GameFactory;
import uk.ac.mmu.assignment26.usecase.ports.ReplayGame;
import uk.ac.mmu.assignment26.usecase.ports.SavedGameRepository;

import java.util.Optional;

/**
 * Use case for finding and replaying saved games
 *
 * <p>Replay uses the saved configuration and dice rolls to rebuild and execute
 * the game again. It does not replay by printing stored console output.</p>
 */
public class ReplayGameUseCase implements ReplayGame {
  private final GameFactory gameFactory;
  private final SavedGameRepository savedGameRepository;

  /**
   * Creates the replay-game use case.
   *
   * @param gameFactory used to rebuild saved games
   * @param savedGameRepository repository used to load saved games
   * @throws IllegalArgumentException if a required port is null
   */
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

  /**
   * Finds a saved game by id
   *
   * @param gameId the saved game id
   * @return the saved game if one exists
   * @throws IllegalArgumentException if the id is not positive
   */
  @Override
  public Optional<SavedGame> findSavedGame(int gameId) {
    if (gameId <= 0) {
      throw new IllegalArgumentException("Game id must be positive.");
    }

    return savedGameRepository.findById(gameId);
  }

  /**
   * Replays a saved game.
   *
   * @param gameId the saved game id
   * @return the result produced by replaying the game
   * @throws IllegalArgumentException if the id is not positive or no saved game exists for the id
   */
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
