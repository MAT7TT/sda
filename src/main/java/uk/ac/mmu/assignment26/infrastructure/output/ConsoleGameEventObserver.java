package uk.ac.mmu.assignment26.infrastructure.output;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.events.GameStateChangedEvent;
import uk.ac.mmu.assignment26.domain.events.GameStartedEvent;
import uk.ac.mmu.assignment26.domain.events.PlayerPathSnapshot;
import uk.ac.mmu.assignment26.domain.events.TurnCompletedEvent;
import uk.ac.mmu.assignment26.domain.events.GameOverAttemptedEvent;
import uk.ac.mmu.assignment26.domain.events.GameWonEvent;
import uk.ac.mmu.assignment26.domain.rules.result.HitResult;
import uk.ac.mmu.assignment26.domain.rules.result.MoveResult;
import uk.ac.mmu.assignment26.domain.rules.result.TeleportResult;
import uk.ac.mmu.assignment26.domain.rules.result.TurnResult;

/**
 * Observer that converts domain events into console output.
 *
 * <p>The domain publishes events describing what happened. This
 * infrastructure observer decides how those events are displayed</p>
 */
@Component
public class ConsoleGameEventObserver {
  private final GameOutputWriter outputWriter;

  /**
   * Creates a console game event observer.
   *
   * @param outputWriter write used to send lines to the console
   */
  public ConsoleGameEventObserver(GameOutputWriter outputWriter) {
    this.outputWriter = outputWriter;
  }

  /**
   * Handles the event publishes when a game starts.
   *
   * @param event the game started event
   */
  @EventListener
  public void onGameStarted(GameStartedEvent event) {
    outputWriter.writeLine("Game");
    outputWriter.writeLine("Board: rows=" + event.rows() + " columns=" + event.columns());

    for (PlayerPathSnapshot player : event.players()) {
      outputWriter.writeLine(player.playerName() + " " + formatPath(player));
    }
  }

  /**
   * Handles the game state transition event.
   *
   * @param event the state changed event
   */
  @EventListener
  public void onGameStateChanged(GameStateChangedEvent event) {
    outputWriter.writeLine("Game State: " + event.from() + " -> " + event.to());
  }

  /**
   * Handles a completed turn event.
   *
   * @param event the completed turn event
   */
  @EventListener
  public void onTurnCompleted(TurnCompletedEvent event) {
    TurnResult result = event.result();

    outputWriter.writeLine(
        result.playerName() + " turn " + result.turnCount() + " rolls " + result.roll());

    printOvershootBeforeMoveIfNeeded(result);

    printMove(
        result.playerName(), result.moveResult(), result.homePosition(), result.endPosition());

    printTeleportIfNeeded(result);

    printHitIfNeeded(result);
  }

  private void printOvershootBeforeMoveIfNeeded(TurnResult result) {
    if (result.moveResult().overshotEnd()) {
      outputWriter.writeLine(result.playerName() + " overshoots end.");
    }
  }

  private void printTeleportIfNeeded(TurnResult result) {
    TeleportResult teleportResult = result.teleportResult();

    if (!teleportResult.teleported()) {
      return;
    }

    outputWriter.writeLine(result.playerName() + " is teleported.");

    printMove(
        result.playerName(),
        teleportResult.from(),
        teleportResult.to(),
        result.homePosition(),
        result.endPosition());
  }

  private void printHitIfNeeded(TurnResult result) {
    HitResult hitResult = result.hitResult();

    if (!hitResult.hit()) {
      return;
    }

    outputWriter.writeLine(
        result.playerName()
            + " hit "
            + hitResult.hitPlayerName()
            + " at Position "
            + hitResult.hitPosition());

    if (hitResult.playerMovedBack()) {
      printMove(
          result.playerName(),
          hitResult.from(),
          hitResult.to(),
          result.homePosition(),
          result.endPosition());
    }
  }

  private void printMove(
      String playerName, MoveResult moveResult, int homePosition, int endPosition) {
    printMove(playerName, moveResult.from(), moveResult.to(), homePosition, endPosition);
  }

  private void printMove(String playerName, int from, int to, int homePosition, int endPosition) {
    if (from == to) {
      outputWriter.writeLine(
          playerName + " remains at " + describePosition(from, homePosition, endPosition));
      return;
    }

    outputWriter.writeLine(
        playerName
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

  private String formatPath(PlayerPathSnapshot player) {
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < player.pathPositions().size(); i++) {
      int position = player.pathPositions().get(i);

      if (position == player.homePosition()) {
        sb.append("Home (Position ").append(position).append(")");
      } else if (position == player.endPosition()) {
        sb.append(", End (Position ").append(position).append(")");
      } else {
        sb.append(", ").append(position);
      }
    }

    return sb.toString();
  }

  /**
   * Handles the event published when a player wins.
   *
   * @param event the game won event
   */
  @EventListener
  public void onGameWon(GameWonEvent event) {
    outputWriter.writeLine(event.playerName() + " wins in " + event.playerTurns() + " turns.");

    outputWriter.writeLine("Total turns: " + event.totalTurns() + ".");
  }

  /**
   * Handles an attempt to play after the game has ended.
   * @param event
   */
  @EventListener
  public void onGameOverAttempted(GameOverAttemptedEvent event) {
    outputWriter.writeLine("Game Over.");
  }
}
