package uk.ac.mmu.assignment26.domain.dice;

public class RandomDoubleDiceShaker implements DiceShaker {
  private final DiceShaker firstDie = new RandomSingleDiceShaker();
  private final DiceShaker secondDie = new RandomSingleDiceShaker();

  @Override
  public int shake() {
    return firstDie.shake() + secondDie.shake();
  }
}
