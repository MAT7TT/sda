package uk.ac.mmu.assignment26.domain.rules.hit;

import org.junit.jupiter.api.Test;
import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.path.LeftStartSnakePathStrategy;
import uk.ac.mmu.assignment26.domain.path.RightStartSnakePathStrategy;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HitRuleTest {

    @Test
    void ignoreHitRuleRejectsNullCurrentPlayer() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new IgnoreHitRule().apply(null, 0, List.of(createRedPlayer()))
        );
    }

    @Test
    void ignoreHitRuleRejectsNullPlayersList() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new IgnoreHitRule().apply(createRedPlayer(), 0, null)
        );
    }

    @Test
    void ignoreHitRuleRejectsEmptyPlayersList() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new IgnoreHitRule().apply(createRedPlayer(), 0, List.of())
        );
    }

    @Test
    void ignoreHitRuleRejectsPlayersListContainingNull() {
        Player player = createRedPlayer();

        assertThrows(
                IllegalArgumentException.class,
                () -> new IgnoreHitRule().apply(player, 0, Arrays.asList(player, null))
        );
    }

    @Test
    void forfeitOnHitRuleRejectsNullCurrentPlayer() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ForfeitOnHitRule().apply(null, 0, List.of(createRedPlayer()))
        );
    }

    @Test
    void forfeitOnHitRuleRejectsNullPlayersList() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ForfeitOnHitRule().apply(createRedPlayer(), 0, null)
        );
    }

    @Test
    void forfeitOnHitRuleRejectsEmptyPlayersList() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ForfeitOnHitRule().apply(createRedPlayer(), 0, List.of())
        );
    }

    @Test
    void forfeitOnHitRuleRejectsInvalidStartTurnPathIndex() {
        Player player = createRedPlayer();

        assertThrows(
                IllegalArgumentException.class,
                () -> new ForfeitOnHitRule().apply(player, player.getPathLength(), List.of(player))
        );
    }

    @Test
    void forfeitOnHitRuleRejectsPlayersListContainingNull() {
        Player player = createRedPlayer();

        assertThrows(
                IllegalArgumentException.class,
                () -> new ForfeitOnHitRule().apply(player, 0, Arrays.asList(player, null))
        );
    }

    @Test
    void forfeitOnHitRuleMovesCurrentPlayerBackToStartTurnPosition() {
        Board board = new Board(3, 3);
        Player red = new Player("Red", board, new LeftStartSnakePathStrategy());
        Player blue = new Player("Blue", board, new RightStartSnakePathStrategy());

        red.setPathIndex(2);
        int startTurnPathIndex = red.getPathIndex();

        red.setPathIndex(4);
        blue.setPathIndex(4);

        new ForfeitOnHitRule().apply(red, startTurnPathIndex, List.of(red, blue));

        assertEquals(startTurnPathIndex, red.getPathIndex());
    }

    private Player createRedPlayer() {
        return new Player("Red", new Board(3, 3), new LeftStartSnakePathStrategy());
    }
}
