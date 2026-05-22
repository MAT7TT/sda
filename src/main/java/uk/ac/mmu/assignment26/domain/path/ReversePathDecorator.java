package uk.ac.mmu.assignment26.domain.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import uk.ac.mmu.assignment26.domain.Board;

/**
 * Decorator that reverses the path created by another path strategy.
 *
 * <p>This is used for players whose path is the reverse of an existing generated path, avoiding
 * duplicated path-building logic</p>
 */
public class ReversePathDecorator extends PathDecorator {
  /**
   * Creates a reverse path decorator.
   *
   * @param wrappedStrategy the path strategy to reverse
   * @throws IllegalArgumentException if the wrapped strategy is null
   */
  public ReversePathDecorator(PathStrategy wrappedStrategy) {
    super(wrappedStrategy);
  }

  /**
   * Builds and reverses the wrapped
   * @param board the board to build the path for
   * @returnk the reversed path
   */
  @Override
  public List<Integer> buildPath(Board board) {
    List<Integer> path = new ArrayList<>(wrappedStrategy.buildPath(board));
    Collections.reverse(path);
    return path;
  }
}
