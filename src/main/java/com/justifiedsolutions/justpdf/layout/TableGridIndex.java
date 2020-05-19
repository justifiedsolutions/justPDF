package com.justifiedsolutions.justpdf.layout;

import java.util.Objects;

/**
 * A simple object that represents a location in a 0 based table grid.
 */
class TableGridIndex implements Comparable<TableGridIndex> {
    private final int row;
    private final int column;
    private boolean span = false;

    /**
     * Creates a new index in the table grid.
     *
     * @param row    the row index in the grid
     * @param column the column index in the grid
     */
    TableGridIndex(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableGridIndex that = (TableGridIndex) o;
        return row == that.row &&
                column == that.column;
    }

    @Override
    public String toString() {
        return "TableGridIndex{row=" + row + ", column=" + column + ", span=" + span + '}';
    }

    @Override
    public int compareTo(TableGridIndex o) {
        int result = Integer.compare(this.row, o.row);
        if (result == 0) {
            result = Integer.compare(this.column, o.column);
        }
        return result;
    }

    /**
     * Gets the row index for this grid entry.
     *
     * @return the row index
     */
    int getRow() {
        return row;
    }

    /**
     * Gets the column index for this grid entry.
     *
     * @return the column index
     */
    int getColumn() {
        return column;
    }

    /**
     * Determines if this index is part of a row or column span.
     *
     * @return true if it is part of a span, false otherwise
     */
    boolean isSpan() {
        return span;
    }

    /**
     * Sets this index as part of a span.
     */
    void setSpan() {
        this.span = true;
    }
}
