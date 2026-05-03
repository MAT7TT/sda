package uk.ac.mmu.assignment26.domain;



import java.util.List;

public interface PathStrategy {
    List<Integer> buildPath(Board board);
}
