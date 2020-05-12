package com.justifiedsolutions.jspdf.layout;

import java.util.Objects;

class TableGridIndex implements Comparable<TableGridIndex> {
    private final int row;
    private final int column;
    private boolean span = false;

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

    int getRow() {
        return row;
    }

    int getColumn() {
        return column;
    }

    boolean isSpan() {
        return span;
    }

    void setSpan() {
        this.span = true;
    }
}
