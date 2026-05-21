package uk.ac.mmu.assignment26.domain.path;

import uk.ac.mmu.assignment26.domain.Board;

import java.util.List;

public class LeftStartSnakePathStrategy implements PathStrategy {

  @Override
  public List<Integer> buildPath(Board board) {
    return board.getLeftStartSnakePath();
  }
}
