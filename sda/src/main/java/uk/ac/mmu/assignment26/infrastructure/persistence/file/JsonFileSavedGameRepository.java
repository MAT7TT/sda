package uk.ac.mmu.assignment26.infrastructure.persistence.file;

import uk.ac.mmu.assignment26.usecase.SavedGame;
import uk.ac.mmu.assignment26.usecase.ports.SavedGameRepository;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;



public class JsonFileSavedGameRepository implements SavedGameRepository {
    private final Path filePath;
    private final ObjectMapper objectMapper;
    private final Map<Integer, SavedGame> savedGames;
    private int nextId;

    public JsonFileSavedGameRepository(Path filePath, ObjectMapper objectMapper) {
        this.filePath = filePath;
        this.objectMapper = objectMapper;
    }

    @Override
    public int save(SavedGame savedGame) {
        // validate savedGame
        // assign id
        // store in map
        // write JSON file
        // return id
    }

    @Override
    public Optional<SavedGame> findById(int id) {
        // read from map
    }
}
