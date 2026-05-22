package uk.ac.mmu.assignment26.domain;

import java.util.ArrayList;
import java.util.List;
import uk.ac.mmu.assignment26.domain.dice.DiceShaker;
import uk.ac.mmu.assignment26.domain.events.GameEventPublisher;
import uk.ac.mmu.assignment26.domain.events.GameStartedEvent;
import uk.ac.mmu.assignment26.domain.events.GameWonEvent;
import uk.ac.mmu.assignment26.domain.events.PlayerPathSnapshot;
import uk.ac.mmu.assignment26.domain.events.TurnCompletedEvent;
import uk.ac.mmu.assignment26.domain.rules.hit.HitRule;
import uk.ac.mmu.assignment26.domain.rules.movement.MovementRule;
import uk.ac.mmu.assignment26.domain.rules.result.HitResult;
import uk.ac.mmu.assignment26.domain.rules.result.MoveResult;
import uk.ac.mmu.assignment26.domain.rules.result.TeleportResult;
import uk.ac.mmu.assignment26.domain.rules.result.TurnResult;
import uk.ac.mmu.assignment26.domain.rules.teleport.TeleportRule;
import uk.ac.mmu.assignment26.domain.state.GameState;
import uk.ac.mmu.assignment26.domain.state.ReadyState;

/**
 * Coordinates one game simulation.
 *
 * <p>The game is the domain context for the selected dice, movement, teleport and hit strategies.
 * It owns the turn order, total turn count, winner detection, state transitions and domain event publication.</p>
 */
public class Game {
  private final Board board;
  private final List<Player> players;
  private final DiceShaker diceShaker;
  private final MovementRule movementRule;
  private final TeleportRule teleportRule;
  private final HitRule hitRule;
  private final GameEventPublisher eventPublisher;
  private final List<Integer> diceRolls;

  private GameState state;
  private int currentPlayerIndex;
  private int totalTurns;
  private Player winner;

  /**
   * Create a game from its required domain collaborators.
   *
   * @param board the board being played on
   * @param players the players in turn order
   * @param diceShaker the dice strategy
   * @param movementRule the movement rule strategy
   * @param teleportRule the teleport rule strategy
   * @param hitRule the hit rule strategy
   * @param eventPublisher publisher for domain events
   * @throws IllegalArgumentException if any required collaborator is null or the player list is empty
   */
  public Game(
      Board board,
      List<Player> players,
      DiceShaker diceShaker,
      MovementRule movementRule,
      TeleportRule teleportRule,
      HitRule hitRule,
      GameEventPublisher eventPublisher) {
    if (board == null) {
      throw new IllegalArgumentException("Board must not be null.");
    }

    if (players == null || players.isEmpty()) {
      throw new IllegalArgumentException("Players must not be empty.");
    }

    for (Player player : players) {
      if (player == null) {
        throw new IllegalArgumentException("Players must not contain null.");
      }
    }

    if (diceShaker == null) {
      throw new IllegalArgumentException("Dice shaker must not be null.");
    }

    if (movementRule == null) {
      throw new IllegalArgumentException("Movement rule must not be null.");
    }

    if (teleportRule == null) {
      throw new IllegalArgumentException("Teleport rule must not be null.");
    }

    if (hitRule == null) {
      throw new IllegalArgumentException("Hit rule must not be null.");
    }

    if (eventPublisher == null) {
      throw new IllegalArgumentException("Event publisher must not be null.");
    }

    this.board = board;
    this.players = new ArrayList<>(players);
    this.diceShaker = diceShaker;
    this.movementRule = movementRule;
    this.teleportRule = teleportRule;
    this.hitRule = hitRule;
    this.eventPublisher = eventPublisher;
    this.diceRolls = new ArrayList<>();

    this.state = new ReadyState();
    this.currentPlayerIndex = 0;
    this.totalTurns = 0;
  }

  /**
   * Plays the game until a player wins.
   *
   * @return the completed game result
   */
  public GameResult play() {
    publishEvent(
        new GameStartedEvent(board.getRows(), board.getColumns(), createPlayerSnapshots()));
    start();

    while (winner == null) {
      playTurn();
    }
    finish();

    return new GameResult(
        winner.getName(), winner.getTurnCount(), totalTurns, List.copyOf(diceRolls));
  }

  /**
   * Requests the current state to start the game.
   */
  public void start() {
    state.start(this);
  }

  /**
   * Requests the current state to play one turn.
   */
  public void playTurn() {
    state.playTurn(this);
  }

  /**
   * Requests the current state to finish the game.
   */
  public void finish() {
    state.finish(this);
  }

  /**
   * Executes one turn while the game is in play.
   *
   * <p>The rule order is movement, teleport, hit, then winner detection.</p>
   */
  public void executeTurn() {
    if (winner != null) {
      return;
    }

    Player currentPlayer = players.get(currentPlayerIndex);

    TurnResult turnResult = takeTurn(currentPlayer);

    publishEvent(new TurnCompletedEvent(turnResult));

    if (currentPlayer.isAtEnd()) {
      winner = currentPlayer;

      publishEvent(new GameWonEvent(winner.getName(), winner.getTurnCount(), totalTurns));

      return;
    }

    moveToNextPlayer();
  }

  private TurnResult takeTurn(Player currentPlayer) {
    int startTurnPathIndex = currentPlayer.getPathIndex();

    int roll = diceShaker.shake();
    diceRolls.add(roll);

    currentPlayer.incrementTurnCount();
    totalTurns++;

    MoveResult moveResult = movementRule.move(currentPlayer, roll);
    TeleportResult teleportResult = teleportRule.apply(board, currentPlayer);
    HitResult hitResult = hitRule.apply(currentPlayer, startTurnPathIndex, players);

    return new TurnResult(
        currentPlayer.getName(),
        roll,
        currentPlayer.getTurnCount(),
        currentPlayer.getHomePosition(),
        currentPlayer.getEndPosition(),
        moveResult,
        teleportResult,
        hitResult);
  }

  private List<PlayerPathSnapshot> createPlayerSnapshots() {
    List<PlayerPathSnapshot> pathSnapshotList = new ArrayList<>();

    for (Player player : players) {
      pathSnapshotList.add(
          new PlayerPathSnapshot(
              player.getName(),
              player.getPathPositions(),
              player.getHomePosition(),
              player.getEndPosition()));
    }

    return pathSnapshotList;
  }

  private void moveToNextPlayer() {
    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
  }

  /**
   * Changes the current game state.
   *
   * <p>This method is used by the State pattern implementations to move the game
   * context between Ready, InPlay and GameOver.</p>
   *
   * @param state the new game state
   * @throws IllegalArgumentException if the state is null
   */
  public void setState(GameState state) {
    if (state == null) {
      throw new IllegalArgumentException("Game state must not be null.");
    }

    this.state = state;
  }

  /**
   * Publishes a domain event through the configured event publisher.
   *
   * @param event the event to publish
   */
  public void publishEvent(Object event) {
    eventPublisher.publish(event);
  }
}
