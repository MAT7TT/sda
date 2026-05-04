package uk.ac.mmu.assignment26.infrastructure.observers;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.events.GameOverAttemptedEvent;
import uk.ac.mmu.assignment26.domain.events.GameStartedEvent;
import uk.ac.mmu.assignment26.domain.events.GameStateChangedEvent;
import uk.ac.mmu.assignment26.domain.events.GameWonEvent;
import uk.ac.mmu.assignment26.domain.events.TurnCompletedEvent;
import uk.ac.mmu.assignment26.domain.rules.result.HitResult;
import uk.ac.mmu.assignment26.domain.rules.result.MoveResult;
import uk.ac.mmu.assignment26.domain.rules.result.TeleportResult;
import uk.ac.mmu.assignment26.domain.rules.result.TurnResult;

@Component
public class ConsoleGameEventObserver {

    @EventListener
    public void onGameStarted(GameStartedEvent event) {
        System.out.println("Game");
        System.out.println("Board: rows=" + event.rows() + " columns=" + event.columns());

        for (Player player : event.players()) {
            System.out.println(player.getName() + " " + player.getPathDescription());
        }
    }

    @EventListener
    public void onGameStateChanged(GameStateChangedEvent event) {
        System.out.println("Game State: " + event.from() + " -> " + event.to());
    }

    @EventListener
    public void onTurnCompleted(TurnCompletedEvent event) {
        TurnResult result = event.result();

        System.out.println(result.playerName()
                + " turn " + result.turnCount()
                + " rolls " + result.roll());

        printMove(
                result.playerName(),
                result.moveResult(),
                result.homePosition(),
                result.endPosition()
        );

        printOvershootIfNeeded(result);

        printTeleportIfNeeded(result);

        printHitIfNeeded(result);
    }

    private void printOvershootIfNeeded(TurnResult result) {
        if (result.moveResult().overshotEnd()) {
            System.out.println(result.playerName() + " overshoots end.");
        }
    }

    private void printTeleportIfNeeded(TurnResult result) {
        TeleportResult teleportResult = result.teleportResult();

        if (!teleportResult.teleported()) {
            return;
        }

        System.out.println(result.playerName() + " is teleported.");

        printMove(
                result.playerName(),
                teleportResult.from(),
                teleportResult.to(),
                result.homePosition(),
                result.endPosition()
        );
    }

    private void printHitIfNeeded(TurnResult result) {
        HitResult hitResult = result.hitResult();

        if (!hitResult.hit()) {
            return;
        }

        System.out.println(result.playerName()
                + " hit "
                + hitResult.hitPlayerName()
                + " at position Position "
                + hitResult.hitPosition());

        if (hitResult.playerMovedBack()) {
            printMove(
                    result.playerName(),
                    hitResult.from(),
                    hitResult.to(),
                    result.homePosition(),
                    result.endPosition()
            );
        }
    }

    private void printMove(
            String playerName,
            MoveResult moveResult,
            int homePosition,
            int endPosition
    ) {
        printMove(
                playerName,
                moveResult.from(),
                moveResult.to(),
                homePosition,
                endPosition
        );
    }

    private void printMove(
            String playerName,
            int from,
            int to,
            int homePosition,
            int endPosition
    ) {
        System.out.println(playerName
                + " moves from "
                + describePosition(from, homePosition, endPosition)
                + " to "
                + describePosition(to, homePosition, endPosition));
    }

    private String describePosition(int position, int homePosition, int endPosition) {
        if (position == homePosition) {
            return "Home (Position " + position + ")";
        }

        if (position == endPosition) {
            return "End (Position " + position + ")";
        }

        return String.valueOf(position);
    }

    @EventListener
    public void onGameWon(GameWonEvent event) {
        System.out.println(event.playerName()
                + " wins in " + event.playerTurns() + " turns.");

        System.out.println("Total turns: " + event.totalTurns() + ".");
    }

    @EventListener
    public void onGameOverAttempted(GameOverAttemptedEvent event) {
        System.out.println("Game Over.");
    }
}