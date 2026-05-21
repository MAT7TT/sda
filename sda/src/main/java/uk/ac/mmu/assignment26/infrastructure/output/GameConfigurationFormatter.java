package uk.ac.mmu.assignment26.infrastructure.output;

import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.config.DiceType;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;
import uk.ac.mmu.assignment26.domain.config.GameConfiguration;
import uk.ac.mmu.assignment26.domain.config.HitRuleType;
import uk.ac.mmu.assignment26.domain.config.TeleportRuleType;

import java.util.stream.Collectors;

@Component
public class GameConfigurationFormatter {

  public String formatRules(GameConfiguration configuration) {
    return "Board: "
        + formatBoard(configuration)
        + ", Players: "
        + configuration.numberOfPlayers()
        + ", Exact End: "
        + formatEndRule(configuration.endRuleType())
        + ", Hit: "
        + formatHitRule(configuration.hitRuleType())
        + ", Teleport: "
        + formatTeleportRule(configuration.teleportRuleType())
        + ", Wormholes: "
        + formatWormholes(configuration);
  }

  public String formatRandomDice(DiceType diceType) {
    if (diceType == DiceType.SINGLE) {
      return "Single random 6 sided die";
    }

    return "Two random 6 sided dice";
  }

  private String formatBoard(GameConfiguration configuration) {
    if (configuration.rows() == 5 && configuration.columns() == 5) {
      return "Small";
    }

    if (configuration.rows() == 6 && configuration.columns() == 6) {
      return "Large";
    }

    return configuration.rows() + "x" + configuration.columns();
  }

  private String formatWormholes(GameConfiguration configuration) {
    if (configuration.wormholes().isEmpty()) {
      return "None";
    }

    return configuration.wormholes().stream()
        .map(wormhole -> wormhole.firstPosition() + " and " + wormhole.secondPosition())
        .collect(Collectors.joining(", "));
  }

  private String formatEndRule(EndRuleType endRuleType) {
    return switch (endRuleType) {
      case STANDARD -> "Player can land on or overshoot the END position to win";
      case EXACT_END_BOUNCE ->
          "Player must land exactly on the END position to win else the player bounces back";
    };
  }

  private String formatHitRule(HitRuleType hitRuleType) {
    return switch (hitRuleType) {
      case IGNORE_HITS -> "HITS are ignored, multiple players can occupy the same position";
      case FORFEIT_ON_HIT -> "Player's turn is forfeit if the player would HIT another player";
    };
  }

  private String formatTeleportRule(TeleportRuleType teleportRuleType) {
    return switch (teleportRuleType) {
      case IGNORE_WORMHOLES -> "Wormholes are ignored";
      case USE_WORMHOLES -> "Player is teleported to the other end of the wormhole";
    };
  }
}
