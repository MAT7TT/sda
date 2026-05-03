package uk.ac.mmu.assignment26.infrastructure.factories;

import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.dice.DiceShaker;
import uk.ac.mmu.assignment26.domain.Game;
import uk.ac.mmu.assignment26.domain.GameEventPublisher;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.config.GameConfiguration;
import uk.ac.mmu.assignment26.domain.config.Wormhole;
import uk.ac.mmu.assignment26.infrastructure.registry.DiceShakerFactoryRegistry;
import uk.ac.mmu.assignment26.infrastructure.registry.RuleRegistry;
import uk.ac.mmu.assignment26.ports.GameFactory;

import java.util.ArrayList;
import java.util.List;

@Component
public class ConfiguredGameFactory implements GameFactory {
    private final BoardFactory boardFactory;
    private final PlayerFactory playerFactory;
    private final RuleRegistry ruleRegistry;
    private final DiceShakerFactoryRegistry diceShakerFactoryRegistry;
    private final GameEventPublisher eventPublisher;

    public ConfiguredGameFactory(
            BoardFactory boardFactory,
            PlayerFactory playerFactory,
            RuleRegistry ruleRegistry,
            DiceShakerFactoryRegistry diceShakerFactoryRegistry,
            GameEventPublisher eventPublisher
    ) {
        this.boardFactory = boardFactory;
        this.playerFactory = playerFactory;
        this.ruleRegistry = ruleRegistry;
        this.diceShakerFactoryRegistry = diceShakerFactoryRegistry;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Game createGame(GameConfiguration configuration) {
        DiceShaker diceShaker =
                diceShakerFactoryRegistry.createDiceShaker(configuration.diceType());

        return buildGame(configuration, diceShaker);
    }

    @Override
    public Game createGame(GameConfiguration configuration, List<Integer> fixedDiceRolls) {
        DiceShaker diceShaker =
                diceShakerFactoryRegistry.createFixedDiceShaker(fixedDiceRolls);

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
                eventPublisher
        );
    }

    private Board createBoard(GameConfiguration configuration) {
        if (configuration.numberOfPlayers() == 4) {
            return boardFactory.createLargeBoard();
        }

        return boardFactory.createSmallBoard();
    }

    private List<Player> createPlayers(GameConfiguration configuration, Board board) {
        List<Player> players = new ArrayList<>();

        players.add(playerFactory.createRed(board));
        players.add(playerFactory.createBlue(board));

        if (configuration.players() == 4) {
            players.add(playerFactory.createYellow(board));
            players.add(playerFactory.createGreen(board));
        }

        return players;
    }

    private void addWormholes(
            Board board,
            GameConfiguration configuration,
            List<Player> players
    ) {
        for (Wormhole wormhole : configuration.wormholes()) {
            board.addWormhole(
                    wormhole.firstPosition(),
                    wormhole.secondPosition(),
                    players
            );
        }
    }
}