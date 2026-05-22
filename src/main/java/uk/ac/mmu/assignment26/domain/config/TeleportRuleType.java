package uk.ac.mmu.assignment26.domain.config;

/**
 * Configuration values for the wormhole teleport variation.
 */
public enum TeleportRuleType {
  /** Wormholes exist but have no effect on player movement. */
  IGNORE_WORMHOLES,
  /** Landing on a wormhole endpoint teleports the player to the other end. */
  USE_WORMHOLES
}
