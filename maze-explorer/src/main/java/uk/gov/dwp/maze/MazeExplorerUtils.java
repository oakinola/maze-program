package uk.gov.dwp.maze;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MazeExplorerUtils {
    private MazeExplorerUtils(){}

    public static void printExplorerJourney(final Explorer explorer) {

        Cell[][] copiedMaze = makeCopy(explorer.getMaze().getCells());
        List<Cell> explorerJourney = explorer.getJourney();
        for (Cell step : explorerJourney) {
            if (step.getCellType() == CellType.EMPTY) {
                Cell cell = new Cell(step.getRow(), step.getColumn(), CellType.PATH.getMarker());
                copiedMaze[cell.getRow()][cell.getColumn()] = cell;
            }
        }

        System.out.println("Hey!, Here are the places I've explored so far marked by '*' --> \n" + getStringRepresentationOfArray(copiedMaze));
    }


    private static Cell[][] makeCopy(Cell[][] inCells) {
        if (inCells == null)
            return null;

        Cell[][] copiedCells = new Cell[inCells.length][];
        for (int row = 0; row < inCells.length; row++) {
            copiedCells[row] = inCells[row].clone();
        }
        return copiedCells;
    }


    public static String getStringRepresentationOfArray(Cell[][] cells) {
        if (cells == null || cells.length == 0)
            return "";

        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                Cell cell = cells[row][col];
                sb.append(cell.getCellType().getMarker());
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public static String getInformationOfWhatsAheadOfExplorer(final Explorer explorer) {
        Optional<Cell> cellInFront = explorer.getWhatsAhead();
        StringBuilder sb = new StringBuilder();
        sb.append("\nExplorer is at Location [" + explorer.getCurrentCell().getRow() + "]["
                + explorer.getCurrentCell().getColumn() + "] facing --> " + explorer.getCurrentDirection().name() + "\n");

        if (cellInFront.isPresent()) {
            sb.append("In front is --> " + cellInFront.get().toString());
        } else {
            sb.append("There is nothing ahead of me!" );
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public static void printPossibleMoves(Set<Direction> directions, int row, int col) {
        System.out.println("\nPossible moves for explorer from Cell[" + row +"][" + col + "] are -->:");
        directions.forEach(System.out::println);
    }
}
