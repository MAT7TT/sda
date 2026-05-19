package uk.ac.mmu.assignment26.domain.path;

import uk.ac.mmu.assignment26.domain.Board;

import java.util.List;

public class TopLeftStartSnakePathStrategy implements PathStrategy {
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
