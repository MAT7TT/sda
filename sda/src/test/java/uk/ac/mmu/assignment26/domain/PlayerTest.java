package uk.ac.mmu.assignment26.domain;

import org.junit.jupiter.api.Test;
import uk.ac.mmu.assignment26.domain.path.LeftStartSnakePathStrategy;
import uk.ac.mmu.assignment26.domain.path.PathStrategy;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerTest {

    @Test
    void rejectsBlankName() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Player(" ", new Board(3, 3), new LeftStartSnakePathStrategy())
        );
    }

    @Test
    void rejectsNullBoard() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Player("Red", null, new LeftStartSnakePathStrategy())
        );
    }

    @Test
    void rejectsNullPathStrategy() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Player("Red", new Board(3, 3), null)
        );
    }

    @Test
    void rejectsNullPathBuiltByStrategy() {
        PathStrategy pathStrategy = board -> null;

        assertThrows(
                IllegalArgumentException.class,
                () -> new Player("Red", new Board(3, 3), pathStrategy)
        );
    }

    @Test
    void rejectsEmptyPathBuiltByStrategy() {
        PathStrategy pathStrategy = board -> List.of();

        assertThrows(
                IllegalArgumentException.class,
                () -> new Player("Red", new Board(3, 3), pathStrategy)
        );
    }

    @Test
    void rejectsPathWithLessThanHomeAndEndPosition() {
        PathStrategy pathStrategy = board -> List.of(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Player("Red", new Board(1, 1), pathStrategy)
        );
    }

    @Test
    void rejectsPathThatDoesNotIncludeEveryBoardPosition() {
        PathStrategy pathStrategy = board -> List.of(1, 2, 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Player("Red", new Board(3, 3), pathStrategy)
        );
    }

    @Test
    void rejectsPathContainingNullPosition() {
        PathStrategy pathStrategy = board -> Arrays.asList(1, 2, 3, 4, null, 6, 7, 8, 9);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Player("Red", new Board(3, 3), pathStrategy)
        );
    }

    @Test
    void rejectsPathContainingPositionOutsideBoard() {
        PathStrategy pathStrategy = board -> List.of(1, 2, 3, 4, 5, 6, 7, 8, 10);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Player("Red", new Board(3, 3), pathStrategy)
        );
    }

    @Test
    void rejectsPathIndexBeforeStart() {
        Player player = createPlayer();

        assertThrows(
                IllegalArgumentException.class,
                () -> player.setPathIndex(-1)
        );
    }

    @Test
    void rejectsPathIndexAfterEnd() {
        Player player = createPlayer();

        assertThrows(
                IllegalArgumentException.class,
                () -> player.setPathIndex(player.getPathLength())
        );
    }

    @Test
    void rejectsFindingPositionThatIsNotOnPath() {
        Player player = createPlayer();

        assertThrows(
                IllegalArgumentException.class,
                () -> player.findPathIndexOfPosition(99)
        );
    }

    private Player createPlayer() {
        return new Player("Red", new Board(3, 3), new LeftStartSnakePathStrategy());
    }
}
