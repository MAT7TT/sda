package uk.ac.mmu.assignment26.infrastructure.persistence;

import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.usecase.SavedGame;
import uk.ac.mmu.assignment26.usecase.ports.SavedGameRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemorySavedGameRepository implements SavedGameRepository {
    private final Map<Integer, SavedGame> savedGames = new HashMap<>();
    private int nextId = 1;

    @Override
    public int save(SavedGame savedGame) {
        int id = nextId;
        savedGames.put(id, savedGame);
        nextId++;
        return id;
    }

    @Override
    public Optional<SavedGame> findById(int id) {
        return Optional.ofNullable(savedGames.get(id));
    }
}