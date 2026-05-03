package uk.ac.mmu.assignment26.infrastructure.path;



import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.PathStrategy;

import java.util.List;

public abstract class PathDecorator implements PathStrategy {
    protected final PathStrategy wrappedStrategy;

    protected PathDecorator(PathStrategy wrappedStrategy) {
        this.wrappedStrategy = wrappedStrategy;
    }

    @Override
    public abstract List<Integer> buildPath(Board board);
}
