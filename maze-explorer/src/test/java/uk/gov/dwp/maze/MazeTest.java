package uk.gov.dwp.maze;

import static org.hamcrest.MatcherAssert.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.*;

public class MazeTest {

    private Maze maze;

    @Before
    public void before() {
        String mazeFileName = "src/main/resources/Maze1.txt";
        this.maze = new Maze(mazeFileName);
    }

    @Test
    public void testEmptyMazeFileFails() {
        String emptyMazeFile = "src/main/resources/EmptyMazeFile.txt";
        try {
            this.maze = new Maze(emptyMazeFile);
        } catch (IllegalArgumentException iae) {
            assertThat(iae.getMessage(), containsString("Supplied file is empty!"));
        }
    }

    @Test
    public void testMazeIsNullPointerSafe() {
        Assert.assertNotNull(maze);
        Assert.assertNotNull(maze.getCells());
    }

    //AC1
    @Test
    public void testMazeHasOnlyOneEntryAndExitPoints() {
        assertThat(maze.getCountOfEntryPoints(), is(equalTo(1)));
        assertThat(maze.getCountOfExitPoints(), is(equalTo(1)));
        assertThat(maze.getStartCell(), is(not(nullValue())));
        assertThat(maze.getFinishCell(), is(not(nullValue())));
    }


    //AC2
    @Test
    public void testMazeWallCount() {
        int totalCells = maze.getRowCount() * maze.getColumnCount();
        int nonWallCells = maze.getCountOfEmptyCells() + maze.getCountOfEntryPoints() + maze.getCountOfExitPoints();
        assertThat(totalCells - nonWallCells, is(equalTo(maze.getCountOfWalls())));
    }

    //AC2
    @Test
    public void testMazeEmptySpacesCount() {
        int totalCells = maze.getRowCount() * maze.getColumnCount();
        int nonEmptyCells = maze.getCountOfWalls() + maze.getCountOfEntryPoints() + maze.getCountOfExitPoints();
        assertThat(totalCells - nonEmptyCells, is(equalTo(maze.getCountOfEmptyCells())));
    }

    //AC1
    @Test
    public void testMazeConsistsOfAllCellTypes() {
        assertThat(maze.getCountOfEntryPoints(), is(greaterThan(0)));
        assertThat(maze.getCountOfExitPoints(), is(greaterThan(0)));
        assertThat(maze.getCountOfWalls(), is(greaterThan(0)));
        assertThat(maze.getCountOfEmptyCells(), is(greaterThan(0)));
    }

    //AC3
    @Test
    public void testWhatExistsAtCoordinates() {
        assertThat(maze.getCellAtLocation(0,0).get().getCellType(), is(equalTo(CellType.WALL)));
        assertThat(maze.getCellAtLocation(3,3).get().getCellType(), is(equalTo(CellType.START)));
        assertThat(maze.getCellAtLocation(1,5).get().getCellType(), is(not(equalTo(CellType.WALL))));
        assertThat(maze.getCellAtLocation(14,1).get().getCellType(), is(equalTo(CellType.FINISH)));
        assertThat(maze.getCellAtLocation(12,13).get().getCellType(), is(equalTo(CellType.EMPTY)));
    }

    @Test(expected = NoSuchElementException.class)
    public void testInvalidMazeLocations() {
        maze.getCellAtLocation(-1,13).get().getCellType();
        maze.getCellAtLocation(2,15).get().getCellType();
        maze.getCellAtLocation(12,-3).get().getCellType();
    }
}
