package uk.ac.mmu.assignment26.infrastructure.persistence.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.ac.mmu.assignment26.usecase.SavedGame;
import uk.ac.mmu.assignment26.usecase.ports.SavedGameRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JsonFileSavedGameRepository implements SavedGameRepository {
    private final Path filePath;
    private final ObjectMapper objectMapper;
    private final Map<Integer, SavedGame> savedGames;
    private int nextId;

    public JsonFileSavedGameRepository(Path filePath, ObjectMapper objectMapper) {
        if (filePath == null) {
            throw new IllegalArgumentException("File path must not be null.");
        }

        if (objectMapper == null) {
            throw new IllegalArgumentException("Object mapper must not be null.");
        }

        this.filePath = filePath;
        this.objectMapper = objectMapper;

        SavedGameStore savedGameStore = readSavedGameStore();
        this.savedGames = new HashMap<>(savedGameStore.savedGames());
        this.nextId = savedGameStore.nextId();
    }

    @Override
    public int save(SavedGame savedGame) {
        if (savedGame == null) {
            throw new IllegalArgumentException("Saved game must not be null.");
        }

        int id = nextId;
        savedGames.put(id, savedGame);
        nextId++;

        writeSavedGameStore();

        return id;
    }

    @Override
    public Optional<SavedGame> findById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Saved game id must be positive.");
        }

        return Optional.ofNullable(savedGames.get(id));
    }

    private SavedGameStore readSavedGameStore() {
        try {
            if (!Files.exists(filePath) || Files.size(filePath) == 0) {
                return new SavedGameStore(1, Map.of());
            }

            return objectMapper.readValue(filePath.toFile(), SavedGameStore.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not read saved games file.", e);
        }
    }

    private void writeSavedGameStore() {
        try {
            Path parent = filePath.getParent();

            if (parent != null) {
                Files.createDirectories(parent);
            }

            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(filePath.toFile(), new SavedGameStore(nextId, savedGames));
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not  write saved games file.", e);
        }
    }

    public record SavedGameStore(
            int nextId,
            Map<Integer, SavedGame> savedGames
    ) {
        public SavedGameStore {
            if (nextId <= 0) {
                throw new IllegalArgumentException("Next id must be positive.");
            }

            if (savedGames == null) {
                throw new IllegalArgumentException("Saved games must not be null.");
            }

            savedGames = Map.copyOf(savedGames);
        }
    }
}
