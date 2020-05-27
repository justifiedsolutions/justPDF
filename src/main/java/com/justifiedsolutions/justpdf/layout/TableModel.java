package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.content.Cell;

import java.util.*;

/**
 * Class that models a table, supporting the layout of cells.
 */
class TableModel {
    private final boolean keepTogether;
    private final Map<TableGridIndex, CellLayout> tableMap = new HashMap<>();
    private final List<TableGridIndex> indices = new ArrayList<>();
    private final List<Column> columns = new ArrayList<>();
    private final List<Row> rows = new ArrayList<>();
    private final Deque<TableChunk> tableChunks = new ArrayDeque<>();


    /**
     * Creates a new TableModel with the specified number of columns and column widths.
     *
     * @param columnWidths the widths of each of the columns
     * @param keepTogether true if the entire table should be kept together on a page, false otherwise
     */
    TableModel(float[] columnWidths, boolean keepTogether) {
        this.keepTogether = keepTogether;
        for (int i = 0; i < columnWidths.length; i++) {
            columns.add(new Column(i, columnWidths[i]));
        }
    }

    /**
     * Adds the specified Cell to the table model.
     *
     * @param cell the cell to add
     */
    void add(Cell cell) {
        TableGridIndex beginningIndex = getNextIndex();
        List<TableGridIndex> cellIndices = CellLayout.getGridIndices(cell, beginningIndex);
        if (cellIndices.size() > 1) {
            beginningIndex.setSpan();
        }
        indices.addAll(cellIndices);
        Collections.sort(indices);

        Set<Column> cellColumns = new HashSet<>();
        for (TableGridIndex index : cellIndices) {
            cellColumns.add(columns.get(index.getColumn()));
        }

        float width = 0;
        for (Column column : cellColumns) {
            width += column.width;
        }

        CellLayout layout = new CellLayout(cell, width);
        tableMap.put(beginningIndex, layout);
    }

    /**
     * Informs the table model that the last cell has been added and the model can be verified.
     *
     * @throws IllegalStateException if an incorrect number of cells have been added to the table model
     */
    void complete() {
        createRows();
        validateCells();
        computeNonSpanMinimumRowHeights();
        computeRowSpanMinimumHeights();
        setNonSpanRowHeights();
        chunkContent();
    }

    float getNextTableChunkHeight() {
        float height = -1;
        TableChunk next = tableChunks.peek();
        if (next != null) {
            height = next.getHeight();
        }
        return height;
    }

    TableChunk getNextTableChunk(float ulx, float uly) {
        TableChunk result = tableChunks.remove();

        float upperLeftY = uly;
        for (Row row : result.getRows()) {
            float upperLeftX = ulx;
            for (int col = 0; col < columns.size(); col++) {
                Column column = columns.get(col);
                TableGridIndex index = new TableGridIndex(row.index, col);
                CellLayout layout = tableMap.get(index);
                if (layout != null) {
                    layout.setLocation(upperLeftX, upperLeftY);
                }
                upperLeftX += column.width;
            }
            upperLeftY -= row.height;
        }

        return result;
    }

    private TableGridIndex getNextIndex() {
        int row = 0;
        while (true) {
            for (int col = 0; col < columns.size(); col++) {
                TableGridIndex index = new TableGridIndex(row, col);
                if (!indices.contains(index)) {
                    return index;
                }
            }
            row++;
        }
    }

    private void createRows() {
        int row = 0;
        while (indices.contains(new TableGridIndex(row, 0))) {
            rows.add(new Row(row));
            row++;
        }
    }

    private void validateCells() {
        TableGridIndex nextIndex = getNextIndex();
        if (nextIndex.getRow() != rows.size() || nextIndex.getColumn() != 0) {
            throw new IllegalStateException("Table is incomplete.");
        }
    }

    private void computeNonSpanMinimumRowHeights() {
        for (Map.Entry<TableGridIndex, CellLayout> entry : tableMap.entrySet()) {
            TableGridIndex index = entry.getKey();
            CellLayout layout = entry.getValue();
            if (!index.isSpan()) {
                Row row = rows.get(index.getRow());
                row.setHeight(layout.getMinimumCellHeight());
            }
        }
    }

    private void computeRowSpanMinimumHeights() {
        for (Map.Entry<TableGridIndex, CellLayout> entry : tableMap.entrySet()) {
            TableGridIndex index = entry.getKey();
            CellLayout layout = entry.getValue();
            if (index.isSpan()) {
                Set<Row> rowSet = getRows(layout, index);
                float height = getHeightOfRows(rowSet);
                if (height < layout.getMinimumCellHeight()) {
                    layout.setCellHeight(layout.getMinimumCellHeight());
                    float diff = ((layout.getMinimumCellHeight() - height) / (float) rowSet.size());
                    for (Row row : rowSet) {
                        row.height += diff;
                    }
                } else {
                    layout.setCellHeight(height);
                }
            }

        }
    }

    private Set<Row> getRows(CellLayout layout, TableGridIndex index) {
        List<TableGridIndex> cellIndices = CellLayout.getGridIndices(layout.getCell(), index);
        Set<Row> rowSet = new HashSet<>();
        for (TableGridIndex spanIndex : cellIndices) {
            Row row = rows.get(spanIndex.getRow());
            rowSet.add(row);
        }
        return rowSet;
    }

    private float getHeightOfRows(Set<Row> rowSet) {
        float height = 0;
        for (Row row : rowSet) {
            height += row.height;
        }
        return height;
    }

    private void setNonSpanRowHeights() {
        for (Map.Entry<TableGridIndex, CellLayout> entry : tableMap.entrySet()) {
            TableGridIndex index = entry.getKey();
            CellLayout layout = entry.getValue();
            if (!index.isSpan()) {
                Row row = rows.get(index.getRow());
                layout.setCellHeight(row.height);
            }
        }
    }

    private void chunkContent() {
        if (keepTogether) {
            TableChunk chunk = new TableChunk(new TreeSet<>(rows), new ArrayList<>(tableMap.values()));
            tableChunks.add(chunk);
            return;
        }

        int row = 0;
        TableChunk chunk = createChunk(row);
        while (chunk != null) {
            tableChunks.add(chunk);
            row = chunk.getLastRow().index + 1;
            chunk = createChunk(row);
        }
    }

    private TableChunk createChunk(int row) {
        if (row >= rows.size()) {
            return null;
        }

        SortedSet<Row> chunkRows = new TreeSet<>();
        List<CellLayout> layouts = new ArrayList<>();

        Deque<Row> rowsToDo = new ArrayDeque<>();
        rowsToDo.add(rows.get(row));
        while (!rowsToDo.isEmpty()) {
            Row currentRow = rowsToDo.remove();
            createChunkProcessRow(currentRow, chunkRows, rowsToDo, layouts);
        }
        return new TableChunk(chunkRows, layouts);
    }

    /**
     * Processes a specific row of a chunk to add all {@link CellLayout}s and look for more rows to check.
     *
     * @param currentRow the row being processed
     * @param chunkRows  the set of rows already included in the chunk
     * @param rowsToDo   the queue of rows that need to be checked
     * @param layouts    the list of layouts that are included in the chunk
     */
    private void createChunkProcessRow(Row currentRow, SortedSet<Row> chunkRows, Deque<Row> rowsToDo, List<CellLayout> layouts) {
        for (int col = 0; col < columns.size(); col++) {
            TableGridIndex index = new TableGridIndex(currentRow.index, col);
            CellLayout layout = tableMap.get(index);
            if (layout != null) {
                layouts.add(layout);
                addMoreRowsToCheck(currentRow, chunkRows, rowsToDo, index, layout);
            }
        }
        chunkRows.add(currentRow);
    }

    /**
     * Goes through all rows involved in the specified cell, and adds any rows that haven't been checked to the queue to
     * check. Adding the rows to a {@link SortedSet} first eliminates dupes for spans that are multi row and column.
     *
     * @param currentRow the current row
     * @param chunkRows  the set of rows already included in the TableChunk
     * @param rowsToDo   the queue of rows to check
     * @param index      the starting index of the cell
     * @param layout     the cell
     */
    private void addMoreRowsToCheck(Row currentRow, SortedSet<Row> chunkRows, Deque<Row> rowsToDo, TableGridIndex index, CellLayout layout) {
        SortedSet<Row> rowsToCheck = new TreeSet<>();
        List<TableGridIndex> layoutIndices = CellLayout.getGridIndices(layout.getCell(), index);
        for (TableGridIndex cellIndex : layoutIndices) {
            Row cellRow = rows.get(cellIndex.getRow());
            if (!currentRow.equals(cellRow) && !chunkRows.contains(cellRow)) {
                rowsToCheck.add(cellRow);
            }
        }
        rowsToDo.addAll(rowsToCheck);
    }

    /**
     * Models a row in a table. Specifies the index and height of the row.
     */
    static class Row implements Comparable<Row> {
        private final int index;
        private float height;

        private Row(int index) {
            this.index = index;
        }

        @Override
        public int hashCode() {
            return Objects.hash(index);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Row row = (Row) o;
            return index == row.index;
        }

        @Override
        public int compareTo(Row o) {
            return Integer.compare(this.index, o.index);
        }

        float getHeight() {
            return height;
        }

        private void setHeight(float height) {
            this.height = Math.max(this.height, height);
        }
    }

    /**
     * Models a column in a table. Specifies the index and width of the column.
     */
    private static class Column {
        private final int index;
        private final float width;

        private Column(int index, float width) {
            this.index = index;
            this.width = width;
        }

        @Override
        public int hashCode() {
            return Objects.hash(index);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Column column = (Column) o;
            return Float.compare(column.index, index) == 0;
        }
    }
}
