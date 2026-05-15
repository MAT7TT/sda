package uk.ac.mmu.assignment26.domain.path;

import uk.ac.mmu.assignment26.domain.Board;

import java.util.List;

public class TopRightStartSnakePathStrategy implements PathStrategy {
    @Override
    public List<Integer> buildPath(Board board) {
        PathStrategy baseStrategy;

        if (board.getRows() % 2 == 1) {
            baseStrategy = new LeftStartSnakePathStrategy();
        } else
        {
            baseStrategy = new RightStartSnakePathStrategy();
        }

        return new ReversePathDecorator(baseStrategy).buildPath(board);
    }
}
