package uk.ac.mmu.assignment26.domain.config;

public enum EndRuleType {
    STANDARD("Player can land on or overshoot the END position to win"),
    EXACT_END_BOUNCE("Player must land exactly on the END position to win else the player bounces back");

    private final String description;

    EndRuleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}