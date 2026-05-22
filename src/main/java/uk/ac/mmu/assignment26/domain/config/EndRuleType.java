package uk.ac.mmu.assignment26.domain.config;

/**
 * Configuration values for the end-position rule variation.
 */
public enum EndRuleType {
  /** A player can win by landing on or overshooting the end position. */
  STANDARD,
  /** A player must land exactly on the end position or bounce back. */
  EXACT_END_BOUNCE
}
