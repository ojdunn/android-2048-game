package com.owenjdunn.game2048;

/**
 * Cell class: stores the location and value of a cell on a 2D array.
 */
public class Cell2048 implements Comparable<Cell2048> {
    public int row, column, value;

    public Cell2048()
    {
        this(0,0,0);
    }
    public Cell2048 (int r, int c, int v)
    {
        row = r;
        column = c;
        value = v;
    }

    /* must override equals to ensure List::contains() works
     * correctly
     */
    @Override
    public boolean equals(Object c) {
        if (c instanceof Cell2048)
           return compareTo((Cell2048)c) == 0;
        return false;
    }

    @Override
    public int compareTo (Cell2048 other) {
        if (this.row < other.row) return -1;
        if (this.row > other.row) return +1;

        /* break the tie using column */
        if (this.column < other.column) return -1;
        if (this.column > other.column) return +1;

        return this.value - other.value;
    }

    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public int getColumn() {
        return column;
    }
    public void setColumn(int column) {
        this.column = column;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
}