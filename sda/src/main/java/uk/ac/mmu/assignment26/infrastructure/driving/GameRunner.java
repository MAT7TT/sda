package uk.ac.mmu.assignment26.infrastructure.driving;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.infrastructure.scenarios.GameScenario;
import uk.ac.mmu.assignment26.infrastructure.scenarios.GameScenarioProvider;
import uk.ac.mmu.assignment26.usecase.PlayGameUseCase;
import uk.ac.mmu.assignment26.usecase.ReplayGameUseCase;

@Component
public class GameRunner implements CommandLineRunner, Ordered {
    private final PlayGameUseCase playGameUseCase;
    private final ReplayGameUseCase replayGameUseCase;
    private final GameScenarioProvider scenarioProvider;

    public GameRunner(
            PlayGameUseCase playGameUseCase,
            ReplayGameUseCase replayGameUseCase,
            GameScenarioProvider scenarioProvider
    ) {
        this.playGameUseCase = playGameUseCase;
        this.replayGameUseCase = replayGameUseCase;
        this.scenarioProvider = scenarioProvider;
    }

    @Override
    public void run(String... args) {
        for (GameScenario scenario : scenarioProvider.getScenarios()) {
            runScenario(scenario);
        }

        replaySavedGame(1);
    }

    private void runScenario(GameScenario scenario) {
        System.out.println(scenario.title());

        int gameId;

        if (scenario.usesFixedDice()) {
            gameId = playGameUseCase.play(
                    scenario.configuration(),
                    scenario.fixedDiceRolls()
            );
        } else {
            gameId = playGameUseCase.play(scenario.configuration());
        }

        System.out.println("Game Id: " + gameId + " saved.");
        System.out.println();
    }

    private void replaySavedGame(int gameId) {
        System.out.println("Replay Game Id: " + gameId);
        replayGameUseCase.replay(gameId);
        System.out.println();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}