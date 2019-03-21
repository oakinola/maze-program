package uk.gov.dwp.maze;

import java.util.*;

public class Explorer {

    private Cell currentCell;
    private Direction currentDirection;
    private Maze maze;
    private List<Cell> journey = new ArrayList<>();

    public Explorer(Maze maze) {
        this(maze, Direction.NORTH);
    }

    public Explorer(Maze maze, Direction initialDirection) {
        validateParameters(maze, initialDirection);

        this.maze = maze;
        this.currentDirection = initialDirection;
        currentCell = maze.getStartCell();
        journey.add(currentCell);
    }

    private void validateParameters(final Maze maze, final Direction initialDirection) {
        if (maze == null)
            throw new IllegalArgumentException("Explorer needs a maze!");

        if (initialDirection == null)
            throw new IllegalArgumentException("Explorer needs a direction to face!");

        if (initialDirection != Direction.NORTH)
            throw new IllegalArgumentException("Explorer is not facing North!");
    }

    public Maze getMaze() {
        return maze;
    }

    public Explorer turnAround() {
        this.currentDirection = this.currentDirection.turnAround();
        return this;
    }

    public Explorer turnLeft() {
        this.currentDirection = this.currentDirection.turnLeft();
        return this;
    }

    public Explorer turnRight() {
        this.currentDirection = this.currentDirection.turnRight();
        return this;
    }

    public Explorer moveForward() {
        move(this.currentDirection);
        return this;
    }

    public Optional<Cell> getWhatsAhead() {
        return getCellFromDirection(currentDirection);
    }

    public List<Cell> getJourney() {
        return journey;
    }

    public void printExplorerJourney() {
        MazeExplorerUtils.printExplorerJourney(this);
    }

    /**
     * Moves the explorer in the direction specified.
     * If move is possible, advances the explorer forward by setting the currentCell to the cell moved to.
     * It logs the move in the explorer's journey
     * Otherwise remains at the same location.
     * @param inDirection
     */
    private void move(final Direction inDirection) {
        Optional<Cell> nextCell = getCellFromDirection(inDirection);
        if (nextCell.isPresent() && nextCell.get().isAccessible()) {
            currentCell = nextCell.get();
            journey.add(nextCell.get());
        }
    }

    /**
     * Returns all possible moves/directions for the explorer from its current location.
     * @return
     */
    public Set<Direction> getAllPossibleMoves() {
        return getAllPossibleMoves(currentCell.getRow(), currentCell.getColumn());
    }

    /**
     * Returns all possible moves from the passed in coordinates
     * @param row
     * @param col
     * @return
     */
    public Set<Direction> getAllPossibleMovesFromLocation(final int row, final int col) {
        Set<Direction> directions = getAllPossibleMoves(row, col);
        return directions;
    }

    private Set<Direction> getAllPossibleMoves(final int row, final int col) {
        Set<Direction> possibleDirections = new HashSet<>();
        for (Direction dir : Direction.values()) {
            Optional<Cell> nextCell = getCellAtLocation(row + dir.getDy(), col + dir.getDx());
            if (nextCell.isPresent() && nextCell.get().isAccessible()) {
                possibleDirections.add(dir);
            }
        }
        return possibleDirections;
    }

    private Optional<Cell> getCellAtLocation(int row, int col) {
        return maze.getCellAtLocation(row, col);
    }

    private Optional<Cell> getCellFromDirection(Direction fromDirection) {
        return getCellAtLocation(currentCell.getRow() + fromDirection.getDy(), currentCell.getColumn() + fromDirection.getDx());
    }


    public Cell getCurrentCell() {
        return currentCell;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }
}
