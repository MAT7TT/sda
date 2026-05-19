package uk.ac.mmu.assignment26.usecase.ports;

import uk.ac.mmu.assignment26.domain.Game;
import uk.ac.mmu.assignment26.domain.config.GameConfiguration;
import uk.ac.mmu.assignment26.usecase.PlayGameResult;

import java.util.List;

public interface PlayGame {
    PlayGameResult play(GameConfiguration configuration);

    PlayGameResult play(GameConfiguration configuration, List<Integer> fixedDiceRolls);
}
