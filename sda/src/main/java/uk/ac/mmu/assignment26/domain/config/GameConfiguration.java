package uk.ac.mmu.assignment26.domain.config;

import java.util.List;
import java.util.stream.Collectors;

public record GameConfiguration(
        int rows,
        int columns,
        int numberOfPlayers,
        DiceType diceType,
        EndRuleType endRuleType,
        HitRuleType hitRuleType,
        TeleportRuleType teleportRuleType,
        List<Wormhole> wormholes
) {
    public GameConfiguration {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Board rows and columns must be positive.");
        }

        if (numberOfPlayers != 2 && numberOfPlayers != 4) {
            throw new IllegalArgumentException("Number of players must be 2 or 4.");
        }

        if (diceType == null) {
            throw new IllegalArgumentException("Dice type must not be null.");
        }

        if (endRuleType == null) {
            throw new IllegalArgumentException("End rule type must not be null.");
        }

        if (hitRuleType == null) {
            throw new IllegalArgumentException("Hit rule type must not be null.");
        }

        if (teleportRuleType == null) {
            throw new IllegalArgumentException("Teleport rule type must not be null.");
        }

        if (wormholes == null) {
            wormholes = List.of();
        } else {
            wormholes = List.copyOf(wormholes);
        }
    }

    public String getBoardDescription() {
        if (rows == 5 && columns == 5) {
            return "Small";
        }

        if (rows == 6 && columns == 6) {
            return "Large";
        }

        return rows + "x" + columns;
    }

    public String getWormholeDescription() {
        if (wormholes.isEmpty()) {
            return "None";
        }

        return wormholes.stream()
                .map(wormhole -> wormhole.firstPosition() + " and " + wormhole.secondPosition())
                .collect(Collectors.joining(", "));
    }

    public String getRuleDescription() {
        return "Board: " + getBoardDescription()
                + ", Players: " + numberOfPlayers
                + ", Exact End: " + endRuleType.getDescription()
                + ", Hit: " + hitRuleType.getDescription()
                + ", Teleport: " + teleportRuleType.getDescription()
                + ", Wormholes: " + getWormholeDescription();
    }
}