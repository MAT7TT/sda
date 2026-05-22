package uk.ac.mmu.assignment26.domain.path;

import uk.ac.mmu.assignment26.domain.Board;

import java.util.List;

/**
 * Base class for path decorators.
 *
 * <p>A path decorator wraps another {@link PathStrategy} and changes the generated
 * path without changing the wrapped strategy.</p>
 */
public abstract class PathDecorator implements PathStrategy {
  protected final PathStrategy wrappedStrategy;

  /**
   * Creates a path decorator.
   *
   * @param wrappedStrategy the path strategy being decorated
   * @throws IllegalArgumentException if the wrapped strategy is null
   */
  protected PathDecorator(PathStrategy wrappedStrategy) {
    if (wrappedStrategy == null) {
      throw new IllegalArgumentException("Wrapped path strategy must not be null.");
    }

    this.wrappedStrategy = wrappedStrategy;
  }

  @Override
  public abstract List<Integer> buildPath(Board board);
}
