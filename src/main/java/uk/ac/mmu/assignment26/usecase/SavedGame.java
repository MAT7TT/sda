package uk.ac.mmu.assignment26.usecase;

import uk.ac.mmu.assignment26.domain.config.GameConfiguration;

import java.util.List;

/**
 * Value object containing the data required to replay a completed game.
 *
 * <p>Replay stores the game configuration and dice rolls rather than storing
 * previous console output. This allows the replay use case to rebuild the game and execute
 * the game logic again.</p>
 *
 * @param configuration the original game configuration
 * @param diceRolls the dice rolls used during the game
 * @throws IllegalArgumentException if the configuration or dice rolls are invalid
 */
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
