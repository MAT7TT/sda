package uk.ac.mmu.assignment26.domain.rules.teleport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;
import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.Wormhole;
import uk.ac.mmu.assignment26.domain.path.LeftStartSnakePathStrategy;

class TeleportRuleTest {

  @Test
  void ignoreTeleportRuleRejectsNullPlayer() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new IgnoreTeleportRule().apply(new Board(3, 3), null));
  }

  @Test
  void wormholeTeleportRuleRejectsNullBoard() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new WormholeTeleportRule().apply(null, createPlayer(new Board(3, 3))));
  }

  @Test
  void wormholeTeleportRuleRejectsNullPlayer() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new WormholeTeleportRule().apply(new Board(3, 3), null));
  }

  @Test
  void wormholeTeleportRuleMovesPlayerToWormholeExit() {
    Board board = new Board(3, 3);
    board.addWormhole(new Wormhole(2, 8), List.of());

    Player player = createPlayer(board);
    player.setPathIndex(player.findPathIndexOfPosition(2));

    new WormholeTeleportRule().apply(board, player);

    assertEquals(8, player.getCurrentPosition());
  }

  private Player createPlayer(Board board) {
    return new Player("Red", board, new LeftStartSnakePathStrategy());
  }
}
