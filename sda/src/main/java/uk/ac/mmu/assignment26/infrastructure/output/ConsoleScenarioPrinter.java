package uk.ac.mmu.assignment26.infrastructure.output;

import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.config.DiceType;
import uk.ac.mmu.assignment26.infrastructure.scenarios.GameScenario;
import uk.ac.mmu.assignment26.usecase.SavedGame;

import java.util.List;

@Component
public class ConsoleScenarioPrinter {
    private final GameOutputWriter outputWriter;

    public ConsoleScenarioPrinter(GameOutputWriter outputWriter) {
        this.outputWriter = outputWriter;
    }

    public void printScenarioStart(GameScenario scenario) {
        outputWriter.writeLine(scenario.title());
        outputWriter.writeLine(scenario.configuration().getRuleDescription());

        if (scenario.usesFixedDice()) {
            outputWriter.writeLine("Fixed sequence of dice rolls "
                    + formatDiceRolls(scenario.fixedDiceRolls()));
        } else {
            printRandomDiceSummary(scenario.configuration().diceType());
        }
    }

    public void printSavedGame(int gameId, SavedGame savedGame) {
        outputWriter.writeLine("Dice rolls: " + formatDiceRolls(savedGame.diceRolls()));
        outputWriter.writeLine("Game Id: " + gameId + " saved.");
        outputWriter.writeBlankLine();
    }

    public void printReplayStart(int gameId, SavedGame savedGame) {
        outputWriter.writeLine("Replay Game Id: " + gameId);
        outputWriter.writeLine(savedGame.configuration().getRuleDescription());
        outputWriter.writeLine("Dice: Replay sequence of dice rolls "
                + formatDiceRolls(savedGame.diceRolls()));
    }

    public void printNoSavedGameFound(int gameId) {
        outputWriter.writeLine("No saved game found for id " + gameId);
        outputWriter.writeBlankLine();
    }

    public void printBlankLine() {
        outputWriter.writeBlankLine();
    }

    private void printRandomDiceSummary(DiceType diceType) {
        if (diceType == DiceType.SINGLE) {
            outputWriter.writeLine("Single random 6 sided die");
            return;
        }

        outputWriter.writeLine("Two random 6 sided dice");
    }

    private String formatDiceRolls(List<Integer> diceRolls) {
        return diceRolls.toString()
                .replace("[", "{")
                .replace("]", "}");
    }
}