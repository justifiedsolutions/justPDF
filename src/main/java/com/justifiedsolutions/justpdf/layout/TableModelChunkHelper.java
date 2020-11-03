/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import java.util.*;

/**
 * Takes the table model and chunks it into groups of rows that should be displayed on the same page. In the basic case,
 * a chunk would be a single row, but in cases where cells span across multiple rows, a chunk would represent the entire
 * span.
 */
final class TableModelChunkHelper {

    /**
     * Prevent instantiation.
     */
    private TableModelChunkHelper() {
    }

    /**
     * Takes the contents of the table, and chunks it into minimum groups of rows that can be displayed on a single
     * page.
     *
     * @param keepTogether specifies if the table should be split across a page boundry or not
     * @param columns      the columns of the table
     * @param rows         the rows of the table
     * @param tableMap     the table map
     * @param tableChunks  the queue of chunks
     */
    static void chunkContent(boolean keepTogether, List<Column> columns, List<Row> rows, Map<TableGridIndex, CellLayout> tableMap, Deque<TableChunk> tableChunks) {
        if (keepTogether) {
            TableChunk chunk = new TableChunk(new TreeSet<>(rows), new ArrayList<>(tableMap.values()));
            tableChunks.add(chunk);
            return;
        }

        int row = 0;
        TableChunk chunk = createChunk(columns, rows, tableMap, row);
        while (chunk != null) {
            tableChunks.add(chunk);
            row = chunk.getLastRow().getIndex() + 1;
            chunk = createChunk(columns, rows, tableMap, row);
        }
    }

    /**
     * Goes through all rows involved in the specified cell, and adds any rows that haven't been checked to the queue to
     * check. Adding the rows to a {@link SortedSet} first eliminates dupes for spans that are multi row and column.
     *
     * @param rows       the rows of the table
     * @param currentRow the current row
     * @param chunkRows  the set of rows already included in the TableChunk
     * @param rowsToDo   the queue of rows to check
     * @param index      the starting index of the cell
     * @param layout     the cell
     */
    private static void addMoreRowsToCheck(List<Row> rows, Row currentRow, SortedSet<Row> chunkRows, Deque<Row> rowsToDo,
                                           TableGridIndex index, CellLayout layout) {
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

    private static TableChunk createChunk(List<Column> columns, List<Row> rows, Map<TableGridIndex, CellLayout> tableMap,
                                          int row) {
        if (row >= rows.size()) {
            return null;
        }

        SortedSet<Row> chunkRows = new TreeSet<>();
        List<CellLayout> layouts = new ArrayList<>();

        Deque<Row> rowsToDo = new ArrayDeque<>();
        rowsToDo.add(rows.get(row));
        while (!rowsToDo.isEmpty()) {
            Row currentRow = rowsToDo.remove();
            createChunkProcessRow(columns, rows, tableMap, currentRow, chunkRows, rowsToDo, layouts);
        }
        return new TableChunk(chunkRows, layouts);
    }

    /**
     * Processes a specific row of a chunk to add all {@link CellLayout}s and look for more rows to check.
     *
     * @param columns    the columns of the table
     * @param rows       the rows of the table
     * @param tableMap   the table map
     * @param currentRow the row being processed
     * @param chunkRows  the set of rows already included in the chunk
     * @param rowsToDo   the queue of rows that need to be checked
     * @param layouts    the list of layouts that are included in the chunk
     */
    private static void createChunkProcessRow(List<Column> columns, List<Row> rows, Map<TableGridIndex, CellLayout> tableMap
            , Row currentRow, SortedSet<Row> chunkRows, Deque<Row> rowsToDo, List<CellLayout> layouts) {
        for (int col = 0; col < columns.size(); col++) {
            TableGridIndex index = new TableGridIndex(currentRow.getIndex(), col);
            CellLayout layout = tableMap.get(index);
            if (layout != null) {
                layouts.add(layout);
                addMoreRowsToCheck(rows, currentRow, chunkRows, rowsToDo, index, layout);
            }
        }
        chunkRows.add(currentRow);
    }
}
