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
        for (MovementRule rule : movementRules) {
            this.movementRules.put(rule.getType(), rule);
        }

        for (HitRule rule : hitRules) {
            this.hitRules.put(rule.getType(), rule);
        }

        for (TeleportRule rule : teleportRules) {
            this.teleportRules.put(rule.getType(), rule);
        }
    }

    public MovementRule getMovementRule(EndRuleType type) {
        MovementRule rule = movementRules.get(type);

        if (rule == null) {
            throw new IllegalArgumentException("No movement rule registered for " + type);
        }

        return rule;
    }

    public HitRule getHitRule(HitRuleType type) {
        HitRule rule = hitRules.get(type);

        if (rule == null) {
            throw new IllegalArgumentException("No hit rule registered for " + type);
        }

        return rule;
    }

    public TeleportRule getTeleportRule(TeleportRuleType type) {
        TeleportRule rule = teleportRules.get(type);

        if (rule == null) {
            throw new IllegalArgumentException("No teleport rule registered for " + type);
        }

        return rule;
    }
}