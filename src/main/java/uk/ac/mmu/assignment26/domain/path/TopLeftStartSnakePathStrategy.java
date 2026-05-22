package uk.ac.mmu.assignment26.domain.path;

import java.util.List;
import uk.ac.mmu.assignment26.domain.Board;

/**
 * Path strategy for players starting from the top-left corner.
 *
 * <p>The strategy chooses the correct base path for odd or even row counts, then reverses it so the
 * player starts at the top-left corner
 */
public class TopLeftStartSnakePathStrategy implements PathStrategy {
  /**
   * Builds a top-left start boustrophedon path.
   *
   * @param board the board to build the path for
   * @return the ordered path positions
   */
  @Override
  public List<Integer> buildPath(Board board) {
    PathStrategy baseStrategy;

    if (board.getRows() % 2 == 1) {
      baseStrategy = new RightStartSnakePathStrategy();
    } else {
      baseStrategy = new LeftStartSnakePathStrategy();
    }

    return new ReversePathDecorator(baseStrategy).buildPath(board);
  }
}
