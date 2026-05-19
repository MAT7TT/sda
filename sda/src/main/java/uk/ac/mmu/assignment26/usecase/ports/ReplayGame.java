package uk.ac.mmu.assignment26.usecase.ports;

import uk.ac.mmu.assignment26.usecase.SavedGame;

import java.util.Optional;

public interface ReplayGame {
    Optional<SavedGame> findSavedGame(int gameId);

    void replay(int gameId);
}
