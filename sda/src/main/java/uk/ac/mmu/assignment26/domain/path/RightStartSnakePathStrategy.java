package uk.ac.mmu.assignment26.domain.path;

import uk.ac.mmu.assignment26.domain.Board;

import java.util.List;

public class RightStartSnakePathStrategy implements PathStrategy {

    @Override
    public List<Integer> buildPath(Board board) {
        return board.getRightStartSnakePath();
    }
}
