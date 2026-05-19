package uk.ac.mmu.assignment26.usecase;

import uk.ac.mmu.assignment26.domain.Game;
import uk.ac.mmu.assignment26.domain.GameResult;
import uk.ac.mmu.assignment26.usecase.ports.GameFactory;
import uk.ac.mmu.assignment26.usecase.ports.SavedGameRepository;

public class ReplayGameUseCase {
    private final GameFactory gameFactory;
    private final SavedGameRepository savedGameRepository;

    public ReplayGameUseCase(
            GameFactory gameFactory,
            SavedGameRepository savedGameRepository
    ) {
        this.gameFactory = gameFactory;
        this.savedGameRepository = savedGameRepository;
    }

    public GameResult replay(int gameId) {
        SavedGame savedGame = savedGameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No saved game found for id " + gameId
                ));

        Game game = gameFactory.createGame(
                savedGame.configuration(),
                savedGame.diceRolls()
        );

        GameResult result = game.play();
        attemptExtraTurnsAfterGameOver(game, savedGame.diceRolls().size() - result.diceRolls().size());

        return result;
    }

    private void attemptExtraTurnsAfterGameOver(Game game, int extraTurnAttempts) {
        for (int i = 0; i < extraTurnAttempts; i++) {
            game.playTurn();
        }
    }
}