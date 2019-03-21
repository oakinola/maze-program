package uk.gov.dwp.maze;

public class Cell {
    private CellType cellType;
    private int row;
    private int column;

    public Cell(int row, int col, char marker) {
        this.row = row;
        this.column = col;
        this.cellType = CellType.getCellTypeFromMarker(marker);
    }

    public boolean isAccessible() {
        return cellType == CellType.EMPTY
                || cellType == CellType.START
                || cellType == CellType.FINISH;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public CellType getCellType() {
        return cellType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell square = (Cell) o;

        if (column != square.column) return false;
        if (row != square.row) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        result = 31 * result + (cellType != null ? cellType.hashCode() : 0);
        return result;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cell at location [" + row + "][" + column + "] --> ");
        sb.append(this.getCellType().name() + " cell");
        return sb.toString();
    }
}
