package uk.ac.mmu.assignment26.domain.config;

import uk.ac.mmu.assignment26.domain.config.DiceType;
import uk.ac.mmu.assignment26.domain.config.EndRuleType;
import uk.ac.mmu.assignment26.domain.config.HitRuleType;
import uk.ac.mmu.assignment26.domain.config.TeleportRuleType;

import java.util.List;

public record GameConfiguration(
        int rows,
        int columns,
        int numberOfPlayers,
        DiceType diceType,
        EndRuleType endRuleType,
        HitRuleType hitRuleType,
        TeleportRuleType teleportRuleType,
        List<Wormhole> wormholes
) {}