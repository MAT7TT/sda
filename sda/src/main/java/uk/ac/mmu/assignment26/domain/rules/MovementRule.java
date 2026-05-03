package uk.ac.mmu.assignment26.domain.rules;


import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;

public interface MovementRule {
    EndRuleType getType();

    void move(Player player, int roll);
}
