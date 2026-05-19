package uk.ac.mmu.assignment26.domain;

import uk.ac.mmu.assignment26.domain.path.PathStrategy;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final List<Integer> path;
    private int pathIndex;
    private int turnCount;

    public Player(String name, Board board, PathStrategy pathStrategy) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Player name must not be blank.");
        }

        if (board == null) {
            throw new IllegalArgumentException("Board must not be null.");
        }

        if (pathStrategy == null) {
            throw new IllegalArgumentException("Path strategy must not be null.");
        }

        List<Integer> builtPath = pathStrategy.buildPath(board);
        validatePath(board, builtPath);

        this.name = name;
        this.path = new ArrayList<>(builtPath);
        this.pathIndex = 0;
        this.turnCount = 0;
    }

    private void validatePath(Board board, List<Integer> path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Player path must not be empty.");
        }

        if (path.size() < 2) {
            throw new IllegalArgumentException("Player path must contain at least a home and end position.");
        }

        if (path.size() != board.getCellCount()) {
            throw new IllegalArgumentException("Player path must include every board position.");
        }

        for (Integer position : path) {
            if (position == null || !board.isValidPosition(position)) {
                throw new IllegalArgumentException("Player path contains an invalid board position.");
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getCurrentPosition() {
        return path.get(pathIndex);
    }

    public int getHomePosition() {
        return path.getFirst();
    }

    public int getEndPosition() {
        return path.getLast();
    }

    public int findPathIndexOfPosition(int position) {
        int index = path.indexOf(position);

        if (index == -1) {
            throw new IllegalArgumentException("Position is not on the player's path.");
        }

        return index;
    }

    public int getPathIndex() {
        return pathIndex;
    }

    public void setPathIndex(int pathIndex) {
        if (pathIndex < 0 || pathIndex >= path.size()) {
            throw new IllegalArgumentException("Path index is outside the player's path.");
        }

        this.pathIndex = pathIndex;
    }

    public int getPathLength() {
        return path.size();
    }

    public boolean isAtEnd() {
        return pathIndex == path.size() - 1;
    }

    public void incrementTurnCount() {
        turnCount++;
    }

    public int getTurnCount() {
        return turnCount;
    }

    public String getPathDescription() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < path.size(); i++) {
            if (i == 0) {
                sb.append("Home (Position ").append(path.get(i)).append(")");
            }
            else if (i == path.size() - 1) {
                sb.append(", End (Position ").append(path.get(i)).append(")");
            }
            else {
                sb.append(", ").append(path.get(i));
            }
        }

        return sb.toString();
    }

    public List<Integer> getPathPositions() {
        return List.copyOf(path);
    }
}
