/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.layout;

import com.justifiedsolutions.jspdf.api.VerticalAlignment;
import com.justifiedsolutions.jspdf.api.content.Cell;
import com.justifiedsolutions.jspdf.pdf.contents.*;
import com.justifiedsolutions.jspdf.pdf.object.PDFReal;
import com.justifiedsolutions.jspdf.pdf.object.PDFRectangle;

import java.util.ArrayList;
import java.util.List;

class TableRow implements ContentLine {

    private final List<GraphicsOperator> operators = new ArrayList<>();

    private final float tableWidth;
    private final float[] columnWidths;
    private final float ulx;
    private final float uly;
    private final List<Cell> cells = new ArrayList<>();
    private final List<CellLayout> cellLayouts = new ArrayList<>();
    private boolean hasAllCells = false;

    TableRow(float tableWidth, float[] columnWidths, float ulx, float uly) {
        this.tableWidth = tableWidth;
        this.columnWidths = columnWidths;
        this.ulx = ulx;
        this.uly = uly;
    }

    @Override
    public float getWidth() {
        return tableWidth;
    }

    @Override
    public float getHeight() {
        if (!hasAllCells) {
            throw new IllegalStateException("Cannot get height until row has all cells.");
        }
        float height = 0;
        for (CellLayout layout : cellLayouts) {
            height = Math.max(height, layout.getMinimumCellHeight());
        }
        return height;
    }

    @Override
    public List<GraphicsOperator> getOperators() {
        if (!hasAllCells) {
            throw new IllegalStateException("Cannot get operators until row has all cells.");
        }
        return operators;
    }

    void add(Cell cell) {
        if (hasAllCells) {
            throw new IllegalArgumentException("Row already has all required cells.");
        }

        cells.add(cell);

        int numColumns = 0;
        for (Cell c : cells) {
            numColumns += c.getColumnSpan();
        }

        if (numColumns > columnWidths.length) {
            throw new IllegalArgumentException("Total column span exceeds number of columns.");
        } else if (numColumns == columnWidths.length) {
            hasAllCells = true;
            layoutRow();
        }
    }

    private void layoutRow() {
        createCellLayouts();
        drawCells();
    }

    private void createCellLayouts() {
        int currentColumn = 0;
        for (Cell cell : cells) {
            float cellWidth = 0;
            for (int j = currentColumn; j < currentColumn + cell.getColumnSpan(); j++) {
                cellWidth += columnWidths[j];
            }
            currentColumn += cell.getColumnSpan();
            cellLayouts.add(new CellLayout(cellWidth, cell));
        }
    }

    private void drawCells() {
        float rowHeight = getHeight();
        float llx = ulx;
        float lly = (uly - rowHeight);
        for (CellLayout layout : cellLayouts) {
            float width = layout.getCellWidth();
            operators.addAll(getCellFill(layout, llx, lly, width, rowHeight));
            operators.addAll(getCellBorders(layout, llx, lly, width, rowHeight));
            operators.addAll(getCellContents(layout, llx, lly, width, rowHeight));
            llx += width;
        }
    }

    private List<GraphicsOperator> getCellFill(CellLayout layout, float llx, float lly, float width, float height) {
        List<GraphicsOperator> result = new ArrayList<>();

        Cell cell = layout.getCell();
        if (cell.getGrayFill() < 1) {
            result.add(new SetGrayFillColor(new PDFReal(cell.getGrayFill())));
            result.add(new CreateRectangularPath(new PDFRectangle(llx, lly, llx + width, lly + height)));
            result.add(new FillPath());
        }

        return result;
    }

    private List<GraphicsOperator> getCellBorders(CellLayout layout, float llx, float lly, float width, float height) {
        List<Cell.Border> borders = layout.getCell().getBorders();

        List<GraphicsOperator> result = new ArrayList<>();
        for (Cell.Border border : borders) {
            switch (border) {
                case LEFT:
                    result.add(new StartPath(new PDFReal(llx), new PDFReal(lly)));
                    result.add(new AppendToPath(new PDFReal(llx), new PDFReal(lly + height)));
                    break;
                case RIGHT:
                    result.add(new StartPath(new PDFReal(llx + width), new PDFReal(lly)));
                    result.add(new AppendToPath(new PDFReal(llx + width), new PDFReal(lly + height)));
                    break;
                case TOP:
                    result.add(new StartPath(new PDFReal(llx), new PDFReal(lly + height)));
                    result.add(new AppendToPath(new PDFReal(llx + width), new PDFReal(lly + height)));
                    break;
                case BOTTOM:
                    result.add(new StartPath(new PDFReal(llx), new PDFReal(lly)));
                    result.add(new AppendToPath(new PDFReal(llx + width), new PDFReal(lly)));
                    break;
                case ALL:
                    result.add(new CreateRectangularPath(new PDFRectangle(llx, lly, llx + width, lly + height)));
                    break;
            }
        }
        if (!result.isEmpty()) {
            result.add(0, new SetLineWidth(new PDFReal(layout.getCell().getBorderWidth())));
            result.add(new StrokePath());
        }
        return result;
    }

    private List<GraphicsOperator> getCellContents(CellLayout layout, float llx, float lly, float width, float height) {
        List<GraphicsOperator> result = new ArrayList<>();
        Cell cell = layout.getCell();

        float textHeight = layout.getTextHeight();

        float startY = 0;
        if (VerticalAlignment.TOP == cell.getVerticalAlignment()) {
            startY = lly + height - cell.getPaddingTop();
        } else if (VerticalAlignment.MIDDLE == cell.getVerticalAlignment()) {
            float remainder = height - textHeight;
            startY = lly + textHeight + (remainder / 2f);
        } else if (VerticalAlignment.BOTTOM == cell.getVerticalAlignment()) {
            startY = lly + textHeight + cell.getPaddingBottom();
        }

        float startX = llx + cell.getPaddingLeft();
        boolean hasText = !layout.getContentLines().isEmpty();

        if (hasText) {
            result.add(new BeginText());
            result.add(new AbsolutePositionText(new PDFReal(startX), new PDFReal(startY)));
        }

        for (ContentLine line : layout.getContentLines()) {
            result.addAll(line.getOperators());
        }

        if (hasText) {
            result.add(new EndText());
        }

        float textLLx = llx + cell.getPaddingLeft();
        float textLLy = lly + cell.getPaddingBottom();
        float textURx = llx + width - cell.getPaddingRight();
        float textURy = lly + height - cell.getPaddingTop();
        result.add(new SetLineWidth(new PDFReal(.25f)));
        result.add(new CreateRectangularPath(new PDFRectangle(textLLx, textLLy, textURx, textURy)));
        result.add(new StrokePath());

        return result;
    }
}
