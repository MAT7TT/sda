package uk.ac.mmu.assignment26.infrastructure.scenarios;

import java.util.List;
import uk.ac.mmu.assignment26.domain.config.GameConfiguration;

/**
 * Describes one demonstration scenario for the console runner.
 *
 * <p>A scenario contains a title, a game configuration and optionally a
 * fixed dice sequence.</p>
 */
public class GameScenario {
  private final String title;
  private final GameConfiguration configuration;
  private final List<Integer> fixedDiceRolls;

  /**
   * Creates a scenario with fixed dice rolls.
   *
   * @param title the scenario title
   * @param configuration the game configuration
   * @param fixedDiceRolls the fixed dice sequence
   */
  public GameScenario(String title, GameConfiguration configuration, List<Integer> fixedDiceRolls) {
    this.title = title;
    this.configuration = configuration;
    this.fixedDiceRolls = fixedDiceRolls;
  }

  /**
   * Create a scenario that uses random dice.
   *
   * @param title the scenario title
   * @param configuration the game configuration
   */
  public GameScenario(String title, GameConfiguration configuration) {
    this(title, configuration, List.of());
  }

  public String title() {
    return title;
  }

  public GameConfiguration configuration() {
    return configuration;
  }

  public List<Integer> fixedDiceRolls() {
    return fixedDiceRolls;
  }

  /**
   * Checks whether this scenario uses fixed dice.
   *
   * @return true if fixed dice rolls are configured
   */
  public boolean usesFixedDice() {
    return !fixedDiceRolls.isEmpty();
  }
}
