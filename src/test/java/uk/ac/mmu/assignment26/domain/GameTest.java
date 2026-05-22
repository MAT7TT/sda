package uk.ac.mmu.assignment26.domain;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import uk.ac.mmu.assignment26.domain.dice.DiceShaker;
import uk.ac.mmu.assignment26.domain.events.GameEventPublisher;
import uk.ac.mmu.assignment26.domain.path.LeftStartSnakePathStrategy;
import uk.ac.mmu.assignment26.domain.path.RightStartSnakePathStrategy;
import uk.ac.mmu.assignment26.domain.rules.hit.HitRule;
import uk.ac.mmu.assignment26.domain.rules.hit.IgnoreHitRule;
import uk.ac.mmu.assignment26.domain.rules.movement.MovementRule;
import uk.ac.mmu.assignment26.domain.rules.movement.StandardEndMovementRule;
import uk.ac.mmu.assignment26.domain.rules.teleport.IgnoreTeleportRule;
import uk.ac.mmu.assignment26.domain.rules.teleport.TeleportRule;

class GameTest {

    @Test
    void rejectsNullBoard() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Game(
                        null,
                        createPlayers(new Board(3, 3)),
                        createDiceShaker(),
                        createMovementRule(),
                        createTeleportRule(),
                        createHitRule(),
                        createEventPublisher()
                )
        );
    }

    @Test
    void rejectsNullPlayersList() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Game(
                        new Board(3, 3),
                        null,
                        createDiceShaker(),
                        createMovementRule(),
                        createTeleportRule(),
                        createHitRule(),
                        createEventPublisher()
                )
        );
    }

    @Test
    void rejectsEmptyPlayersList() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Game(
                        new Board(3, 3),
                        List.of(),
                        createDiceShaker(),
                        createMovementRule(),
                        createTeleportRule(),
                        createHitRule(),
                        createEventPublisher()
                )
        );
    }

    @Test
    void rejectsPlayersListContainingNull() {
        Board board = new Board(3, 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Game(
                        board,
                        Arrays.asList(createRedPlayer(board), null),
                        createDiceShaker(),
                        createMovementRule(),
                        createTeleportRule(),
                        createHitRule(),
                        createEventPublisher()
                )
        );
    }

    @Test
    void rejectsNullDiceShaker() {
        Board board = new Board(3, 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Game(
                        board,
                        createPlayers(board),
                        null,
                        createMovementRule(),
                        createTeleportRule(),
                        createHitRule(),
                        createEventPublisher()
                )
        );
    }

    @Test
    void rejectsNullMovementRule() {
        Board board = new Board(3, 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Game(
                        board,
                        createPlayers(board),
                        createDiceShaker(),
                        null,
                        createTeleportRule(),
                        createHitRule(),
                        createEventPublisher()
                )
        );
    }

    @Test
    void rejectsNullTeleportRule() {
        Board board = new Board(3, 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Game(
                        board,
                        createPlayers(board),
                        createDiceShaker(),
                        createMovementRule(),
                        null,
                        createHitRule(),
                        createEventPublisher()
                )
        );
    }

    @Test
    void rejectsNullHitRule() {
        Board board = new Board(3, 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Game(
                        board,
                        createPlayers(board),
                        createDiceShaker(),
                        createMovementRule(),
                        createTeleportRule(),
                        null,
                        createEventPublisher()
                )
        );
    }

    @Test
    void rejectsNullEventPublisher() {
        Board board = new Board(3, 3);

        assertThrows(
                IllegalArgumentException.class,
                () -> new Game(
                        board,
                        createPlayers(board),
                        createDiceShaker(),
                        createMovementRule(),
                        createTeleportRule(),
                        createHitRule(),
                        null
                )
        );
    }

    @Test
    void rejectsNullGameState() {
        Game game = createGame();

        assertThrows(
                IllegalArgumentException.class,
                () -> game.setState(null)
        );
    }

    private Game createGame() {
        Board board = new Board(3, 3);

        return new Game(
                board,
                createPlayers(board),
                createDiceShaker(),
                createMovementRule(),
                createTeleportRule(),
                createHitRule(),
                createEventPublisher()
        );
    }

    private List<Player> createPlayers(Board board) {
        return List.of(
                createRedPlayer(board),
                createBluePlayer(board)
        );
    }

    private Player createRedPlayer(Board board) {
        return new Player("Red", board, new LeftStartSnakePathStrategy());
    }

    private Player createBluePlayer(Board board) {
        return new Player("Blue", board, new RightStartSnakePathStrategy());
    }

    private DiceShaker createDiceShaker() {
        return () -> 1;
    }

    private MovementRule createMovementRule() {
        return new StandardEndMovementRule();
    }

    private TeleportRule createTeleportRule() {
        return new IgnoreTeleportRule();
    }

    private HitRule createHitRule() {
        return new IgnoreHitRule();
    }

    private GameEventPublisher createEventPublisher() {
        return event -> {
        };
    }
}
