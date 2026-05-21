package uk.ac.mmu.assignment26.domain.config;

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
