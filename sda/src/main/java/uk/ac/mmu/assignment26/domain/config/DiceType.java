package uk.ac.mmu.assignment26.domain.config;

public enum DiceType {
    SINGLE("Single random 6 sided die"),
    DOUBLE("Two random 6 sided dice"),
    FIXED("Fixed sequence of dice rolls");

    private final String description;

    DiceType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}