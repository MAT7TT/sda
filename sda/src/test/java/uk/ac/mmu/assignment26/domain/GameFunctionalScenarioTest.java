package uk.ac.mmu.assignment26.domain;

import org.junit.jupiter.api.Test;
import uk.ac.mmu.assignment26.domain.config.DiceType;
import uk.ac.mmu.assignment26.domain.dice.FixedDiceShaker;
import uk.ac.mmu.assignment26.domain.events.GameEventPublisher;
import uk.ac.mmu.assignment26.domain.events.TurnCompletedEvent;
import uk.ac.mmu.assignment26.domain.path.LeftStartSnakePathStrategy;
import uk.ac.mmu.assignment26.domain.path.RightStartSnakePathStrategy;
import uk.ac.mmu.assignment26.domain.rules.hit.ForfeitOnHitRule;
import uk.ac.mmu.assignment26.domain.rules.hit.HitRule;
import uk.ac.mmu.assignment26.domain.rules.hit.IgnoreHitRule;
import uk.ac.mmu.assignment26.domain.rules.movement.MovementRule;
import uk.ac.mmu.assignment26.domain.rules.movement.StandardEndMovementRule;
import uk.ac.mmu.assignment26.domain.rules.teleport.IgnoreTeleportRule;
import uk.ac.mmu.assignment26.domain.rules.teleport.TeleportRule;
import uk.ac.mmu.assignment26.domain.rules.teleport.WormholeTeleportRule;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameFunctionalScenarioTest {

    @Test
    void gameEndsWhenPlayerReachesEnd() {
        CapturingGameEventPublisher publisher = new CapturingGameEventPublisher();

        Game game = createGame(
                new Board(3, 3),
                new FixedDiceShaker(List.of(8), DiceType.DOUBLE),
                new StandardEndMovementRule(),
                new IgnoreTeleportRule(),
                new IgnoreHitRule(),
                publisher
        );

        GameResult result = game.play();

        assertEquals("Red", result.winnerName());
        assertEquals(1, result.winnerTurns());
        assertEquals(1, result.totalTurns());
        assertEquals(List.of(8), result.diceRolls());
    }

    @Test
    void turnEventRecordsPlayerMovement() {
        CapturingGameEventPublisher publisher = new CapturingGameEventPublisher();

        Game game = createGame(
                new Board(3, 3),
                new FixedDiceShaker(List.of(2, 8), DiceType.DOUBLE),
                new StandardEndMovementRule(),
                new IgnoreTeleportRule(),
                new IgnoreHitRule(),
                publisher
        );

        game.play();

        TurnCompletedEvent firstTurn = publisher.eventsOfType(TurnCompletedEvent.class).getFirst();

        assertEquals("Red", firstTurn.result().playerName());
        assertEquals(1, firstTurn.result().moveResult().from());
        assertEquals(3, firstTurn.result().moveResult().to());
    }

    @Test
    void wormholeTeleportMovesPlayerToExitPosition() {
        Board board = new Board(3, 3);
        board.addWormhole(3, 8, List.of());

        CapturingGameEventPublisher publisher = new CapturingGameEventPublisher();

        Game game = createGame(
                board,
                new FixedDiceShaker(List.of(2, 8), DiceType.DOUBLE),
                new StandardEndMovementRule(),
                new WormholeTeleportRule(),
                new IgnoreHitRule(),
                publisher
        );

        game.play();

        TurnCompletedEvent firstTurn = publisher.eventsOfType(TurnCompletedEvent.class).getFirst();

        assertTrue(firstTurn.result().teleportResult().teleported());
        assertEquals(3, firstTurn.result().teleportResult().from());
        assertEquals(8, firstTurn.result().teleportResult().to());
    }

    @Test
    void forfeitOnHitMovesCurrentPlayerBackToStartTurnPosition() {
        CapturingGameEventPublisher publisher = new CapturingGameEventPublisher();

        Game game = createGame(
                new Board(3, 3),
                new FixedDiceShaker(List.of(2, 8), DiceType.DOUBLE),
                new StandardEndMovementRule(),
                new IgnoreTeleportRule(),
                new ForfeitOnHitRule(),
                publisher
        );

        game.play();

        TurnCompletedEvent firstTurn = publisher.eventsOfType(TurnCompletedEvent.class).getFirst();

        assertTrue(firstTurn.result().hitResult().hit());
        assertTrue(firstTurn.result().hitResult().playerMovedBack());
        assertEquals(3, firstTurn.result().hitResult().from());
        assertEquals(1, firstTurn.result().hitResult().to());
    }

    private Game createGame(
            Board board,
            FixedDiceShaker diceShaker,
            MovementRule movementRule,
            TeleportRule teleportRule,
            HitRule hitRule,
            GameEventPublisher eventPublisher
    ) {
        return new Game(
                board,
                createPlayers(board),
                diceShaker,
                movementRule,
                teleportRule,
                hitRule,
                eventPublisher
        );
    }

    private List<Player> createPlayers(Board board) {
        return List.of(
                new Player("Red", board, new LeftStartSnakePathStrategy()),
                new Player("Blue", board, new RightStartSnakePathStrategy())
        );
    }

    private static class CapturingGameEventPublisher implements GameEventPublisher {
        private final List<Object> events = new ArrayList<>();

        @Override
        public void publish(Object event) {
            events.add(event);
        }

        private <T> List<T> eventsOfType(Class<T> eventType) {
            return events.stream()
                    .filter(eventType::isInstance)
                    .map(eventType::cast)
                    .toList();
        }
    }
}
