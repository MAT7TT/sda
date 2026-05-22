package uk.ac.mmu.assignment26.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.mmu.assignment26.usecase.PlayGameUseCase;
import uk.ac.mmu.assignment26.usecase.ReplayGameUseCase;
import uk.ac.mmu.assignment26.usecase.ports.GameFactory;
import uk.ac.mmu.assignment26.usecase.ports.PlayGame;
import uk.ac.mmu.assignment26.usecase.ports.ReplayGame;
import uk.ac.mmu.assignment26.usecase.ports.SavedGameRepository;

/**
 * Spring configuration for use case beans.
 *
 * <p>The Spring Dependency injection container supplies the required ports to each use case.
 */
@Configuration
public class UseCaseConfiguration {

  /**
   * Create the play-game input port
   *
   * @param gameFactory game factory port
   * @param savedGameRepository saved game repository port
   * @return the play-game use case
   */
  @Bean
  public PlayGame playGameUseCase(
      GameFactory gameFactory, SavedGameRepository savedGameRepository) {
    return new PlayGameUseCase(gameFactory, savedGameRepository);
  }

  /**
   * Creates the replay-game input port.
   *
   * @param gameFactory game factory port
   * @param savedGameRepository saved game repository port
   * @return the replay-game use case
   */
  @Bean
  public ReplayGame replayGameUseCase(
      GameFactory gameFactory, SavedGameRepository savedGameRepository) {
    return new ReplayGameUseCase(gameFactory, savedGameRepository);
  }
}
