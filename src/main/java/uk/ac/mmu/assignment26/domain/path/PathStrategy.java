package uk.ac.mmu.assignment26.domain.path;

import uk.ac.mmu.assignment26.domain.Board;

import java.util.List;

/**
 * Strategy interface for building player paths
 *
 * <p>Concrete path strategies create a boustrophedon route from a particular
 * starting side or corner of the board.</p>
 */
public interface PathStrategy {
  /**
   * Builds the ordered list of board positions for a player.
   *
   *@param board the board to build the path for
   * @return the ordered board positions from home to end
   * @throws IllegalArgumentException if the board breaks the path strategy contract
   */
  List<Integer> buildPath(Board board);
}
