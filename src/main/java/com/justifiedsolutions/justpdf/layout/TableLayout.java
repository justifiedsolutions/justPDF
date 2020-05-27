/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.Margin;
import com.justifiedsolutions.justpdf.api.content.Cell;
import com.justifiedsolutions.justpdf.api.content.Content;
import com.justifiedsolutions.justpdf.api.content.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Lays out a {@link Table} into a series of {@link ContentLine}s.
 */
class TableLayout implements ContentLayout {
    private final float spacingBefore;
    private final float spacingAfter;

    private final float tableLeft;
    private final TableModel tableModel;
    private Table table;

    /**
     * Creates a new TableLayout. This should only be called by {@link TableLayoutFactory#getContentLayout(Content)}.
     *
     * @param margin    the margin of the page
     * @param lineWidth the width of the lines to be created by this {@link ContentLayout}
     * @param table     the Table to layout
     */
    TableLayout(Margin margin, float lineWidth, Table table) {
        this.table = Objects.requireNonNull(table);

        this.spacingBefore = this.table.getSpacingBefore();
        this.spacingAfter = this.table.getSpacingAfter();

        float tableWidth = (lineWidth * (this.table.getWidthPercentage() / 100f));
        float[] relativeColumnWidths = this.table.getRelativeColumnWidths();
        float[] columnWidths = new float[relativeColumnWidths.length];
        for (int i = 0; i < relativeColumnWidths.length; i++) {
            columnWidths[i] = tableWidth * relativeColumnWidths[i];
        }

        this.tableModel = new TableModel(columnWidths, this.table.isKeepTogether());
        for (Cell cell : this.table.getCells()) {
            this.tableModel.add(cell);
        }
        this.tableModel.complete();

        tableLeft = margin.getLeft() + ((lineWidth - tableWidth) / 2f);
    }

    @Override
    public float getMinimumHeight() {
        if (table == null) {
            return -1;
        }

        return tableModel.getNextTableChunkHeight();
    }

    @Override
    public ContentLine getNextLine(float verticalPosition) {
        if (table == null) {
            return null;
        }

        List<Cell> originalCells = new ArrayList<>(table.getCells());

        TableChunk tableChunk = tableModel.getNextTableChunk(tableLeft, verticalPosition);

        if (tableChunk != null) {
            for (CellLayout layout : tableChunk.getCellLayouts()) {
                originalCells.remove(layout.getCell());
            }
        }

        if (originalCells.isEmpty()) {
            table = null;
        } else {
            table = new Table(table, originalCells);
        }

        return tableChunk;
    }

    @Override
    public Content getRemainingContent() {
        return table;
    }

    @Override
    public float getSpacingBefore() {
        return spacingBefore;
    }

    @Override
    public float getSpacingAfter() {
        return spacingAfter;
    }

}
