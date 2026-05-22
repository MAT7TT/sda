package uk.ac.mmu.assignment26.domain.rules.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.path.LeftStartSnakePathStrategy;

class MovementRuleTest {

  @Test
  void standardRuleRejectsNullPlayer() {
    assertThrows(IllegalArgumentException.class, () -> new StandardEndMovementRule().move(null, 1));
  }

  @Test
  void standardRuleRejectsZeroRoll() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new StandardEndMovementRule().move(createPlayer(), 0));
  }

  @Test
  void standardRuleRejectsNegativeRoll() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new StandardEndMovementRule().move(createPlayer(), -1));
  }

  @Test
  void standardRuleMovesPlayerForward() {
    Player player = createPlayer();

    new StandardEndMovementRule().move(player, 3);

    assertEquals(3, player.getPathIndex());
  }

  @Test
  void standardRuleStopsAtEndWhenPlayerOvershoots() {
    Player player = createPlayer();

    new StandardEndMovementRule().move(player, 20);

    assertEquals(player.getPathLength() - 1, player.getPathIndex());
  }

  @Test
  void exactEndBounceRuleRejectsNullPlayer() {
    assertThrows(
        IllegalArgumentException.class, () -> new ExactEndBounceMovementRule().move(null, 1));
  }

  @Test
  void exactEndBounceRuleRejectsZeroRoll() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new ExactEndBounceMovementRule().move(createPlayer(), 0));
  }

  @Test
  void exactEndBounceRuleMovesPlayerForward() {
    Player player = createPlayer();

    new ExactEndBounceMovementRule().move(player, 3);

    assertEquals(3, player.getPathIndex());
  }

  @Test
  void exactEndBounceRuleBouncesBackWhenPlayerOvershoots() {
    Player player = createPlayer();
    int startIndex = player.getPathLength() - 2;
    player.setPathIndex(startIndex);

    new ExactEndBounceMovementRule().move(player, 3);

    assertEquals(startIndex - 1, player.getPathIndex());
  }

  private Player createPlayer() {
    return new Player("Red", new Board(3, 3), new LeftStartSnakePathStrategy());
  }
}
