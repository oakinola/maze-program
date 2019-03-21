package uk.gov.dwp.maze;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static uk.gov.dwp.maze.MazeExplorerUtils.*;

public class ExplorerTest {

    private Maze maze;

    @Before
    public void before() {
        String mazeFileName = "src/main/resources/Maze1.txt";
        this.maze = new Maze(mazeFileName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testForNullMazeSafety(){
        new Explorer(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testForNullDirectionSafety(){
        new Explorer(maze, null);
    }

    //AC1
    @Test
    public void testExplorerCanDropIntoStartPointFacingNorthInitially() {
        Explorer explorer = new Explorer(maze);
        assertThat(maze.getStartCell().isAccessible(), is(equalTo(Boolean.TRUE.booleanValue())));
        assertThat(maze.getStartCell().getCellType(), is(equalTo(CellType.START)));
        assertThat(maze.getStartCell().getRow(), is(equalTo(3)));
        assertThat(maze.getStartCell().getColumn(), is(equalTo(3)));

        //and is facing north?
        assertThat(explorer.getCurrentDirection(), is(equalTo(Direction.NORTH)));
        try {
            new Explorer(maze, Direction.EAST);
        } catch (IllegalArgumentException iae) {
            assertThat(iae.getMessage(), containsString("Explorer is not facing North"));
        }
    }

    //AC2, AC3
    @Test
    public void testExplorerCanMoveForwardAndChangeDirection() {
        Explorer explorer = new Explorer(maze, Direction.NORTH);
        explorer.moveForward(); //can't move north so should remain in place
        assertThat(explorer.getCurrentCell(), is(equalTo(maze.getStartCell())));

        explorer.turnRight().moveForward();
        assertThat(explorer.getCurrentDirection(), is(equalTo(Direction.EAST)));
        assertThat(explorer.getCurrentCell(), is(not(maze.getStartCell())));

        explorer.turnAround().moveForward();//Can move West so should move away
        assertThat(explorer.getCurrentDirection(), is(equalTo(Direction.WEST)));

        assertThat(explorer.getCurrentCell(), is(maze.getStartCell()));
        assertThat(explorer.getCurrentCell().getCellType(), is(equalTo(CellType.START)));
    }

    //AC4
    @Test
    public void testFindOutWhatsInFront() {
        Explorer explorer = new Explorer(maze, Direction.NORTH);
        String whatsAhead = getInformationOfWhatsAheadOfExplorer(explorer);
        assertThat(whatsAhead, containsString("facing --> NORTH"));

        whatsAhead = getInformationOfWhatsAheadOfExplorer(explorer.turnRight());
        assertThat(whatsAhead, containsString("facing --> EAST"));

        whatsAhead = getInformationOfWhatsAheadOfExplorer(explorer.turnAround());
        assertThat(whatsAhead, containsString("facing --> WEST"));

        whatsAhead = getInformationOfWhatsAheadOfExplorer(explorer.turnLeft());
        assertThat(whatsAhead, containsString("facing --> SOUTH"));

        whatsAhead = getInformationOfWhatsAheadOfExplorer(explorer.turnLeft().moveForward().moveForward().moveForward());
        assertThat(whatsAhead, containsString("facing --> EAST"));
    }

    //AC5
    @Test
    public void testExplorerCanDeclareMovementOptions() {
        Explorer explorer = new Explorer(maze, Direction.NORTH);
        //Explorer should be at start point now.

        Set<Direction> directions = explorer.getAllPossibleMoves();
        printPossibleMoves(directions, explorer.getCurrentCell().getRow(), explorer.getCurrentCell().getColumn());
        assertThat(directions, hasItem(Direction.EAST));
        assertThat(directions, not(hasItem(Direction.NORTH)));

        //move to cell[3][11]
        explorer.turnRight().moveForward().moveForward().moveForward().moveForward().moveForward().moveForward()
                .moveForward().moveForward().moveForward();
        directions = explorer.getAllPossibleMoves();
        printPossibleMoves(directions, explorer.getCurrentCell().getRow(), explorer.getCurrentCell().getColumn());
        assertThat(directions, hasItems(Direction.WEST, Direction.SOUTH));
        assertThat(directions, not(hasItems(Direction.NORTH, Direction.EAST)));

        directions = explorer.getAllPossibleMovesFromLocation(13, 11);
        printPossibleMoves(directions, explorer.getCurrentCell().getRow(), explorer.getCurrentCell().getColumn());
        assertThat(directions, hasItems(Direction.EAST, Direction.NORTH));

        directions = explorer.getAllPossibleMovesFromLocation(12, 9);
        printPossibleMoves(directions, explorer.getCurrentCell().getRow(), explorer.getCurrentCell().getColumn());
        assertThat(directions, hasItems(Direction.WEST, Direction.EAST));
    }

    //AC6
    @Test
    public void testExplorerCanReportOnJourney() {
        Explorer explorer = new Explorer(maze, Direction.NORTH);
        explorer.turnRight().moveForward().moveForward().turnAround().moveForward().turnAround().moveForward().moveForward().moveForward();
        printExplorerJourney(explorer);

        explorer.moveForward().moveForward().moveForward().moveForward().turnRight().moveForward();
        printExplorerJourney(explorer);
    }
}
