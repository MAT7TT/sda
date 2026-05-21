package uk.ac.mmu.assignment26.domain;

import uk.ac.mmu.assignment26.domain.config.Wormhole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
  private final int[][] positions;
  private final int rows;
  private final int columns;
  private final Map<Integer, Integer> wormholes = new HashMap<>();

  public Board(int rows, int columns) {
    if (rows <= 0 || columns <= 0) {
      throw new IllegalArgumentException("Board rows and columns must be greater than zero.");
    }

    this.rows = rows;
    this.columns = columns;
    this.positions = new int[rows][columns];

    fillBoard();
  }

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

  public boolean hasWormholeAt(int position) {
    return wormholes.containsKey(position);
  }

  public int getWormholeExit(int position) {
    Integer exit = wormholes.get(position);

    if (exit == null) {
      throw new IllegalArgumentException("No wormhole exists at position " + position);
    }

    return exit;
  }

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
