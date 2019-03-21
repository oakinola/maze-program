package uk.gov.dwp.maze;

public enum Direction {
    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0);

    private int dx, dy;

    Direction(int x, int y) {
        this.dx = x;
        this.dy = y;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public Direction turnRight() {
        switch(this) {
            case NORTH :
                return Direction.EAST;
            case EAST :
                return Direction.SOUTH;
            case SOUTH :
                return Direction.WEST;
            case WEST :
                return Direction.NORTH;
            default:
                throw new RuntimeException("Invalid value") ;
        }
    }

    public Direction turnLeft() {
        switch(this) {
            case NORTH :
                return Direction.WEST;
            case EAST :
                return Direction.NORTH;
            case SOUTH :
                return Direction.EAST;
            case WEST :
                return Direction.SOUTH;
            default:
                throw new RuntimeException("Invalid value") ;
        }
    }

    public Direction turnAround() {
        switch(this) {
            case NORTH :
                return Direction.SOUTH;
            case EAST :
                return Direction.WEST;
            case SOUTH :
                return Direction.NORTH;
            case WEST :
                return Direction.EAST;
            default:
                throw new RuntimeException("Invalid value") ;
        }
    }
}
