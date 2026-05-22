package uk.ac.mmu.assignment26.domain;

import uk.ac.mmu.assignment26.domain.config.Wormhole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the numbered board used by the game.
 *
 * <p>The board owns the row and column dimensions, the generated boustrophedon numbering
 * and the configured wormholes. Its main invariant is that all positions must be inside the
 * board and all wormholes must connect two valid, distinct, unblocked positions.</p>
 */
public class Board {
  private final int[][] positions;
  private final int rows;
  private final int columns;
  private final Map<Integer, Integer> wormholes = new HashMap<>();

  /**
   * Creates a board with the supplied dimensions.
   *
   * @param rows the number of board rows
   * @param columns the number of board columns
   * @throws IllegalArgumentException if rows and columns are not positive
   */
  public Board(int rows, int columns) {
    if (rows <= 0 || columns <= 0) {
      throw new IllegalArgumentException("Board rows and columns must be greater than zero.");
    }

    this.rows = rows;
    this.columns = columns;
    this.positions = new int[rows][columns];

    fillBoard();
  }

  /**
   * Add a two-way wormhole to the board
   *
   * <p>The blocked positions are usually player home and end positions. This
   * Prevents wormholes being placed where the brief says they are not allowed.</p>
   *
   * @param wormhole the wormhole to add
   * @param blockedPositions positions that cannot be wormhole endpoints
   * @throws IllegalArgumentException if the wormhole is null, outside the board,
   * reuses an endpoint, or uses a blocked position
   */
  public void addWormhole(Wormhole wormhole, List<Integer> blockedPositions) {
    validateWormhole(wormhole, blockedPositions);
    wormholes.put(wormhole.firstPosition(), wormhole.secondPosition());
    wormholes.put(wormhole.secondPosition(), wormhole.firstPosition());
  }

  private void validateWormhole(Wormhole wormhole, List<Integer> blockedPositions) {
    if (wormhole == null) {
      throw new IllegalArgumentException("Wormhole must not be null.");
    }

    if (blockedPositions == null) {
      throw new IllegalArgumentException("Blocked positions must not be null.");
    }

    int firstPosition = wormhole.firstPosition();
    int secondPosition = wormhole.secondPosition();

    if (!isValidPosition(firstPosition) || !isValidPosition(secondPosition)) {
      throw new IllegalArgumentException("Wormhole endpoint is outside the board.");
    }

    if (wormholes.containsKey(firstPosition) || wormholes.containsKey(secondPosition)) {
      throw new IllegalArgumentException("A wormhole endpoint is already in use.");
    }

    if (blockedPositions.contains(firstPosition) || blockedPositions.contains(secondPosition)) {
      throw new IllegalArgumentException(
          "Wormholes cannot be placed on a player's home or end position.");
    }
  }

  /**
   * Checks whether a wormhole starts or ends at the supplied position.
   *
   * @param position the board position to check
   * @return true if the position is a wormhole endpoint
   */
  public boolean hasWormholeAt(int position) {
    return wormholes.containsKey(position);
  }

  /**
   * Returns the opposite endpoint of the wormhole at the supplied position.
   *
   * @param position the wormhole endpoint
   * @return the connected wormhole endpoint
   * @throws IllegalArgumentException if no wormhole exists at the supplied position
   */
  public int getWormholeExit(int position) {
    Integer exit = wormholes.get(position);

    if (exit == null) {
      throw new IllegalArgumentException("No wormhole exists at position " + position);
    }

    return exit;
  }

  /**
   * Checks whether a position inside the board.
   *
   * @param position the position to check
   * @return true if the position is between one and the number of cells
   */
  public boolean isValidPosition(int position) {
    return position >= 1 && position <= getCellCount();
  }

  private void fillBoard() {
    int count = 1;

    for (int row = rows - 1; row >= 0; row--) {
      int rowFromBottom = rows - 1 - row;

      if (rowFromBottom % 2 == 0) {
        for (int col = 0; col < columns; col++) {
          positions[row][col] = count++;
        }
      } else {
        for (int col = columns - 1; col >= 0; col--) {
          positions[row][col] = count++;
        }
      }
    }
  }

  public int getRows() {
    return rows;
  }

  public int getColumns() {
    return columns;
  }

  public int getCellCount() {
    return rows * columns;
  }

  /**
   * Builds the boustrophedon path starting from the lower-left side of the board
   *
   * @return the ordered board positions from left-start home to end
   */
  public List<Integer> getLeftStartSnakePath() {
    List<Integer> path = new ArrayList<>();

    for (int row = rows - 1; row >= 0; row--) {
      int rowFromBottom = rows - 1 - row;

      if (rowFromBottom % 2 == 0) {
        for (int col = 0; col < columns; col++) {
          path.add(positions[row][col]);
        }
      } else {
        for (int col = columns - 1; col >= 0; col--) {
          path.add(positions[row][col]);
        }
      }
    }

    return path;
  }

  /**
   * Builds the boustrophedon path starting from the lower-right side of the board
   *
   * @return the ordered board positions from right-start home to end
   */
  public List<Integer> getRightStartSnakePath() {
    List<Integer> path = new ArrayList<>();

    for (int row = rows - 1; row >= 0; row--) {
      int rowFromBottom = rows - 1 - row;

      if (rowFromBottom % 2 == 0) {
        for (int col = columns - 1; col >= 0; col--) {
          path.add(positions[row][col]);
        }
      } else {
        for (int col = 0; col < columns; col++) {
          path.add(positions[row][col]);
        }
      }
    }

    return path;
  }
}
