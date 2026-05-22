package uk.ac.mmu.assignment26.domain.state;

import uk.ac.mmu.assignment26.domain.Game;
import uk.ac.mmu.assignment26.domain.events.GameOverAttemptedEvent;

/**
 * State used after a winner has been found.
 *
 * <p>Further attempts to start, play or finish the game publish a game-over attempted event.</p>
 */
public class GameOverState implements GameState {
  @Override
  public void start(Game game) {
    game.publishEvent(new GameOverAttemptedEvent());
  }

  @Override
  public void playTurn(Game game) {
    game.publishEvent(new GameOverAttemptedEvent());
  }

  @Override
  public void finish(Game game) {
    game.publishEvent(new GameOverAttemptedEvent());
  }

  @Override
  public String getName() {
    return "GameOver";
  }
}
