package uk.ac.mmu.assignment26.usecase;

import uk.ac.mmu.assignment26.domain.config.GameConfiguration;

import java.util.List;

public record SavedGame(GameConfiguration configuration, List<Integer> diceRolls) {
  public SavedGame {
    if (configuration == null) {
      throw new IllegalArgumentException("Game configuration must not be null.");
    }

    if (diceRolls == null || diceRolls.isEmpty()) {
      throw new IllegalArgumentException("Dice rolls must not be empty.");
    }

    for (Integer diceRoll : diceRolls) {
      if (diceRoll == null) {
        throw new IllegalArgumentException("Dice rolls must not contain null.");
      }

      if (!configuration.diceType().acceptsRoll(diceRoll)) {
        throw new IllegalArgumentException("Dice rolls must match the configured dice type.");
      }
    }

    diceRolls = List.copyOf(diceRolls);
  }
}
