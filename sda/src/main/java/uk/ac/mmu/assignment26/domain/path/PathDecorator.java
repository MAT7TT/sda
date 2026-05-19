package uk.ac.mmu.assignment26.domain.path;



import uk.ac.mmu.assignment26.domain.Board;

import java.util.List;

public abstract class PathDecorator implements PathStrategy {
    protected final PathStrategy wrappedStrategy;

    protected PathDecorator(PathStrategy wrappedStrategy) {
        if (wrappedStrategy == null) {
            throw new IllegalArgumentException("Wrapped path strategy must not be null.");
        }

        this.wrappedStrategy = wrappedStrategy;
    }

    @Override
    public abstract List<Integer> buildPath(Board board);
}
