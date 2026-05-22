package uk.ac.mmu.assignment26.infrastructure.factories;

import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.Board;

/**
 * Factory for creating board instances.
 */
@Component
public class BoardFactory {

  /**
   * Create a board with the supplied dimesions.
   *
   * @param rows the number of rows
   * @param columns the number of columns
   * @return the created board
   * @throws IllegalArgumentException if the board dimensions are invalid
   */
  public Board createBoard(int rows, int columns) {
    return new Board(rows, columns);
  }
}
