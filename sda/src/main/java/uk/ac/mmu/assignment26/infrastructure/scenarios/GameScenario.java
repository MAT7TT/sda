package uk.ac.mmu.assignment26.infrastructure.scenarios;

import uk.ac.mmu.assignment26.domain.config.GameConfiguration;

import java.util.List;

public class GameScenario {
  private final String title;
  private final GameConfiguration configuration;
  private final List<Integer> fixedDiceRolls;

  public GameScenario(String title, GameConfiguration configuration, List<Integer> fixedDiceRolls) {
    this.title = title;
    this.configuration = configuration;
    this.fixedDiceRolls = fixedDiceRolls;
  }

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

  public boolean usesFixedDice() {
    return !fixedDiceRolls.isEmpty();
  }
}
