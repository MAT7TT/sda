package uk.ac.mmu.assignment26.domain.state;

import uk.ac.mmu.assignment26.domain.Game;
import uk.ac.mmu.assignment26.domain.events.GameStateChangedEvent;

/**
 * State used while a game is being played.
 *
 * <p>Turn requests are delegated to the game context.
 * Finishing the game moves the context into {@link GameOverState}</p>
 */
public class InPlayState implements GameState {
  @Override
  public void start(Game game) {
    // Already in play.
  }

  @Override
  public void playTurn(Game game) {
    game.executeTurn();
  }

  @Override
  public void finish(Game game) {
    GameState nextState = new GameOverState();

    game.publishEvent(new GameStateChangedEvent(getName(), nextState.getName()));

    game.setState(nextState);
  }

  @Override
  public String getName() {
    return "InPlay";
  }
}
