package uk.ac.mmu.assignment26.infrastructure.registry;

import org.junit.jupiter.api.Test;
import uk.ac.mmu.assignment26.domain.config.DiceType;
import uk.ac.mmu.assignment26.domain.dice.DiceShaker;
import uk.ac.mmu.assignment26.infrastructure.dice.DoubleDiceShakerFactory;
import uk.ac.mmu.assignment26.infrastructure.dice.FixedDiceShakerFactory;
import uk.ac.mmu.assignment26.infrastructure.dice.SingleDiceShakerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiceShakerFactoryRegistryTest {

    @Test
    void createsFixedSingleDiceWhenRollsMatchSingleDieRange() {
        DiceShaker diceShaker = createRegistry()
                .createFixedDiceShaker(DiceType.SINGLE, List.of(6));

        assertEquals(6, diceShaker.shake());
    }

    @Test
    void rejectsFixedSingleDiceRollAboveSix() {
        assertThrows(
                IllegalArgumentException.class,
                () -> createRegistry().createFixedDiceShaker(DiceType.SINGLE, List.of(7))
        );
    }

    @Test
    void createsFixedDoubleDiceWhenRollsMatchDoubleDiceRange() {
        DiceShaker diceShaker = createRegistry()
                .createFixedDiceShaker(DiceType.DOUBLE, List.of(2, 7, 12));

        assertEquals(2, diceShaker.shake());
        assertEquals(7, diceShaker.shake());
        assertEquals(12, diceShaker.shake());
    }

    @Test
    void rejectsFixedDoubleDiceRollBelowTwo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> createRegistry().createFixedDiceShaker(DiceType.DOUBLE, List.of(1))
        );
    }

    @Test
    void rejectsFixedDiceTypeAsGameDiceVariation() {
        assertThrows(
                IllegalArgumentException.class,
                () -> createRegistry().createFixedDiceShaker(DiceType.FIXED, List.of(6))
        );
    }

    private DiceShakerFactoryRegistry createRegistry() {
        return new DiceShakerFactoryRegistry(List.of(
                new SingleDiceShakerFactory(),
                new DoubleDiceShakerFactory(),
                new FixedDiceShakerFactory()
        ));
    }
}
