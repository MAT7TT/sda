package uk.ac.mmu.assignment26.infrastructure.output;

import java.util.List;
import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.config.DiceType;
import uk.ac.mmu.assignment26.infrastructure.scenarios.GameScenario;
import uk.ac.mmu.assignment26.usecase.SavedGame;

/**
 * Prints scenario-level console output.
 *
 * <p>This class prints information before and after a game, while turn-by-turn output is handled by
 * the event observer.
 */
@Component
public class ConsoleScenarioPrinter {
  private final GameOutputWriter outputWriter;
  private final GameConfigurationFormatter configurationFormatter;

  /**
   * Creates the scenario printer.
   *
   * @param outputWriter writer used to send output to the console
   * @param configurationFormatter formatter for game configuration text
   */
  public ConsoleScenarioPrinter(
      GameOutputWriter outputWriter, GameConfigurationFormatter configurationFormatter) {
    this.outputWriter = outputWriter;
    this.configurationFormatter = configurationFormatter;
  }

  /**
   * Prints the title, rule summary and dice information for a scenario.
   *
   * @param scenario the scenario being started
   */
  public void printScenarioStart(GameScenario scenario) {
    outputWriter.writeLine(scenario.title());
    outputWriter.writeLine(configurationFormatter.formatRules(scenario.configuration()));

    if (scenario.usesFixedDice()) {
      outputWriter.writeLine(
          configurationFormatter.formatFixedDice(scenario.configuration().diceType())
              + " "
              + formatDiceRolls(scenario.fixedDiceRolls()));
    } else {
      printRandomDiceSummary(scenario.configuration().diceType());
    }
  }

  /**
   * Prints the saved-game summary after a game finishes.
   *
   * @param gameId the saved game id
   * @param savedGame the saved game data
   */
  public void printSavedGame(int gameId, SavedGame savedGame) {
    outputWriter.writeLine("Dice rolls: " + formatDiceRolls(savedGame.diceRolls()));
    outputWriter.writeLine("Game Id: " + gameId + " saved.");
    outputWriter.writeBlankLine();
  }

  /**
   * Prints the heading and setup for a replay.
   *
   * @param gameId the saved game id being replayed
   * @param savedGame the saved game data
   */
  public void printReplayStart(int gameId, SavedGame savedGame) {
    outputWriter.writeLine("Replay Game Id: " + gameId);
    outputWriter.writeLine(configurationFormatter.formatRules(savedGame.configuration()));
    outputWriter.writeLine(
        "Dice: Replay "
            + configurationFormatter.formatFixedDice(savedGame.configuration().diceType())
            + " "
            + formatDiceRolls(savedGame.diceRolls()));
  }

  /**
   * Prints a message when a saved game cannot be found.
   *
   * @param gameId the missing saved game id
   */
  public void printNoSavedGameFound(int gameId) {
    outputWriter.writeLine("No saved game found for id " + gameId);
    outputWriter.writeBlankLine();
  }

  /** Prints a blank line. */
  public void printBlankLine() {
    outputWriter.writeBlankLine();
  }

  private void printRandomDiceSummary(DiceType diceType) {
    outputWriter.writeLine(configurationFormatter.formatRandomDice(diceType));
  }

  private String formatDiceRolls(List<Integer> diceRolls) {
    return diceRolls.toString().replace("[", "{").replace("]", "}");
  }
}
