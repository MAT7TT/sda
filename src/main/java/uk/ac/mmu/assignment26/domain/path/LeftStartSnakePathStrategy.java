package uk.ac.mmu.assignment26.domain.path;

import java.util.List;
import uk.ac.mmu.assignment26.domain.Board;

/** Path strategy for players starting from the lower-left side of the board. */
public class LeftStartSnakePathStrategy implements PathStrategy {

  /**
   * Builds the lower-left start boustrophedon path
   *
   * @param board the board to build the path for
   * @return the ordered path positions
   */
  @Override
  public List<Integer> buildPath(Board board) {
    return board.getLeftStartSnakePath();
  }
}
