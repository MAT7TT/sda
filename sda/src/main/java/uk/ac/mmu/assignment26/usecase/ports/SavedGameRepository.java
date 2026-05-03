package uk.ac.mmu.assignment26.usecase.ports;

import uk.ac.mmu.assignment26.usecase.SavedGame;

import java.util.Optional;

public interface SavedGameRepository {
    int save(SavedGame savedGame);

    Optional<SavedGame> findById(int id);
}