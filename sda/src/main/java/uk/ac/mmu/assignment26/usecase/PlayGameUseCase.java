package uk.ac.mmu.assignment26.usecase;

import uk.ac.mmu.assignment26.domain.Game;
import uk.ac.mmu.assignment26.domain.GameResult;
import uk.ac.mmu.assignment26.domain.config.GameConfiguration;
import uk.ac.mmu.assignment26.usecase.ports.GameFactory;
import uk.ac.mmu.assignment26.usecase.ports.SavedGameRepository;

import java.util.List;

public class PlayGameUseCase {
    private final GameFactory gameFactory;
    private final SavedGameRepository savedGameRepository;

    public PlayGameUseCase(
            GameFactory gameFactory,
            SavedGameRepository savedGameRepository
    ) {
        this.gameFactory = gameFactory;
        this.savedGameRepository = savedGameRepository;
    }

    public int play(GameConfiguration configuration) {
        Game game = gameFactory.createGame(configuration);
        GameResult result = game.play();

        return saveGame(configuration, result);
    }

    public int play(GameConfiguration configuration, List<Integer> fixedDiceRolls) {
        Game game = gameFactory.createGame(configuration, fixedDiceRolls);
        GameResult result = game.play();

        return saveGame(configuration, result);
    }

    private int saveGame(GameConfiguration configuration, GameResult result) {
        SavedGame savedGame = new SavedGame(
                configuration,
                result.diceRolls()
        );

        return savedGameRepository.save(savedGame);
    }
}