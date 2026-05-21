package uk.ac.mmu.assignment26.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.ac.mmu.assignment26.infrastructure.persistence.file.JsonFileSavedGameRepository;
import uk.ac.mmu.assignment26.infrastructure.persistence.memory.InMemorySavedGameRepository;
import uk.ac.mmu.assignment26.usecase.ports.SavedGameRepository;

import java.nio.file.Path;

@Configuration
public class PersistenceConfiguration {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }

  @Bean
  @Profile("file-persistence")
  public SavedGameRepository fileSavedGameRepository(ObjectMapper objectMapper) {
    Path filePath = Path.of(System.getProperty("user.home"), ".sda", "saved-games.json");

    return new JsonFileSavedGameRepository(filePath, objectMapper);
  }

  @Bean
  @Profile("memory-persistence")
  public SavedGameRepository inMemorySavedGameRepository() {
    return new InMemorySavedGameRepository();
  }
}
