package uk.ac.mmu.assignment26.infrastructure.factories;

import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.Board;

@Component
public class BoardFactory {

  public Board createBoard(int rows, int columns) {
    return new Board(rows, columns);
  }
}
