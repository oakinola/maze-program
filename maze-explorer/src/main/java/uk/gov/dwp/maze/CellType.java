package uk.gov.dwp.maze;

import java.util.Arrays;

public enum CellType {
    EMPTY(' '),
    WALL('X'),
    START('S'),
    FINISH('F'),
    PATH('*'); //Used to show the explorer's path

    private char marker;
    CellType(char marker) {
        this.marker = marker;
    }

    public static CellType getCellTypeFromMarker(char marker){
        return Arrays.stream(CellType.values())
                .filter(l -> l.marker == marker)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unknown marker detected %s.", marker)));
    }

    public char getMarker() {
        return marker;
    }
}
