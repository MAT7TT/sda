package uk.ac.mmu.assignment26.domain.dice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import uk.ac.mmu.assignment26.domain.config.DiceType;

class FixedDiceShakerTest {

  @Test
  void returnsFixedRollsInOrder() {
    FixedDiceShaker diceShaker = new FixedDiceShaker(List.of(3, 6, 12), DiceType.DOUBLE);

    assertEquals(3, diceShaker.shake());
    assertEquals(6, diceShaker.shake());
    assertEquals(12, diceShaker.shake());
  }

  @Test
  void rejectsNullDiceType() {
    assertThrows(IllegalArgumentException.class, () -> new FixedDiceShaker(List.of(3), null));
  }

  @Test
  void rejectsNullRollSequence() {
    assertThrows(IllegalArgumentException.class, () -> new FixedDiceShaker(null, DiceType.DOUBLE));
  }

  @Test
  void rejectsEmptyRollSequence() {
    assertThrows(
        IllegalArgumentException.class, () -> new FixedDiceShaker(List.of(), DiceType.DOUBLE));
  }

  @Test
  void rejectsNullRollInsideSequence() {
    assertThrows(
        IllegalArgumentException.class,
        () -> new FixedDiceShaker(Arrays.asList(1, null, 5), DiceType.DOUBLE));
  }

  @Test
  void singleDiceAcceptsRollsBetweenOneAndSix() {
    FixedDiceShaker diceShaker = new FixedDiceShaker(List.of(1, 6), DiceType.SINGLE);

    assertEquals(1, diceShaker.shake());
    assertEquals(6, diceShaker.shake());
  }

  @Test
  void singleDiceRejectsRollAboveSix() {
    assertThrows(
        IllegalArgumentException.class, () -> new FixedDiceShaker(List.of(7), DiceType.SINGLE));
  }

  @Test
  void doubleDiceAcceptsRollsBetweenTwoAndTwelve() {
    FixedDiceShaker diceShaker = new FixedDiceShaker(List.of(2, 12), DiceType.DOUBLE);

    assertEquals(2, diceShaker.shake());
    assertEquals(12, diceShaker.shake());
  }

  @Test
  void doubleDiceRejectsRollBelowTwo() {
    assertThrows(
        IllegalArgumentException.class, () -> new FixedDiceShaker(List.of(1), DiceType.DOUBLE));
  }

  @Test
  void doubleDiceRejectsRollAboveTwelve() {
    assertThrows(
        IllegalArgumentException.class, () -> new FixedDiceShaker(List.of(13), DiceType.DOUBLE));
  }

  @Test
  void throwsWhenNoMoreFixedRollsAreAvailable() {
    FixedDiceShaker diceShaker = new FixedDiceShaker(List.of(4), DiceType.SINGLE);

    assertEquals(4, diceShaker.shake());

    assertThrows(IllegalStateException.class, diceShaker::shake);
  }

  @Test
  void copiesInputRollsSoExternalChangesDoNotAffectSequence() {
    List<Integer> rolls = new ArrayList<>(List.of(3));
    FixedDiceShaker diceShaker = new FixedDiceShaker(rolls, DiceType.SINGLE);

    rolls.set(0, 6);

    assertEquals(3, diceShaker.shake());
  }
}
