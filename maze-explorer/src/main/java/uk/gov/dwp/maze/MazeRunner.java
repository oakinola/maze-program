package uk.gov.dwp.maze;

import static uk.gov.dwp.maze.MazeExplorerUtils.*;

public class MazeRunner {
    public static void main(String[] args) {
        Maze maze = new Maze("src/main/resources/Maze1.txt");

        System.out.println(getStringRepresentationOfArray(maze.getCells()));

        System.out.println("\nNumber of Walls " + maze.getCountOfWalls());
        System.out.println("Number of Empty Spaces " + maze.getCountOfEmptyCells());
        System.out.println("Number of Start points " + maze.getCountOfEntryPoints());
        System.out.println("Number of Exits " + maze.getCountOfExitPoints());

        System.out.println("\nStart Cell Info --> " + maze.getStartCell().toString());
        System.out.println("Exit Cell Info -->  " + maze.getFinishCell().toString());

        System.out.println("\nCell at location [11][7] is --> " + maze.getCellAtLocation(11,7));
        System.out.println("\nCell at location [14][1] is --> " + maze.getCellAtLocation(14,1));
        System.out.println("\nCell at location [12][7] is --> " + maze.getCellAtLocation(12,7));

        System.out.println("\n*****Explorer Stuff *****");
        Explorer explorer = new Explorer(maze);

        System.out.println("\nExplorer is at --> " + explorer.getCurrentCell().toString());
        System.out.println("\nExplorer's initial direction is --> " + explorer.getCurrentDirection().name());

        explorer.turnRight().moveForward().moveForward();
        System.out.println("\nExplorer's direction is now --> " + explorer.getCurrentDirection().name());
        System.out.println("\nExplorer is now at --> " + explorer.getCurrentCell().toString());

        explorer.turnAround();
        explorer.turnLeft().turnLeft();
        System.out.println("\nExplorer's direction is now --> " + explorer.getCurrentDirection().name());

        System.out.println("\nExplorer's journey so far: ");
        explorer.printExplorerJourney();
        explorer.moveForward().moveForward();

        System.out.println("\n What's in front of explorer? " + explorer.getWhatsAhead().get().toString());
        getInformationOfWhatsAheadOfExplorer(explorer);

        System.out.println("\nFrom this location my movement options are :");
        printPossibleMoves(explorer.getAllPossibleMoves(), explorer.getCurrentCell().getRow(), explorer.getCurrentCell().getColumn());

        System.out.println("\nExplorer's journey so far: ");
        explorer.printExplorerJourney();

    }
}
