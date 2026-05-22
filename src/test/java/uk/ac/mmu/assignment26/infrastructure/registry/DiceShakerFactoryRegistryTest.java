package uk.ac.mmu.assignment26.infrastructure.registry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import uk.ac.mmu.assignment26.domain.config.DiceType;
import uk.ac.mmu.assignment26.domain.dice.DiceShaker;
import uk.ac.mmu.assignment26.infrastructure.dice.DiceShakerFactory;
import uk.ac.mmu.assignment26.infrastructure.dice.DoubleDiceShakerFactory;
import uk.ac.mmu.assignment26.infrastructure.dice.SingleDiceShakerFactory;

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
    void rejectsNullDiceTypeWhenCreatingFixedDice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> createRegistry().createFixedDiceShaker(null, List.of(6))
        );
    }

    @Test
    void rejectsNullDiceTypeWhenCreatingRandomDice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> createRegistry().createDiceShaker(null)
        );
    }

    @Test
    void rejectsNullFactoryList() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new DiceShakerFactoryRegistry(null)
        );
    }

    @Test
    void rejectsEmptyFactoryList() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new DiceShakerFactoryRegistry(List.of())
        );
    }

    @Test
    void rejectsNullFactoryInsideList() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new DiceShakerFactoryRegistry(Arrays.asList(new SingleDiceShakerFactory(), null))
        );
    }

    @Test
    void rejectsDuplicateFactoryType() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new DiceShakerFactoryRegistry(List.of(
                        new SingleDiceShakerFactory(),
                        new SingleDiceShakerFactory()
                ))
        );
    }

    @Test
    void rejectsFactoryWithNullType() {
        DiceShakerFactory factory = new DiceShakerFactory() {
            @Override
            public DiceType getType() {
                return null;
            }

            @Override
            public DiceShaker create() {
                return () -> 1;
            }
        };

        assertThrows(
                IllegalArgumentException.class,
                () -> new DiceShakerFactoryRegistry(List.of(factory))
        );
    }

    private DiceShakerFactoryRegistry createRegistry() {
        return new DiceShakerFactoryRegistry(List.of(
                new SingleDiceShakerFactory(),
                new DoubleDiceShakerFactory()
        ));
    }
}
