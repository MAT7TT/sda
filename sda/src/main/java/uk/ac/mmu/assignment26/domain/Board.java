package uk.ac.mmu.assignment26.domain;

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

    public void addWormhole(int firstPosition, int secondPosition, List<Player> players) {
        validateWormhole(firstPosition, secondPosition, players);
        wormholes.put(firstPosition, secondPosition);
        wormholes.put(secondPosition, firstPosition);
    }

    private void validateWormhole(int firstPosition, int secondPosition, List<Player> players) {
        if (firstPosition == secondPosition) {
            throw new IllegalArgumentException("Wormhole endpoints must be different.");
        }

        if (!isValidPosition(firstPosition) || !isValidPosition(secondPosition)) {
            throw new IllegalArgumentException("Wormhole endpoint is outside the board.");
        }

        if (wormholes.containsKey(firstPosition) || wormholes.containsKey(secondPosition)) {
            throw new IllegalArgumentException("A wormhole endpoint is already in use.");
        }

        for (Player player : players) {
            int home = player.getHomePosition();
            int end = player.getEndPosition();

            if (firstPosition == home || firstPosition == end
                    || secondPosition == home || secondPosition == end) {
                throw new IllegalArgumentException(
                        "Wormholes cannot be placed on a player's home or end position."
                );
            }
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

    public int[][] getPositions() {
        int[][] copy = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            System.arraycopy(positions[i], 0, copy[i], 0, columns);
        }

        return copy;
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