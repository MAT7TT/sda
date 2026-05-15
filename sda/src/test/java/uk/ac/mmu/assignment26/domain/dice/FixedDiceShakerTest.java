package uk.ac.mmu.assignment26.domain.dice;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FixedDiceShakerTest {

    @Test
    void returnsFixedRollsInOrder() {
        FixedDiceShaker diceShaker = new FixedDiceShaker(List.of(3, 6, 12));

        assertEquals(3, diceShaker.shake());
        assertEquals(6, diceShaker.shake());
        assertEquals(12, diceShaker.shake());
    }

    @Test
    void rejectsNullRollSequence() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new FixedDiceShaker((List<Integer>) null)
        );
    }

    @Test
    void rejectsEmptyRollSequence() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new FixedDiceShaker(List.of())
        );
    }

    @Test
    void rejectsNullRollInsideSequence() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new FixedDiceShaker(Arrays.asList(1, null, 5))
        );
    }

    @Test
    void rejectsRollBelowMinimum() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new FixedDiceShaker(List.of(0))
        );
    }

    @Test
    void rejectsRollAboveMaximum() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new FixedDiceShaker(List.of(13))
        );
    }

    @Test
    void throwsWhenNoMoreFixedRollsAreAvailable() {
        FixedDiceShaker diceShaker = new FixedDiceShaker(List.of(4));

        assertEquals(4, diceShaker.shake());

        assertThrows(
                IllegalStateException.class,
                diceShaker::shake
        );
    }

    @Test
    void copiesInputRollsSoExternalChangesDoNotAffectSequence() {
        List<Integer> rolls = new ArrayList<>(List.of(3));
        FixedDiceShaker diceShaker = new FixedDiceShaker(rolls);

        rolls.set(0, 12);

        assertEquals(3, diceShaker.shake());
    }
}
