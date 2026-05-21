package uk.ac.mmu.assignment26.usecase;

import uk.ac.mmu.assignment26.domain.Game;
import uk.ac.mmu.assignment26.domain.GameResult;
import uk.ac.mmu.assignment26.domain.config.GameConfiguration;
import uk.ac.mmu.assignment26.usecase.ports.GameFactory;
import uk.ac.mmu.assignment26.usecase.ports.PlayGame;
import uk.ac.mmu.assignment26.usecase.ports.SavedGameRepository;

import java.util.List;

public class PlayGameUseCase implements PlayGame {
  private final GameFactory gameFactory;
  private final SavedGameRepository savedGameRepository;

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

  public PlayGameResult play(GameConfiguration configuration) {
    validateConfiguration(configuration);

    Game game = gameFactory.createGame(configuration);
    GameResult result = game.play();

    return saveGame(configuration, result.diceRolls());
  }

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
