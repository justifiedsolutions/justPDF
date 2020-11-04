/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.content.Cell;

import java.util.*;

/**
 * Class that models a table, supporting the layout of cells.
 */
class TableModel {
    private final boolean keepTogether;
    private final Map<TableGridIndex, CellLayout> tableMap = new HashMap<>();
    private final Map<TableGridIndex, Cell> cellMap = new HashMap<>();
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
        cellMap.put(beginningIndex, cell);

        List<TableGridIndex> cellIndices = CellLayout.getGridIndices(cell, beginningIndex);
        if (cellIndices.size() > 1) {
            beginningIndex.setSpan();
        } else {
            Column column = columns.get(beginningIndex.getColumn());
            column.setMinWidth(CellWidthHelper.getMinimumWidth(cell));
        }
        indices.addAll(cellIndices);
        Collections.sort(indices);
    }

    /**
     * Informs the table model that the last cell has been added and the model can be verified.
     *
     * @throws IllegalStateException if an incorrect number of cells have been added to the table model or if the width
     *                               required for the columns is less than the width available
     */
    void complete() {
        CellWidthHelper.calculateColumnWidths(columns);
        createTableMap();
        createRows();
        validateCells();
        computeNonSpanMinimumRowHeights();
        computeRowSpanMinimumHeights();
        setNonSpanRowHeights();
        TableModelChunkHelper.chunkContent(keepTogether, columns, rows, tableMap, tableChunks);
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
                TableGridIndex index = new TableGridIndex(row.getIndex(), col);
                CellLayout layout = tableMap.get(index);
                if (layout != null) {
                    layout.setLocation(upperLeftX, upperLeftY);
                }
                upperLeftX += column.getActualWidth();
            }
            upperLeftY -= row.getHeight();
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

    private void createTableMap() {
        for (Map.Entry<TableGridIndex, Cell> entry : cellMap.entrySet()) {
            TableGridIndex beginIndex = entry.getKey();
            Cell cell = entry.getValue();
            List<TableGridIndex> cellIndices = CellLayout.getGridIndices(cell, beginIndex);

            Set<Column> cellColumns = new HashSet<>();
            for (TableGridIndex index : cellIndices) {
                cellColumns.add(columns.get(index.getColumn()));
            }

            float width = 0;
            for (Column column : cellColumns) {
                width += column.getActualWidth();
            }

            CellLayout layout = new CellLayout(cell, width);
            tableMap.put(beginIndex, layout);
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
                        row.setHeight(row.getHeight() + diff);
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
            height += row.getHeight();
        }
        return height;
    }

    private void setNonSpanRowHeights() {
        for (Map.Entry<TableGridIndex, CellLayout> entry : tableMap.entrySet()) {
            TableGridIndex index = entry.getKey();
            CellLayout layout = entry.getValue();
            if (!index.isSpan()) {
                Row row = rows.get(index.getRow());
                layout.setCellHeight(row.getHeight());
            }
        }
    }
}
