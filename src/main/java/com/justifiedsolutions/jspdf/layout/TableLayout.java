/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.layout;

import com.justifiedsolutions.jspdf.api.Margin;
import com.justifiedsolutions.jspdf.api.content.Cell;
import com.justifiedsolutions.jspdf.api.content.Content;
import com.justifiedsolutions.jspdf.api.content.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Lays out a {@link Table} into a series of {@link ContentLine}s.
 */
class TableLayout implements ContentLayout {
    private final Margin margin;
    private final float lineWidth;
    private final float spacingBefore;
    private final float spacingAfter;

    private final float tableLeft;
    private final float tableWidth;
    private final float[] columnWidths;
    private Table table;

    /**
     * Creates a new TableLayout. This should only be called by {@link TableLayoutFactory#getContentLayout(Content)}.
     *
     * @param margin    the margin of the page
     * @param lineWidth the width of the lines to be created by this {@link ContentLayout}
     * @param table     the Table to layout
     */
    TableLayout(Margin margin, float lineWidth, Table table) {
        this.margin = margin;
        this.lineWidth = lineWidth;
        this.table = Objects.requireNonNull(table);

        this.spacingBefore = this.table.getSpacingBefore();
        this.spacingAfter = this.table.getSpacingAfter();

        tableWidth = (this.lineWidth * (this.table.getWidthPercentage() / 100f));
        float[] relativeColumnWidths = this.table.getRelativeColumnWidths();
        columnWidths = new float[relativeColumnWidths.length];
        for (int i = 0; i < relativeColumnWidths.length; i++) {
            columnWidths[i] = tableWidth * relativeColumnWidths[i];
        }

        tableLeft = margin.getLeft() + ((lineWidth - tableWidth) / 2f);
    }

    @Override
    public float getMinimumHeight() {
        if (table == null) {
            return -1;
        }

        float height = 0;
        TableLayout layout = new TableLayout(this.margin, this.lineWidth, this.table);
        ContentLine line = layout.getNextLine(0);
        if (this.table.isKeepTogether()) {
            while (line != null) {
                height += line.getHeight();
                line = layout.getNextLine(0);
            }
        } else {
            height = line.getHeight();
        }
        return height;
    }

    @Override
    public ContentLine getNextLine(float verticalPosition) {
        if (table == null) {
            return null;
        }

        List<Cell> originalCells = new ArrayList<>(table.getCells());

        TableRow row = new TableRow(tableWidth, columnWidths, tableLeft, verticalPosition);
        int columnsAdded = 0;
        while (columnsAdded < columnWidths.length) {
            Cell cell = originalCells.remove(0);
            if ((columnsAdded + cell.getColumnSpan()) <= columnWidths.length) {
                row.add(cell);
                columnsAdded += cell.getColumnSpan();
            } else {
                throw new IllegalArgumentException("Cell configuration does not match Table column width.");
            }
        }

        if (originalCells.isEmpty()) {
            table = null;
        } else {
            table = new Table(table, originalCells);
        }

        return row;
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
