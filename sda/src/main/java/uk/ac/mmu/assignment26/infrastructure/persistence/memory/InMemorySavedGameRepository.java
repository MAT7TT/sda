package uk.ac.mmu.assignment26.infrastructure.persistence.memory;

import uk.ac.mmu.assignment26.usecase.SavedGame;
import uk.ac.mmu.assignment26.usecase.ports.SavedGameRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemorySavedGameRepository implements SavedGameRepository {
  private final Map<Integer, SavedGame> savedGames = new HashMap<>();
  private int nextId = 1;

  @Override
  public int save(SavedGame savedGame) {
    if (savedGame == null) {
      throw new IllegalArgumentException("Saved game must not be null.");
    }

    int id = nextId;
    savedGames.put(id, savedGame);
    nextId++;
    return id;
  }

  @Override
  public Optional<SavedGame> findById(int id) {
    if (id <= 0) {
      throw new IllegalArgumentException("Saved game id must be positive.");
    }

    return Optional.ofNullable(savedGames.get(id));
  }
}
