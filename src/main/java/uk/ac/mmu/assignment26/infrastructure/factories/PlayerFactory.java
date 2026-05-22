package uk.ac.mmu.assignment26.infrastructure.factories;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Component;
import uk.ac.mmu.assignment26.domain.Board;
import uk.ac.mmu.assignment26.domain.Player;
import uk.ac.mmu.assignment26.domain.path.LeftStartSnakePathStrategy;
import uk.ac.mmu.assignment26.domain.path.PathStrategy;
import uk.ac.mmu.assignment26.domain.path.RightStartSnakePathStrategy;
import uk.ac.mmu.assignment26.domain.path.TopLeftStartSnakePathStrategy;
import uk.ac.mmu.assignment26.domain.path.TopRightStartSnakePathStrategy;

/**
 * Factory for creating the player layouts uesd by the assessment variations
 *
 * <p>The factory creates either the two-player Red/Blue setup or the
 * four-player Red/Blue/Yellow/Green setup and validate that player homes, ends and names are usable</p>
 */
@Component
public class PlayerFactory {

  /**
   * Create players for the two-player game.
   * @param board the board the players will use
   * @return Red and Blue players in turn order
   * @throws IllegalArgumentException if the board or generated players are invalid
   */
  public List<Player> createTwoPlayerGamePlayers(Board board) {
    validateBoard(board);

    return validatePlayers(List.of(createRed(board), createBlue(board)));
  }

  /**
   * Create players for the four-player game.
   *
   * @param board the board the players will use
   * @return Red, Blue, Yellow and Green players in turn order
   * @throws IllegalArgumentException if the board or generated players are invalid.
   */
  public List<Player> createFourPlayerGamePlayers(Board board) {
    validateBoard(board);

    return validatePlayers(
        List.of(createRed(board), createBlue(board), createYellow(board), createGreen(board)));
  }

  private void validateBoard(Board board) {
    if (board == null) {
      throw new IllegalArgumentException("Board must not be null.");
    }
  }

  private List<Player> validatePlayers(List<Player> players) {
    Set<String> names = new HashSet<>();
    Set<Integer> homes = new HashSet<>();
    Set<Integer> ends = new HashSet<>();

    for (Player player : players) {
      if (!names.add(player.getName())) {
        throw new IllegalArgumentException("Player names must be unique.");
      }

      if (!homes.add(player.getHomePosition())) {
        throw new IllegalArgumentException("Player home positions must be unique.");
      }

      if (!ends.add(player.getEndPosition())) {
        throw new IllegalArgumentException("Player end positions must be unique.");
      }

      if (player.getHomePosition() == player.getEndPosition()) {
        throw new IllegalArgumentException("Player home and end positions must be different.");
      }
    }

    return players;
  }

  private Player createRed(Board board) {
    PathStrategy strategy = new LeftStartSnakePathStrategy();
    return new Player("Red", board, strategy);
  }

  private Player createBlue(Board board) {
    PathStrategy strategy = new TopRightStartSnakePathStrategy();
    return new Player("Blue", board, strategy);
  }

  private Player createYellow(Board board) {
    PathStrategy strategy = new TopLeftStartSnakePathStrategy();
    return new Player("Yellow", board, strategy);
  }

  private Player createGreen(Board board) {
    PathStrategy strategy = new RightStartSnakePathStrategy();
    return new Player("Green", board, strategy);
  }
}
