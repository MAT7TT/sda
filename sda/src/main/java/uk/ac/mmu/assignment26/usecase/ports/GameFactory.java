package uk.ac.mmu.assignment26.ports;

import uk.ac.mmu.assignment26.domain.Game;
import uk.ac.mmu.assignment26.domain.config.GameConfiguration;

import java.util.List;

public interface GameFactory {
    Game createGame(GameConfiguration configuration);

    Game createGame(GameConfiguration configuration, List<Integer> fixedDiceRolls);
}