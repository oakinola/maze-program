package uk.gov.dwp.maze;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Paths.get;

public class Maze {
    private Cell[][] cells;

    private Cell startCell;
    private Cell finishCell;

    private int countOfEntryPoints;
    private int countOfExitPoints;
    private int countOfWalls;
    private int countOfEmptyCells;

    public Maze(final String mazeFileName) {
        loadMaze(mazeFileName);
    }

    private void loadMaze(String mazeFileName) {
        String content = validateFileAndExtractMazeStructure(mazeFileName);
        if (content == null || (content = content.trim()).length() == 0) {
            throw new IllegalArgumentException("Supplied file is empty!");
        }

        String[] lines = content.split("\n");
        cells = new Cell[lines.length][lines[0].length()];

        for (int row = 0; row < getRowCount(); row++ ) {
            if (lines[row].length() != getColumnCount()) {
                throw new IllegalArgumentException("Incorrect Column count for line " + row + ": Expected ==> " + getColumnCount()  + ", got ==> " + lines[row].length());
            }

            for (int col = 0; col < getColumnCount(); col++) {
                Cell cell = new Cell(row, col, lines[row].charAt(col));
                cells[row][col] = cell;
                updateCounters(cell);
            }
        }
    }

    private void updateCounters(Cell cell) {
        switch (cell.getCellType()) {
            case EMPTY : countOfEmptyCells++;
                break;
            case WALL : countOfWalls++;
                break;
            case START : countOfEntryPoints++;
                startCell = cell;
                break;
            case FINISH : countOfExitPoints++;
                finishCell = cell;
                break;
            default : break;
        }
    }

    private String validateFileAndExtractMazeStructure(String mazeFileName) {
        if (mazeFileName == null || mazeFileName.length() == 0) {
            throw new IllegalArgumentException("File name cannot be empty");
        }

        String content = "";
        try {
            Stream<String> stream;
            stream = Files.lines(get(mazeFileName));
            content = stream.collect(Collectors.joining("\n"));
            stream.close();
        } catch (IOException io) {
            io.printStackTrace();
            throw new IllegalArgumentException("Missing or Non-existent file");
        }
        return content;
    }

    private boolean isInvalidCell(int row, int col) {
        return (row < 0 || row >= getRowCount() || col < 0 || col >= getColumnCount());
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getColumnCount() {
        return cells[0].length;
    }

    public int getRowCount() {
        return cells.length;
    }

    public int getCountOfEntryPoints() {
        return countOfEntryPoints;
    }

    public int getCountOfExitPoints() {
        return countOfExitPoints;
    }

    public int getCountOfEmptyCells() {
        return countOfEmptyCells;
    }

    public int getCountOfWalls() {
        return countOfWalls;
    }

    public Cell getFinishCell() {
        return finishCell;
    }

    public Cell getStartCell() {
        return startCell;
    }

    public Optional<Cell> getCellAtLocation(final int row, final int col) {
        return (isInvalidCell(row,col)) ? Optional.empty() : Optional.of(cells[row][col]);
    }
}
