package uk.ac.mmu.assignment26.domain.config;

/** Configuration values for the hit rule variation. */
public enum HitRuleType {
  /** Hits are reported but players may share the same position. */
  IGNORE_HITS,
  /** A player forfeits the move if they would land on another player */
  FORFEIT_ON_HIT
}
