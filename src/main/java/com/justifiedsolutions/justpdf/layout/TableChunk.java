package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.pdf.contents.GraphicsOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

/**
 * Represents a minimal set of rows that can be displayed at once. If all the cells in row have a {@code rowspan == 1},
 * then it would represent just that row. For rows with a {@code rowspan > 1}, the {@code TableChunk} will contain the
 * necessary rows to show all of the multi-row cells in their entirty.
 */
class TableChunk implements ContentLine {
    private final SortedSet<TableModel.Row> rows;
    private final List<CellLayout> cellLayouts;

    /**
     * Creates a new TableChunk.
     *
     * @param rows        the rows included in the chunk
     * @param cellLayouts all of the cells included in the chunk
     */
    TableChunk(SortedSet<TableModel.Row> rows, List<CellLayout> cellLayouts) {
        this.rows = rows;
        this.cellLayouts = cellLayouts;
    }

    @Override
    public float getHeight() {
        float height = 0;
        for (TableModel.Row row : rows) {
            height += row.getHeight();
        }
        return height;
    }

    @Override
    public List<GraphicsOperator> getOperators() {
        List<GraphicsOperator> operators = new ArrayList<>();
        for (CellLayout layout : cellLayouts) {
            operators.addAll(layout.getOperators());
        }
        return operators;
    }

    /**
     * Gets the {@link SortedSet} of {@link com.justifiedsolutions.justpdf.layout.TableModel.Row}s included in the
     * chunk.
     *
     * @return the set of one or more rows included in the chunk
     */
    SortedSet<TableModel.Row> getRows() {
        return rows;
    }

    /**
     * The list of cells included in the chunk.
     *
     * @return the cells of the chunk
     */
    List<CellLayout> getCellLayouts() {
        return cellLayouts;
    }

    /**
     * Gets the last row of the chunk.
     *
     * @return the last row
     */
    TableModel.Row getLastRow() {
        return rows.last();
    }

}
