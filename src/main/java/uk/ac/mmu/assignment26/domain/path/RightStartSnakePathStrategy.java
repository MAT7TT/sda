package uk.ac.mmu.assignment26.domain.path;

import uk.ac.mmu.assignment26.domain.Board;

import java.util.List;

/**
 * Path strategy for players starting from the lower-right side of the board.
 */
public class RightStartSnakePathStrategy implements PathStrategy {

  /**
   * Builds the lower-right start boustrophedon path.
   *
   * @param board the board to build the path for
   * @return the ordered path positions
   */
  @Override
  public List<Integer> buildPath(Board board) {
    return board.getRightStartSnakePath();
  }
}
