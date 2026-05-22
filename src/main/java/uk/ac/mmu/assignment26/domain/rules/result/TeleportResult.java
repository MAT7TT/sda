package uk.ac.mmu.assignment26.domain.rules.result;

/**
 * Value object describing the teleport part of a turn.
 *
 * @param teleported true if the player was teleported
 * @param from the position before teleporting
 * @param to the position after teleporting
 */
public record TeleportResult(boolean teleported, int from, int to) {
  /**
   * Creates a result for a turn where no teleport occurred.
   *
   * @param position the player's unchanged position
   * @return a not-teleported result
   */
  public static TeleportResult notTeleported(int position) {
    return new TeleportResult(false, position, position);
  }

  /**
   * Creates a result for a successful teleport
   *
   * @param from the wormhole entry position
   * @param to the wormhole exit position
   * @return a teleported result
   */
  public static TeleportResult teleported(int from, int to) {
    return new TeleportResult(true, from, to);
  }
}
