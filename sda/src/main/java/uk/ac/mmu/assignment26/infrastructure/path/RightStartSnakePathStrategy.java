package uk.ac.mmu.assignment26.infrastructure.path;

import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.PathStrategy;

import java.util.List;

public class RightStartSnakePathStrategy implements PathStrategy {

    @Override
    public List<Integer> buildPath(Board board) {
        return board.getRightStartSnakePath();
    }
}
