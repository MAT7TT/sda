package uk.ac.mmu.assignment26.usecase.ports;

import uk.ac.mmu.assignment26.domain.config.GameConfiguration;
import uk.ac.mmu.assignment26.usecase.PlayGameResult;

import java.util.List;

/**
 * Input port for playing a game.
 *
 * <p>This port is called by driving adapter, such as the console runner. It represents
 * the application use case for running a configured game to completion.</p>
 */
public interface PlayGame {
  /**
   * Plays a game using the game configuration.
   * @param configuration the game configuration to play
   * @return the saved game id and saved game data
   * @throws IllegalArgumentException if the configuration is null or breaks the use case contract
   */
  PlayGameResult play(GameConfiguration configuration);

  /**
   * Plays a game using a fixed sequence of dice rolls.
   *
   * @param configuration the game configuration to play
   * @param fixedDiceRolls the dice rolls to use
   * @return; the saved game id and saved game data
   * @throws IllegalArgumentException if the configuration or fixed dice rolls break
   * the use case contract
   */
  PlayGameResult play(GameConfiguration configuration, List<Integer> fixedDiceRolls);
}
