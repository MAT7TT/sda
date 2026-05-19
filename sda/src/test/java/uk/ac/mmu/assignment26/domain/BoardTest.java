package uk.ac.mmu.assignment26.domain;

import org.junit.jupiter.api.Test;

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
    void rejectsNullBlockedPositionsWhenAddingWormhole() {
        Board board = new Board(3, 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> board.addWormhole(2, 8, null)
        );
    }

    @Test
    void rejectsWormholeWithSameEndpoint() {
        Board board = new Board(3, 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> board.addWormhole(2, 2, List.of())
        );
    }

    @Test
    void rejectsWormholeOutsideBoard() {
        Board board = new Board(3, 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> board.addWormhole(2, 10, List.of())
        );
    }

    @Test
    void rejectsWormholeEndpointAlreadyInUse() {
        Board board = new Board(3, 3);
        board.addWormhole(2, 8, List.of());

        assertThrows(
                IllegalArgumentException.class,
                () -> board.addWormhole(2, 6, List.of())
        );
    }

    @Test
    void rejectsWormholeOnBlockedPosition() {
        Board board = new Board(3, 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> board.addWormhole(1, 8, List.of(1, 9))
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
