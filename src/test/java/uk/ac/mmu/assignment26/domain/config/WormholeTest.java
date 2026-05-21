package uk.ac.mmu.assignment26.domain.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WormholeTest {

    @Test
    void storesWormholePositions() {
        Wormhole wormhole = new Wormhole(4, 9);

        assertEquals(4, wormhole.firstPosition());
        assertEquals(9, wormhole.secondPosition());
    }

    @Test
    void rejectsFirstPositionLessThanOne() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Wormhole(0, 9)
        );
    }

    @Test
    void rejectsSecondPositionLessThanOne() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Wormhole(4, 0)
        );
    }

    @Test
    void rejectsSameEndpoint() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Wormhole(4, 4)
        );
    }
}
