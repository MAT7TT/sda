package uk.ac.mmu.assignment26.usecase;

import org.junit.jupiter.api.Test;
import uk.ac.mmu.assignment26.domain.config.DiceType;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;
import uk.ac.mmu.assignment26.domain.config.GameConfiguration;
import uk.ac.mmu.assignment26.domain.config.HitRuleType;
import uk.ac.mmu.assignment26.domain.config.TeleportRuleType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SavedGameTest {

    @Test
    void rejectsNullConfiguration() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new SavedGame(null, List.of(6))
        );
    }

    @Test
    void rejectsNullDiceRolls() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new SavedGame(createConfiguration(DiceType.SINGLE), null)
        );
    }

    @Test
    void rejectsEmptyDiceRolls() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new SavedGame(createConfiguration(DiceType.SINGLE), List.of())
        );
    }

    @Test
    void rejectsNullDiceRollInsideSequence() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new SavedGame(createConfiguration(DiceType.SINGLE), Arrays.asList(1, null, 3))
        );
    }

    @Test
    void rejectsSingleDiceRollAboveSix() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new SavedGame(createConfiguration(DiceType.SINGLE), List.of(7))
        );
    }

    @Test
    void rejectsDoubleDiceRollBelowTwo() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new SavedGame(createConfiguration(DiceType.DOUBLE), List.of(1))
        );
    }

    @Test
    void copiesDiceRollsSoExternalChangesDoNotAffectSavedGame() {
        List<Integer> diceRolls = new ArrayList<>(List.of(3));
        SavedGame savedGame = new SavedGame(createConfiguration(DiceType.SINGLE), diceRolls);

        diceRolls.set(0, 6);

        assertEquals(List.of(3), savedGame.diceRolls());
    }

    private GameConfiguration createConfiguration(DiceType diceType) {
        return new GameConfiguration(
                5,
                5,
                2,
                diceType,
                EndRuleType.STANDARD,
                HitRuleType.IGNORE_HITS,
                TeleportRuleType.IGNORE_WORMHOLES,
                List.of()
        );
    }
}