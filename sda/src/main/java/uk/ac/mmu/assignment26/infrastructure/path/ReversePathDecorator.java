package uk.ac.mmu.assignment26.infrastructure.path;




import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.PathStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReversePathDecorator extends PathDecorator {
    public ReversePathDecorator(PathStrategy wrappedStrategy) {
        super(wrappedStrategy);
    }

    @Override
    public List<Integer> buildPath(Board board) {
        List<Integer> path = new ArrayList<>(wrappedStrategy.buildPath(board));
        Collections.reverse(path);
        return path;
    }
}
