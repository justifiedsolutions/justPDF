package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.content.Cell;

import java.util.*;

class TableModel {
    private final boolean keepTogether;
    private final Map<TableGridIndex, CellLayout> tableMap = new HashMap<>();
    private final List<TableGridIndex> indices = new ArrayList<>();
    private final List<Column> columns = new ArrayList<>();
    private final List<Row> rows = new ArrayList<>();
    private final Deque<TableChunk> tableChunks = new ArrayDeque<>();


    TableModel(float[] columnWidths, boolean keepTogether) {
        this.keepTogether = keepTogether;
        for (int i = 0; i < columnWidths.length; i++) {
            columns.add(new Column(i, columnWidths[i]));
        }
    }

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
        for (TableGridIndex index : tableMap.keySet()) {
            if (!index.isSpan()) {
                CellLayout layout = tableMap.get(index);
                Row row = rows.get(index.getRow());
                row.setHeight(layout.getMinimumCellHeight());
            }
        }
    }

    private void computeRowSpanMinimumHeights() {
        for (TableGridIndex index : tableMap.keySet()) {
            if (index.isSpan()) {
                CellLayout layout = tableMap.get(index);
                List<TableGridIndex> cellIndices = CellLayout.getGridIndices(layout.getCell(), index);

                Set<Row> rowSet = new HashSet<>();
                for (TableGridIndex spanIndex : cellIndices) {
                    Row row = rows.get(spanIndex.getRow());
                    rowSet.add(row);
                }

                float height = 0;
                for (Row row : rowSet) {
                    height += row.height;
                }

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

    private void setNonSpanRowHeights() {
        for (TableGridIndex index : tableMap.keySet()) {
            if (!index.isSpan()) {
                CellLayout layout = tableMap.get(index);
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
            for (int col = 0; col < columns.size(); col++) {
                TableGridIndex index = new TableGridIndex(currentRow.index, col);
                CellLayout layout = tableMap.get(index);
                if (layout != null) {
                    layouts.add(layout);
                    List<TableGridIndex> indices = CellLayout.getGridIndices(layout.getCell(), index);
                    for (TableGridIndex cellIndex : indices) {
                        Row cellRow = rows.get(cellIndex.getRow());
                        if (!currentRow.equals(cellRow) && !chunkRows.contains(cellRow)) {
                            rowsToDo.add(cellRow);
                        }
                    }
                }
            }
            chunkRows.add(currentRow);
        }
        return new TableChunk(chunkRows, layouts);
    }

    static class Row implements Comparable<Row> {
        private final int index;
        private float height = 0;

        private Row(int index) {
            this.index = index;
        }

        @Override
        public int hashCode() {
            return Objects.hash(index);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
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
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Column column = (Column) o;
            return Float.compare(column.index, index) == 0;
        }
    }
}
