package uk.ac.mmu.assignment26.infrastructure.driving;

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

import java.util.Optional;

@Component
public class ConsoleGameRunner implements CommandLineRunner, Ordered {
    private final PlayGame playGame;
    private final ReplayGame replayGame;
    private final GameScenarioProvider scenarioProvider;
    private final ConsoleScenarioPrinter scenarioPrinter;

    public ConsoleGameRunner(
            PlayGame playGame,
            ReplayGame replayGame,
            GameScenarioProvider scenarioProvider,
            ConsoleScenarioPrinter scenarioPrinter
    ) {
        this.playGame = playGame;
        this.replayGame = replayGame;
        this.scenarioProvider = scenarioProvider;
        this.scenarioPrinter = scenarioPrinter;
    }

    @Override
    public void run(String... args) {
        for (GameScenario scenario : scenarioProvider.getScenarios()) {
            runScenario(scenario);
        }

        replaySavedGame(1);
    }

    private void runScenario(GameScenario scenario) {
        scenarioPrinter.printScenarioStart(scenario);

        PlayGameResult result;

        if (scenario.usesFixedDice()) {
            result = playGame.play(
                    scenario.configuration(),
                    scenario.fixedDiceRolls()
            );
        } else {
            result = playGame.play(scenario.configuration());
        }

        scenarioPrinter.printSavedGame(result.gameId(), result.savedGame());
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

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}