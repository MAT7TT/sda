package uk.ac.mmu.assignment26.infrastructure.factories;

import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.path.*;

import java.util.List;

@Component
public class PlayerFactory {

    public List<Player> createTwoPlayerGamePlayers(Board board) {
        return List.of(
                createRed(board),
                createBlue(board)
        );
    }

    public List<Player> createFourPlayerGamePlayers(Board board) {
        return List.of(
                createRed(board),
                createBlue(board),
                createYellow(board),
                createGreen(board)
        );
    }

    public Player createRed(Board board) {
        PathStrategy strategy = new LeftStartSnakePathStrategy();
        return new Player("Red", board, strategy);
    }

    public Player createBlue(Board board) {
        PathStrategy strategy = new TopRightStartSnakePathStrategy();
        return new Player("Blue", board, strategy);
    }

    public Player createYellow(Board board) {
        PathStrategy strategy = new TopLeftStartSnakePathStrategy();
        return new Player("Yellow", board, strategy);
    }

    public Player createGreen(Board board) {
        PathStrategy strategy = new RightStartSnakePathStrategy();
        return new Player("Green", board, strategy);
    }
}