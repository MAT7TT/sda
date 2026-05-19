package uk.ac.mmu.assignment26.infrastructure.driving;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.GameResult;
import uk.ac.mmu.assignment26.infrastructure.output.ConsoleScenarioPrinter;
import uk.ac.mmu.assignment26.infrastructure.scenarios.GameScenario;
import uk.ac.mmu.assignment26.infrastructure.scenarios.GameScenarioProvider;
import uk.ac.mmu.assignment26.usecase.PlayGameUseCase;
import uk.ac.mmu.assignment26.usecase.ReplayGameUseCase;
import uk.ac.mmu.assignment26.usecase.SavedGame;
import uk.ac.mmu.assignment26.usecase.ports.SavedGameRepository;

import java.util.Optional;

@Component
public class ConsoleGameRunner implements CommandLineRunner, Ordered {
    private final PlayGameUseCase playGameUseCase;
    private final ReplayGameUseCase replayGameUseCase;
    private final SavedGameRepository savedGameRepository;
    private final GameScenarioProvider scenarioProvider;
    private final ConsoleScenarioPrinter scenarioPrinter;

    public ConsoleGameRunner(
            PlayGameUseCase playGameUseCase,
            ReplayGameUseCase replayGameUseCase,
            SavedGameRepository savedGameRepository,
            GameScenarioProvider scenarioProvider,
            ConsoleScenarioPrinter scenarioPrinter
    ) {
        this.playGameUseCase = playGameUseCase;
        this.replayGameUseCase = replayGameUseCase;
        this.savedGameRepository = savedGameRepository;
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

        int gameId;

        if (scenario.usesFixedDice()) {
            gameId = playGameUseCase.play(
                    scenario.configuration(),
                    scenario.fixedDiceRolls()
            );
        } else {
            gameId = playGameUseCase.play(scenario.configuration());
        }

        printSavedGame(gameId);
    }

    private void printSavedGame(int gameId) {
        Optional<SavedGame> savedGame = savedGameRepository.findById(gameId);

        if (savedGame.isEmpty()) {
            return;
        }

        scenarioPrinter.printSavedGame(gameId, savedGame.get());
    }

    private void replaySavedGame(int gameId) {
        Optional<SavedGame> savedGame = savedGameRepository.findById(gameId);

        if (savedGame.isEmpty()) {
            scenarioPrinter.printNoSavedGameFound(gameId);
            return;
        }

        scenarioPrinter.printReplayStart(gameId, savedGame.get());
        replayGameUseCase.replay(gameId);
        scenarioPrinter.printBlankLine();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}