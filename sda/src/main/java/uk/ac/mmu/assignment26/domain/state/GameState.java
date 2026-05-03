package uk.ac.mmu.assignment26.domain.state;

import uk.ac.mmu.assignment26.domain.Game;

public interface GameState {
     void start(Game game);
     void playTurn(Game game);
     void finish(Game game);
     String getName();
}
