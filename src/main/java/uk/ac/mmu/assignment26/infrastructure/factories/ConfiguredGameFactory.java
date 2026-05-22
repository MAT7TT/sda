package uk.ac.mmu.assignment26.infrastructure.factories;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.Game;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.GameConfiguration;
import uk.ac.mmu.assignment26.domain.config.Wormhole;
import uk.ac.mmu.assignment26.domain.dice.DiceShaker;
import uk.ac.mmu.assignment26.domain.events.GameEventPublisher;
import uk.ac.mmu.assignment26.infrastructure.registry.DiceShakerFactoryRegistry;
import uk.ac.mmu.assignment26.infrastructure.registry.RuleRegistry;
import uk.ac.mmu.assignment26.usecase.ports.GameFactory;

/**
 * Infrastructure adapter that creates configured domain games.
 *
 * <p>This class implements the {@link GameFactory} port. It assembles
 * the board, players, dice, rules, wormholes and event publisher needed by the domain {@link Game}</p>
 */
@Component
public class ConfiguredGameFactory implements GameFactory {
  private final BoardFactory boardFactory;
  private final PlayerFactory playerFactory;
  private final RuleRegistry ruleRegistry;
  private final DiceShakerFactoryRegistry diceShakerFactoryRegistry;
  private final GameEventPublisher eventPublisher;

  /**
   * Create a configured game factory.
   *
   * @param boardFactory factory for board creation
   * @param playerFactory factory for player creation
   * @param ruleRegistry registry for selected rule strategies
   * @param diceShakerFactoryRegistry registry for dice creation
   * @param eventPublisher publisher used by created games
   */
  public ConfiguredGameFactory(
      BoardFactory boardFactory,
      PlayerFactory playerFactory,
      RuleRegistry ruleRegistry,
      DiceShakerFactoryRegistry diceShakerFactoryRegistry,
      GameEventPublisher eventPublisher) {
    this.boardFactory = boardFactory;
    this.playerFactory = playerFactory;
    this.ruleRegistry = ruleRegistry;
    this.diceShakerFactoryRegistry = diceShakerFactoryRegistry;
    this.eventPublisher = eventPublisher;
  }

  /**
   * Create a game using the dice type from the configuration
   *
   * @param configuration the game configuration
   * @return the configured game
   */
  @Override
  public Game createGame(GameConfiguration configuration) {
    DiceShaker diceShaker = diceShakerFactoryRegistry.createDiceShaker(configuration.diceType());

    return buildGame(configuration, diceShaker);
  }

  /**
   * Crreate a game using fixed dice rolls
   * @param configuration the game configuration
   * @param fixedDiceRolls the dice rolls to use
   * @return the configured game
   */
  @Override
  public Game createGame(GameConfiguration configuration, List<Integer> fixedDiceRolls) {
    DiceShaker diceShaker =
        diceShakerFactoryRegistry.createFixedDiceShaker(configuration.diceType(), fixedDiceRolls);

    return buildGame(configuration, diceShaker);
  }

  private Game buildGame(GameConfiguration configuration, DiceShaker diceShaker) {
    Board board = createBoard(configuration);
    List<Player> players = createPlayers(configuration, board);

    addWormholes(board, configuration, players);

    return new Game(
        board,
        players,
        diceShaker,
        ruleRegistry.getMovementRule(configuration.endRuleType()),
        ruleRegistry.getTeleportRule(configuration.teleportRuleType()),
        ruleRegistry.getHitRule(configuration.hitRuleType()),
        eventPublisher);
  }

  private Board createBoard(GameConfiguration configuration) {
    return boardFactory.createBoard(configuration.rows(), configuration.columns());
  }

  private List<Player> createPlayers(GameConfiguration configuration, Board board) {
    if (configuration.numberOfPlayers() == 2) {
      return playerFactory.createTwoPlayerGamePlayers(board);
    }

    return playerFactory.createFourPlayerGamePlayers(board);
  }

  private void addWormholes(Board board, GameConfiguration configuration, List<Player> players) {
    List<Integer> blockedPositions = getBlockedWormholePositions(players);

    for (Wormhole wormhole : configuration.wormholes()) {
      board.addWormhole(wormhole, blockedPositions);
    }
  }

  private List<Integer> getBlockedWormholePositions(List<Player> players) {
    List<Integer> blockedPositions = new ArrayList<>();

    for (Player player : players) {
      blockedPositions.add(player.getHomePosition());
      blockedPositions.add(player.getEndPosition());
    }

    return blockedPositions;
  }
}
