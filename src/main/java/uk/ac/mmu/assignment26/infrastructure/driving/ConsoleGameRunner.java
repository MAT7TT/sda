package uk.ac.mmu.assignment26.infrastructure.driving;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.infrastructure.output.ConsoleScenarioPrinter;
import uk.ac.mmu.assignment26.infrastructure.scenarios.GameScenario;
import uk.ac.mmu.assignment26.infrastructure.scenarios.GameScenarioProvider;
import uk.ac.mmu.assignment26.usecase.PlayGameResult;
import uk.ac.mmu.assignment26.usecase.SavedGame;
import uk.ac.mmu.assignment26.usecase.ports.PlayGame;
import uk.ac.mmu.assignment26.usecase.ports.ReplayGame;

/**
 * Console driving adapter for the application.
 *
 * <p>This class is started by Spring Boot and calls the play and replay use case ports. It
 * demonstrates the configured scenarios and then replays the saved games.
 */
@Component
public class ConsoleGameRunner implements CommandLineRunner, Ordered {
  private final PlayGame playGame;
  private final ReplayGame replayGame;
  private final GameScenarioProvider scenarioProvider;
  private final ConsoleScenarioPrinter scenarioPrinter;

  /**
   * Create the console game runner.
   *
   * @param playGame play-game input port
   * @param replayGame replay-game input port
   * @param scenarioProvider provider for demonstrating scenarios
   * @param scenarioPrinter printer for scenario-level output
   */
  public ConsoleGameRunner(
      PlayGame playGame,
      ReplayGame replayGame,
      GameScenarioProvider scenarioProvider,
      ConsoleScenarioPrinter scenarioPrinter) {
    this.playGame = playGame;
    this.replayGame = replayGame;
    this.scenarioProvider = scenarioProvider;
    this.scenarioPrinter = scenarioPrinter;
  }

  /**
   * Runs all configured scenarios and replays the saved games.
   *
   * @param args command line arguments supplied by Spring Boot
   */
  @Override
  public void run(String... args) {
    List<Integer> savedGameIds = new ArrayList<>();

    for (GameScenario scenario : scenarioProvider.getScenarios()) {
      savedGameIds.add(runScenario(scenario));
    }

    replaySavedGame(savedGameIds.getFirst());
  }

  private int runScenario(GameScenario scenario) {
    scenarioPrinter.printScenarioStart(scenario);

    PlayGameResult result;

    if (scenario.usesFixedDice()) {
      result = playGame.play(scenario.configuration(), scenario.fixedDiceRolls());
    } else {
      result = playGame.play(scenario.configuration());
    }

    scenarioPrinter.printSavedGame(result.gameId(), result.savedGame());

    return result.gameId();
  }

  private void replaySavedGame(int gameId) {
    Optional<SavedGame> savedGame = replayGame.findSavedGame(gameId);

    if (savedGame.isEmpty()) {
      scenarioPrinter.printNoSavedGameFound(gameId);
      return;
    }

    scenarioPrinter.printReplayStart(gameId, savedGame.get());
    replayGame.replay(gameId);
    scenarioPrinter.printBlankLine();
  }

  /**
   * Gives this runner higher precedence when Spring starts command line runners.
   *
   * @return the runner order
   */
  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE;
  }
}
