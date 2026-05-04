package uk.ac.mmu.assignment26.domain.config;

public enum HitRuleType {
    IGNORE_HITS("HITS are ignored, multiple players can occupy the same position"),
    FORFEIT_ON_HIT("Player's turn is forfeit if the player would HIT another player");

    private final String description;

    HitRuleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}