package uk.ac.mmu.assignment26.infrastructure.registry;

import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;
import uk.ac.mmu.assignment26.domain.config.HitRuleType;
import uk.ac.mmu.assignment26.domain.config.TeleportRuleType;
import uk.ac.mmu.assignment26.domain.rules.hit.HitRule;
import uk.ac.mmu.assignment26.domain.rules.movement.MovementRule;
import uk.ac.mmu.assignment26.domain.rules.teleport.TeleportRule;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class RuleRegistry {
    private final Map<EndRuleType, MovementRule> movementRules =
            new EnumMap<>(EndRuleType.class);

    private final Map<HitRuleType, HitRule> hitRules =
            new EnumMap<>(HitRuleType.class);

    private final Map<TeleportRuleType, TeleportRule> teleportRules =
            new EnumMap<>(TeleportRuleType.class);

    public RuleRegistry(
            List<MovementRule> movementRules,
            List<HitRule> hitRules,
            List<TeleportRule> teleportRules
    ) {
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

    public HitRule getHitRule(HitRuleType type) {
        if (type == null) {
            throw new IllegalArgumentException("End rule type must not be null.");
        }

        HitRule rule = hitRules.get(type);

        if (rule == null) {
            throw new IllegalArgumentException("No hit rule registered for " + type);
        }

        return rule;
    }

    public TeleportRule getTeleportRule(TeleportRuleType type) {
        if (type == null) {
            throw new IllegalArgumentException("End rule type must not be null.");
        }

        TeleportRule rule = teleportRules.get(type);

        if (rule == null) {
            throw new IllegalArgumentException("No teleport rule registered for " + type);
        }

        return rule;
    }
}