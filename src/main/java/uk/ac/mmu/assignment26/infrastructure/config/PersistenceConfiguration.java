package uk.ac.mmu.assignment26.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.ac.mmu.assignment26.infrastructure.persistence.file.JsonFileSavedGameRepository;
import uk.ac.mmu.assignment26.infrastructure.persistence.memory.InMemorySavedGameRepository;
import uk.ac.mmu.assignment26.usecase.ports.SavedGameRepository;

import java.nio.file.Path;

/**
 * Spring configuration for persistence adapters.
 *
 * <p>The active Spring profile decides whether saved games are stored
 * in memory or in a JSON file.</p>
 */
@Configuration
public class PersistenceConfiguration {

  /**
   * Create a Jackson object mapper used by JSON persistence.
   *
   * @return the object mapper
   */
  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  /**
   * Creates the JSON file saved game repository.
   *
   * @param objectMapper object mapper used for JSON serialisation
   * @return the file persistence adapter
   */
  @Bean
  @Profile("file-persistence")
  public SavedGameRepository fileSavedGameRepository(ObjectMapper objectMapper) {
    Path filePath = Path.of(System.getProperty("user.home"), ".sda", "saved-games.json");

    return new JsonFileSavedGameRepository(filePath, objectMapper);
  }

  /**
   * Create the in-memory saved game repository.
   *
   * @return the in-memory persistence adapter
   */
  @Bean
  @Profile("memory-persistence")
  public SavedGameRepository inMemorySavedGameRepository() {
    return new InMemorySavedGameRepository();
  }
}
