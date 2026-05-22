package uk.ac.mmu.assignment26.domain.state;

import uk.ac.mmu.assignment26.domain.Game;

/**
 * State interface for the game state machine
 *
 * <p>Concrete states define how the game responds to lifecycle operations
 * while it is ready, in play or game over.</p>
 */
public interface GameState {
  /**
   * Handles a request to start the game.
   *
   * @param game the game context
   */
  void start(Game game);

  /**
   * Handles a request to play one turn.
   *
   * @param game the game context
   */
  void playTurn(Game game);

  /**
   * Handles a request to finish the game.
   *
   * @param game the game context
   */
  void finish(Game game);

  /**
   * Returns the display name of the state.
   *
   * @return the state name
   */
  String getName();
}
