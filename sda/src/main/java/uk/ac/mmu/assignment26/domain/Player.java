package uk.ac.mmu.assignment26.domain;

import uk.ac.mmu.assignment26.domain.path.PathStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        Set<Integer> uniquePositions = new HashSet<>();

        for (Integer position : path) {
            if (position == null || !board.isValidPosition(position)) {
                throw new IllegalArgumentException("Player path contains an invalid board position.");
            }

            if (!uniquePositions.add(position)) {
                throw new IllegalArgumentException("Player must not contain duplicate positions");
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

    public List<Integer> getPathPositions() {
        return List.copyOf(path);
    }
}
