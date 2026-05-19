package uk.ac.mmu.assignment26.usecase;

import uk.ac.mmu.assignment26.domain.Game;
import uk.ac.mmu.assignment26.domain.GameResult;
import uk.ac.mmu.assignment26.usecase.ports.GameFactory;
import uk.ac.mmu.assignment26.usecase.ports.ReplayGame;
import uk.ac.mmu.assignment26.usecase.ports.SavedGameRepository;

import java.util.Optional;

public class ReplayGameUseCase implements ReplayGame {
    private final GameFactory gameFactory;
    private final SavedGameRepository savedGameRepository;

    public ReplayGameUseCase(
            GameFactory gameFactory,
            SavedGameRepository savedGameRepository
    ) {
        this.gameFactory = gameFactory;
        this.savedGameRepository = savedGameRepository;
    }

    @Override
    public Optional<SavedGame> findSavedGame(int gameId) {
        return savedGameRepository.findById(gameId);
    }

    public void replay(int gameId) {
        SavedGame savedGame = findSavedGame(gameId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No saved game found for id " + gameId
                ));

        Game game = gameFactory.createGame(
                savedGame.configuration(),
                savedGame.diceRolls()
        );

        GameResult result = game.play();
        attemptExtraTurnsAfterGameOver(
                game,
                savedGame.diceRolls().size() - result.diceRolls().size()
        );
    }

    private void attemptExtraTurnsAfterGameOver(Game game, int extraTurnAttempts) {
        for (int i = 0; i < extraTurnAttempts; i++) {
            game.playTurn();
        }
    }
}