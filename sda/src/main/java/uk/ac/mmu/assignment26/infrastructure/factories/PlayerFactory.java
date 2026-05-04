package uk.ac.mmu.assignment26.infrastructure.factories;


import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.path.PathStrategy;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.path.LeftStartSnakePathStrategy;
import uk.ac.mmu.assignment26.domain.path.ReversePathDecorator;
import uk.ac.mmu.assignment26.domain.path.RightStartSnakePathStrategy;

@Component
public class PlayerFactory {

    public Player createRed(Board board) {
        PathStrategy strategy = new LeftStartSnakePathStrategy();
        return new Player("Red", board, strategy);
    }

    public Player createBlue(Board board) {
        PathStrategy strategy = new ReversePathDecorator(new RightStartSnakePathStrategy());
        return new Player("Blue", board, strategy);
    }

    public Player createYellow(Board board) {
        PathStrategy strategy = new ReversePathDecorator(new LeftStartSnakePathStrategy());
        return new Player("Yellow", board, strategy);
    }

    public Player createGreen(Board board) {
        PathStrategy strategy = new RightStartSnakePathStrategy();
        return new Player("Green", board, strategy);
    }
}
