package uk.ac.mmu.assignment26.domain;

import org.junit.jupiter.api.Test;
import uk.ac.mmu.assignment26.domain.config.Wormhole;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BoardTest {

    @Test
    void rejectsRowsLessThanOne() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Board(0, 3)
        );
    }

    @Test
    void rejectsColumnsLessThanOne() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Board(3, 0)
        );
    }

    @Test
    void rejectsNullWormhole() {
        Board board = new Board(3, 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> board.addWormhole(null, List.of())
        );
    }

    @Test
    void rejectsNullBlockedPositionsWhenAddingWormhole() {
        Board board = new Board(3, 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> board.addWormhole(new Wormhole(2, 8), null)
        );
    }

    @Test
    void rejectsWormholeOutsideBoard() {
        Board board = new Board(3, 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> board.addWormhole(new Wormhole(2, 10), List.of())
        );
    }

    @Test
    void rejectsWormholeEndpointAlreadyInUse() {
        Board board = new Board(3, 3);
        board.addWormhole(new Wormhole(2, 8), List.of());

        assertThrows(
                IllegalArgumentException.class,
                () -> board.addWormhole(new Wormhole(2, 6), List.of())
        );
    }

    @Test
    void rejectsWormholeOnBlockedPosition() {
        Board board = new Board(3, 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> board.addWormhole(new Wormhole(1, 8), List.of(1, 9))
        );
    }

    @Test
    void rejectsGettingExitForPositionWithoutWormhole() {
        Board board = new Board(3, 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> board.getWormholeExit(5)
        );
    }
}