package uk.ac.mmu.assignment26.infrastructure.registry;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;
import uk.ac.mmu.assignment26.domain.config.HitRuleType;
import uk.ac.mmu.assignment26.domain.config.TeleportRuleType;
import uk.ac.mmu.assignment26.domain.rules.hit.HitRule;
import uk.ac.mmu.assignment26.domain.rules.movement.MovementRule;
import uk.ac.mmu.assignment26.domain.rules.teleport.TeleportRule;

/**
 * Registry for configured rule strategies.
 *
 * <p>Spring supplies the concrete rule implementations, and this registry maps each rule type to
 * the matching concrete strategy.</p>
 */
@Component
public class RuleRegistry {
  private final Map<EndRuleType, MovementRule> movementRules = new EnumMap<>(EndRuleType.class);

  private final Map<HitRuleType, HitRule> hitRules = new EnumMap<>(HitRuleType.class);

  private final Map<TeleportRuleType, TeleportRule> teleportRules =
      new EnumMap<>(TeleportRuleType.class);

  /**
   * Create a rule registry from the available concrete strategies.
   *
   * @param movementRules movement rule strategies
   * @param hitRules hit rule strategies
   * @param teleportRules teleport rule strategies
   * @throws IllegalArgumentException if a rule list is empty, contains null,
   * or contains duplicate rule types
   */
  public RuleRegistry(
      List<MovementRule> movementRules, List<HitRule> hitRules, List<TeleportRule> teleportRules) {
    validateRuleList(movementRules, "Movement");
    validateRuleList(hitRules, "Hit");
    validateRuleList(teleportRules, "Teleport");

    for (MovementRule rule : movementRules) {
      EndRuleType type = rule.getType();

      if (type == null) {
        throw new IllegalArgumentException("Movement rule type must not be null.");
      }

      if (this.movementRules.containsKey(type)) {
        throw new IllegalArgumentException("Duplicate movement rule for " + type + ".");
      }

      this.movementRules.put(type, rule);
    }

    for (HitRule rule : hitRules) {
      HitRuleType type = rule.getType();

      if (type == null) {
        throw new IllegalArgumentException("Hit rule type must not be null.");
      }

      if (this.hitRules.containsKey(type)) {
        throw new IllegalArgumentException("Duplicate hit rule for " + type + ".");
      }

      this.hitRules.put(type, rule);
    }

    for (TeleportRule rule : teleportRules) {
      TeleportRuleType type = rule.getType();

      if (type == null) {
        throw new IllegalArgumentException("Teleport rule type must not be null.");
      }

      if (this.teleportRules.containsKey(type)) {
        throw new IllegalArgumentException("Duplicate teleport rule for " + type + ".");
      }

      this.teleportRules.put(type, rule);
    }
  }

  private <T> void validateRuleList(List<T> rules, String ruleName) {
    if (rules == null || rules.isEmpty()) {
      throw new IllegalArgumentException(ruleName + " rules must not be empty.");
    }

    for (T rule : rules) {
      if (rule == null) {
        throw new IllegalArgumentException(ruleName + " rules must not contain null");
      }
    }
  }

  /**
   * Returns the movement rule for the supplied type.
   *
   * @param type the configured end rule type
   * @return the matching movement rule
   * @throws IllegalArgumentException if the type is null or unregistered
   */
  public MovementRule getMovementRule(EndRuleType type) {
    if (type == null) {
      throw new IllegalArgumentException("End rule type must not be null.");
    }

    MovementRule rule = movementRules.get(type);

    if (rule == null) {
      throw new IllegalArgumentException("No movement rule registered for " + type);
    }

    return rule;
  }

  /**
   * Returns the hit rule for the supplied type.
   *
   * @param type the configured hit rule type
   * @return the matching hit rule
   * @throws IllegalArgumentException if the type is null or unregistered
   */
  public HitRule getHitRule(HitRuleType type) {
    if (type == null) {
      throw new IllegalArgumentException("Hit rule type must not be null.");
    }

    HitRule rule = hitRules.get(type);

    if (rule == null) {
      throw new IllegalArgumentException("No hit rule registered for " + type);
    }

    return rule;
  }

  /**
   * Returns the teleport rule for the supplied type.
   *
   * @param type the configured teleport rule type
   * @return the matching teleport rule
   * @throws IllegalArgumentException if the type is null or unregistered
   */
  public TeleportRule getTeleportRule(TeleportRuleType type) {
    if (type == null) {
      throw new IllegalArgumentException("Teleport rule type must not be null.");
    }

    TeleportRule rule = teleportRules.get(type);

    if (rule == null) {
      throw new IllegalArgumentException("No teleport rule registered for " + type);
    }

    return rule;
  }
}
