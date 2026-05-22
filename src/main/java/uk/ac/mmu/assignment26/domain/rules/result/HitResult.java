package uk.ac.mmu.assignment26.domain.rules.result;

/**
 * Value object describing the hit part of a turn.
 *
 * @param hit true if another player was hit
 * @param hitPlayerName the name of the player that was hit
 * @param hitPosition the board position where the hit happened
 * @param from the moving player's position before hit handling
 * @param to the moving player's position after hit handling
 */
public record HitResult(boolean hit, String hitPlayerName, int hitPosition, int from, int to) {
  /**
   * Checks whether handling moved the player back.
   *
   * @return true if the player was moved back after a hit
   */
  public boolean playerMovedBack() {
    return hit && from != to;
  }

  /**
   * Creates a result for a turn where no hit occurred.
   * @param position the player's position
   * @return a no-hit result
   */
  public static HitResult noHit(int position) {
    return new HitResult(false, null, position, position, position);
  }

  /**
   * Creates a result for a hit that does not move the current player.
   *
   * @param hitPlayerName the player that was hit
   * @param position the position where the hit happened
   * @return a hit result with no movement back
   */
  public static HitResult hitIgnored(String hitPlayerName, int position) {
    return new HitResult(true, hitPlayerName, position, position, position);
  }

  /**
   * Creates a result for a hit where the current player moves back.
   *
   * @param hitPlayerName the player that was hit
   * @param from the hit position
   * @param to the position moved back to
   * @return a hit result with movement back
   */
  public static HitResult hitAndMovedBack(String hitPlayerName, int from, int to) {
    return new HitResult(true, hitPlayerName, from, from, to);
  }
}
