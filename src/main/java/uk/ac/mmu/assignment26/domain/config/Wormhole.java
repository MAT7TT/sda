package uk.ac.mmu.assignment26.domain.config;

/**
 * Value object representing a pair of connected wormhole positions.
 *
 * @param firstPosition one endpoint of the wormhole
 * @param secondPosition the other endpoint of the wormhole
 * @throws IllegalArgumentException if either position is not positive or both endpoints are the
 *     same
 */
public record Wormhole(int firstPosition, int secondPosition) {
  public Wormhole {
    if (firstPosition <= 0 || secondPosition <= 0) {
      throw new IllegalArgumentException("Wormhole positions must be positive.");
    }

    if (firstPosition == secondPosition) {
      throw new IllegalArgumentException("Wormhole endpoints must be different.");
    }
  }
}
