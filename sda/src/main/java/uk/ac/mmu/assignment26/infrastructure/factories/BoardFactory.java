package uk.ac.mmu.assignment26.infrastructure.factories;


import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.Board;

@Component
public class BoardFactory {

    public Board createSmallBoard() {
        return new Board(5, 5);
    }

    public Board createLargeBoard() {
        return new Board(6, 6);
    }
}