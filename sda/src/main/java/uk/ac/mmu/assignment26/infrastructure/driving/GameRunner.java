package uk.ac.mmu.assignment26.infrastructure.driving;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.config.DiceType;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;
import uk.ac.mmu.assignment26.domain.config.GameConfiguration;
import uk.ac.mmu.assignment26.domain.config.HitRuleType;
import uk.ac.mmu.assignment26.domain.config.TeleportRuleType;
import uk.ac.mmu.assignment26.usecase.PlayGameUseCase;

import java.util.List;

@Component
public class GameRunner implements CommandLineRunner, Ordered {
    private final PlayGameUseCase playGameUseCase;

    public GameRunner(PlayGameUseCase playGameUseCase) {
        this.playGameUseCase = playGameUseCase;
    }

    @Override
    public void run(String... args) {
        System.out.println("==================================================");
        System.out.println("Basic game: Red wins in 2 turns");
        System.out.println("==================================================");

        GameConfiguration configuration = new GameConfiguration(
                5,
                5,
                2,
                DiceType.DOUBLE,
                EndRuleType.STANDARD,
                HitRuleType.IGNORE_HITS,
                TeleportRuleType.IGNORE_WORMHOLES,
                List.of()
        );

        List<Integer> fixedDiceRolls = List.of(12, 10, 12);

        int gameId = playGameUseCase.play(configuration, fixedDiceRolls);

        System.out.println("Game Id: " + gameId + " saved.");
        System.out.println();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}