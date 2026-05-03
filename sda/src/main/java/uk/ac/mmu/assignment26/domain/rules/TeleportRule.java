package uk.ac.mmu.assignment26.domain.rules;


import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.TeleportRuleType;

public interface TeleportRule {
    TeleportRuleType getType();

    void apply(Board board, Player player);
}
