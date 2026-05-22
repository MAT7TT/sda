package uk.ac.mmu.assignment26.usecase;

import java.util.List;
import uk.ac.mmu.assignment26.domain.Game;
import uk.ac.mmu.assignment26.domain.GameResult;
import uk.ac.mmu.assignment26.domain.config.GameConfiguration;
import uk.ac.mmu.assignment26.usecase.ports.GameFactory;
import uk.ac.mmu.assignment26.usecase.ports.PlayGame;
import uk.ac.mmu.assignment26.usecase.ports.SavedGameRepository;

/**
 * Use case for playing and saving a configured game.
 *
 * <p>The use case coordinates application flow. It asks the game factory to create a domain game,
 * runs the game to completion and saves the configuration and dice rolls through the repository
 * port.
 */
public class PlayGameUseCase implements PlayGame {
  private final GameFactory gameFactory;
  private final SavedGameRepository savedGameRepository;

  /**
   * Create the play-game use case.
   *
   * @param gameFactory factory used to create configured games
   * @param savedGameRepository used to save completed games
   * @throws IllegalArgumentException if a required port is null
   */
  public PlayGameUseCase(GameFactory gameFactory, SavedGameRepository savedGameRepository) {
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
   * Plays a game using the dice behaviour selected by the configuration.
   *
   * @param configuration the game configuration to play
   * @return the saved game result
   * @throws IllegalArgumentException if the configuration is null
   */
  public PlayGameResult play(GameConfiguration configuration) {
    validateConfiguration(configuration);

    Game game = gameFactory.createGame(configuration);
    GameResult result = game.play();

    return saveGame(configuration, result.diceRolls());
  }

  /**
   * Plays a game using a fixed dice sequence.
   *
   * <p>This method is used for deterministic demonstration scenarios and for replaying a known dice
   * sequence.
   *
   * @param configuration the game configuration to play
   * @param fixedDiceRolls the dice rolls to use
   * @return the saved game result
   * @throws IllegalArgumentException if the configuration is null or the fixed dice sequence is
   *     empty.
   */
  public PlayGameResult play(GameConfiguration configuration, List<Integer> fixedDiceRolls) {
    validateConfiguration(configuration);
    validateFixedDiceRolls(fixedDiceRolls);

    Game game = gameFactory.createGame(configuration, fixedDiceRolls);
    GameResult result = game.play();

    attemptExtraTurnsAfterGameOver(game, fixedDiceRolls.size() - result.diceRolls().size());

    return saveGame(configuration, fixedDiceRolls);
  }

  private void validateConfiguration(GameConfiguration configuration) {
    if (configuration == null) {
      throw new IllegalArgumentException("Game configuration must not be null.");
    }
  }

  private void validateFixedDiceRolls(List<Integer> fixedDiceRolls) {
    if (fixedDiceRolls == null || fixedDiceRolls.isEmpty()) {
      throw new IllegalArgumentException("Fixed dice rolls must not be empty.");
    }
  }

  private void attemptExtraTurnsAfterGameOver(Game game, int extraTurnAttempts) {
    for (int i = 0; i < extraTurnAttempts; i++) {
      game.playTurn();
    }
  }

  private PlayGameResult saveGame(GameConfiguration configuration, List<Integer> diceRolls) {
    SavedGame savedGame = new SavedGame(configuration, diceRolls);

    int gameId = savedGameRepository.save(savedGame);

    return new PlayGameResult(gameId, savedGame);
  }
}
