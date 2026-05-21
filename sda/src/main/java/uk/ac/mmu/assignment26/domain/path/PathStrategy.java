package uk.ac.mmu.assignment26.domain.path;

import uk.ac.mmu.assignment26.domain.Board;

import java.util.List;

public interface PathStrategy {
  List<Integer> buildPath(Board board);
}
