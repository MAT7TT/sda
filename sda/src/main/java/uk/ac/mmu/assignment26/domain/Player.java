package uk.ac.mmu.assignment26.domain;

import uk.ac.mmu.assignment26.domain.path.PathStrategy;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private final List<Integer> path;
    private int pathIndex;
    private int turnCount;

    public Player(String name, Board board, PathStrategy pathStrategy) {
        this.name = name;
        this.path = new ArrayList<>(pathStrategy.buildPath(board));
        this.pathIndex = 0;
        this.turnCount = 0;
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
        return path.indexOf(position);
    }

    public int getPathIndex() {
        return pathIndex;
    }

    public void setPathIndex(int pathIndex) {
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
}
