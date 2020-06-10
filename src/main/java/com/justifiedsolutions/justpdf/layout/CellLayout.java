/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.VerticalAlignment;
import com.justifiedsolutions.justpdf.api.content.Cell;
import com.justifiedsolutions.justpdf.api.content.Paragraph;
import com.justifiedsolutions.justpdf.pdf.contents.*;
import com.justifiedsolutions.justpdf.pdf.object.PDFReal;
import com.justifiedsolutions.justpdf.pdf.object.PDFRectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Lays out the contents of a {@link Cell}.
 */
class CellLayout {
    private final Cell cell;
    private final float cellWidth;
    private final List<ContentLine> contentLines = new ArrayList<>();
    private float cellHeight;
    private PDFRectangle location;

    /**
     * Creates a new CellLayout.
     *
     * @param cell      the cell to layout
     * @param cellWidth the width of the specified cell
     */
    CellLayout(Cell cell, float cellWidth) {
        this.cell = Objects.requireNonNull(cell);
        this.cellWidth = cellWidth;
        layoutContent();
    }

    /**
     * Gets all {@link TableGridIndex}s that the specified {@link Cell} will cover.
     *
     * @param cell           the cell
     * @param beginningIndex the index where the cell starts
     * @return the list of all indices that the cell will cover
     */
    static List<TableGridIndex> getGridIndices(Cell cell, TableGridIndex beginningIndex) {
        List<TableGridIndex> indices = new ArrayList<>();
        for (int i = 0; i < cell.getRowSpan(); i++) {
            int row = beginningIndex.getRow() + i;
            for (int j = 0; j < cell.getColumnSpan(); j++) {
                int column = beginningIndex.getColumn() + j;
                indices.add(new TableGridIndex(row, column));
            }
        }

        if (indices.size() > 1) {
            for (TableGridIndex index : indices) {
                index.setSpan();
            }
        }

        Collections.sort(indices);
        return indices;
    }

    /**
     * Gets the {@link Cell} that is being laid out
     *
     * @return the cell
     */
    Cell getCell() {
        return cell;
    }

    /**
     * Gets the minimum height of the cell including top and bottom padding.
     *
     * @return the minimum height
     */
    float getMinimumCellHeight() {
        float height = cell.getPaddingTop() + cell.getPaddingBottom() + getTextHeight();
        return Math.max(height, cell.getMinimumHeight());
    }

    /**
     * Sets the actual height of the cell.
     *
     * @param cellHeight the cell height
     */
    void setCellHeight(float cellHeight) {
        this.cellHeight = cellHeight;
    }

    /**
     * Sets the {@link Cell}'s location on the page.
     *
     * @param ulx the x coordinate of the upper left corner of the cell
     * @param uly the y coordinate of the upper left corner of the cell
     */
    void setLocation(float ulx, float uly) {
        float lly = uly - cellHeight;
        float urx = ulx + cellWidth;

        location = new PDFRectangle(ulx, lly, urx, uly);
    }

    /**
     * Gets the list of {@link GraphicsOperator}s necessary to draw this cell and it's contents.
     *
     * @return the operators
     */
    List<GraphicsOperator> getOperators() {
        if (location == null) {
            throw new IllegalStateException("The location must be set first.");
        }

        List<GraphicsOperator> operators = new ArrayList<>();
        operators.addAll(drawCellFill());
        operators.addAll(drawCellBorders());
        operators.addAll(drawCellContents());
        operators.addAll(drawCellPadding());
        return operators;
    }

    private void layoutContent() {
        ContentLayout contentLayout = getContentLayout();
        if (contentLayout != null) {
            ContentLine line = contentLayout.getNextLine(0);
            while (line != null) {
                contentLines.add(line);
                line = contentLayout.getNextLine(0);
            }
        }
    }

    private ContentLayout getContentLayout() {
        Paragraph content = TextContentUtility.getParagraph(cell.getContent());
        if (content != null) {
            content.setSpacingBefore(0);
            content.setSpacingAfter(0);
            content.setAlignment(cell.getHorizontalAlignment());
            float contentWidth = cellWidth - (cell.getPaddingLeft() + cell.getPaddingRight());
            return new TextContentLayout(contentWidth, content);
        }
        return null;
    }

    private float getTextHeight() {
        float height = 0;
        for (ContentLine line : contentLines) {
            height += line.getHeight();
        }
        return height;
    }

    private List<GraphicsOperator> drawCellFill() {
        List<GraphicsOperator> result = new ArrayList<>();

        if (cell.getGrayFill() < 1) {
            DeviceGray fill = new DeviceGray(new PDFReal(cell.getGrayFill()));
            result.add(new SetGrayFillColor(fill));
            result.add(new CreateRectangularPath(location));
            result.add(new FillPath());
        }

        return result;
    }

    private List<GraphicsOperator> drawCellBorders() {
        List<Cell.Border> borders = cell.getBorders();

        List<GraphicsOperator> result = new ArrayList<>();
        for (Cell.Border border : borders) {
            switch (border) {
                case LEFT:
                    result.add(new StartPath(location.getLLx(), location.getLLy()));
                    result.add(new AppendToPath(location.getLLx(), location.getURy()));
                    break;
                case RIGHT:
                    result.add(new StartPath(location.getURx(), location.getLLy()));
                    result.add(new AppendToPath(location.getURx(), location.getURy()));
                    break;
                case TOP:
                    result.add(new StartPath(location.getLLx(), location.getURy()));
                    result.add(new AppendToPath(location.getURx(), location.getURy()));
                    break;
                case BOTTOM:
                    result.add(new StartPath(location.getLLx(), location.getLLy()));
                    result.add(new AppendToPath(location.getURx(), location.getLLy()));
                    break;
                case ALL:
                    result.add(new CreateRectangularPath(location));
                    break;
            }
        }
        if (!result.isEmpty()) {
            result.add(0, new SetLineWidth(new PDFReal(cell.getBorderWidth())));
            result.add(new StrokePath());
        }
        return result;
    }

    private List<GraphicsOperator> drawCellContents() {
        List<GraphicsOperator> result = new ArrayList<>();
        if (contentLines.isEmpty()) {
            return result;
        }

        float textHeight = getTextHeight();

        float startY = 0;
        if (VerticalAlignment.TOP == cell.getVerticalAlignment()) {
            startY = location.getURy().getValue() - cell.getPaddingTop();
        } else if (VerticalAlignment.MIDDLE == cell.getVerticalAlignment()) {
            float remainder = location.getHeight().getValue() - textHeight;
            startY = location.getLLy().getValue() + textHeight + (remainder / 2f);
        } else if (VerticalAlignment.BOTTOM == cell.getVerticalAlignment()) {
            startY = location.getLLy().getValue() + textHeight + cell.getPaddingBottom();
        }
        float startX = location.getLLx().getValue() + cell.getPaddingLeft();

        result.add(new BeginText());
        result.add(new AbsolutePositionText(new PDFReal(startX), new PDFReal(startY)));
        for (ContentLine line : contentLines) {
            result.addAll(line.getOperators());
        }
        result.add(new EndText());

        return result;
    }

    /**
     * Method for debugging cell layout issues. Will draw a thin line for the padding border.
     *
     * @return the operators required to draw the padding border. An empty list if the debug is turned off.
     */
    private List<GraphicsOperator> drawCellPadding() {
        List<GraphicsOperator> result = new ArrayList<>();
        String debug = System.getProperty("DrawCellPadding");
        if (debug != null) {
            float textLLx = location.getLLx().getValue() + cell.getPaddingLeft();
            float textLLy = location.getLLy().getValue() + cell.getPaddingBottom();
            float textURx = location.getURx().getValue() - cell.getPaddingRight();
            float textURy = location.getURy().getValue() - cell.getPaddingTop();
            result.add(new SetLineWidth(new PDFReal(.25f)));
            result.add(new CreateRectangularPath(new PDFRectangle(textLLx, textLLy, textURx, textURy)));
            result.add(new StrokePath());
        }
        return result;
    }
}
