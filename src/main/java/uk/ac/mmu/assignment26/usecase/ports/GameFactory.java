package uk.ac.mmu.assignment26.usecase.ports;

import java.util.List;
import uk.ac.mmu.assignment26.domain.Game;
import uk.ac.mmu.assignment26.domain.config.GameConfiguration;

/**
 * Output port for creating configured domain games.
 *
 * <p>The use cases depend on abstraction instead of constructing
 * concrete domain and infrastructure objects directly.</p>
 */
public interface GameFactory {
  /**
   * Creates a game from the supplied configuration.
   *
   * @param configuration the game configuration
   * @return the configured game
   * @throws IllegalArgumentException if the configuration breaks the factory contract
   */
  Game createGame(GameConfiguration configuration);

  /**
   * Creates a game from the supplied configuration and fixed dice rolls.
   *
   * @param configuration the game configuration
   * @param fixedDiceRolls the dice rolls to use
   * @return the configured game
   * @throws IllegalArgumentException if the configuration or fixed dice rolls break the factory contract
   */
  Game createGame(GameConfiguration configuration, List<Integer> fixedDiceRolls);
}
