package uk.ac.mmu.assignment26.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.mmu.assignment26.usecase.PlayGameUseCase;
import uk.ac.mmu.assignment26.usecase.ReplayGameUseCase;
import uk.ac.mmu.assignment26.usecase.ports.GameFactory;
import uk.ac.mmu.assignment26.usecase.ports.PlayGame;
import uk.ac.mmu.assignment26.usecase.ports.ReplayGame;
import uk.ac.mmu.assignment26.usecase.ports.SavedGameRepository;

@Configuration
public class UseCaseConfiguration {

  @Bean
  public PlayGame playGameUseCase(
      GameFactory gameFactory, SavedGameRepository savedGameRepository) {
    return new PlayGameUseCase(gameFactory, savedGameRepository);
  }

  @Bean
  public ReplayGame replayGameUseCase(
      GameFactory gameFactory, SavedGameRepository savedGameRepository) {
    return new ReplayGameUseCase(gameFactory, savedGameRepository);
  }
}
