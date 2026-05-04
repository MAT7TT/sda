package uk.ac.mmu.assignment26.domain.config;

public enum TeleportRuleType {
    IGNORE_WORMHOLES("Wormholes are ignored"),
    USE_WORMHOLES("Player is teleported to the other end of the wormhole");

    private final String description;

    TeleportRuleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}