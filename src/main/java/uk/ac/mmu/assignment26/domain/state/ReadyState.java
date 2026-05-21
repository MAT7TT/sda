package uk.ac.mmu.assignment26.domain.state;

import uk.ac.mmu.assignment26.domain.Game;
import uk.ac.mmu.assignment26.domain.events.GameStateChangedEvent;

public class ReadyState implements GameState {
  @Override
  public void start(Game game) {
    GameState nextState = new InPlayState();

    game.publishEvent(new GameStateChangedEvent(getName(), nextState.getName()));

    game.setState(nextState);
  }

  @Override
  public void playTurn(Game game) {
    start(game);
    game.playTurn();
  }

  @Override
  public void finish(Game game) {
    // Game has not started yet.
  }

  @Override
  public String getName() {
    return "Ready";
  }
}
