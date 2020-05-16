package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.pdf.contents.GraphicsOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

class TableChunk implements ContentLine {
    private final SortedSet<TableModel.Row> rows;
    private final List<CellLayout> cellLayouts;

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

    SortedSet<TableModel.Row> getRows() {
        return rows;
    }

    List<CellLayout> getCellLayouts() {
        return cellLayouts;
    }

    TableModel.Row getLastRow() {
        return rows.last();
    }

}
