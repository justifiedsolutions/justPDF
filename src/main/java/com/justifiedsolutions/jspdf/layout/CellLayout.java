package com.justifiedsolutions.jspdf.layout;

import com.justifiedsolutions.jspdf.api.VerticalAlignment;
import com.justifiedsolutions.jspdf.api.content.Cell;
import com.justifiedsolutions.jspdf.api.content.Chunk;
import com.justifiedsolutions.jspdf.api.content.Paragraph;
import com.justifiedsolutions.jspdf.api.content.Phrase;
import com.justifiedsolutions.jspdf.pdf.contents.*;
import com.justifiedsolutions.jspdf.pdf.object.PDFReal;
import com.justifiedsolutions.jspdf.pdf.object.PDFRectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

class CellLayout {
    private final Cell cell;
    private final float cellWidth;
    private final List<ContentLine> contentLines = new ArrayList<>();
    private float cellHeight;
    private PDFRectangle location;

    CellLayout(Cell cell, float cellWidth) {
        this.cell = Objects.requireNonNull(cell);
        this.cellWidth = cellWidth;
        layoutContent();
    }

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
                index.setSpan(true);
            }
        }

        Collections.sort(indices);
        return indices;
    }

    Cell getCell() {
        return cell;
    }

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

    List<GraphicsOperator> getOperators() {
        if (location == null) {
            throw new IllegalStateException("The location must be set first.");
        }

        List<GraphicsOperator> operators = new ArrayList<>();
        operators.addAll(getCellFill());
        operators.addAll(getCellBorders());
        operators.addAll(getCellContents());
        return operators;
    }

    private void layoutContent() {
        ContentLayout contentLayout = getContentLayout();
        ContentLine line = contentLayout.getNextLine(0);
        while (line != null) {
            contentLines.add(line);
            line = contentLayout.getNextLine(0);
        }
    }

    private ContentLayout getContentLayout() {
        float contentWidth = cellWidth - (cell.getPaddingLeft() + cell.getPaddingRight());

        Paragraph content;
        if (cell.getContent() instanceof Paragraph) {
            content = (Paragraph) cell.getContent();
            content.setSpacingBefore(0);
            content.setSpacingAfter(0);
            content.setAlignment(cell.getHorizontalAlignment());
        } else if (cell.getContent() instanceof Phrase) {
            Phrase phrase = (Phrase) cell.getContent();
            content = new Paragraph();
            content.setFont(phrase.getFont());
            content.setLeading(phrase.getLeading());
            content.setAlignment(cell.getHorizontalAlignment());
            for (Chunk chunk : phrase.getChunks()) {
                content.add(chunk);
            }
        } else {
            throw new IllegalArgumentException("Invalid content type for cell: " + cell.getContent().getClass().getSimpleName());
        }
        return new ParagraphLayout(contentWidth, content);
    }

    private float getTextHeight() {
        float height = 0;
        for (ContentLine line : contentLines) {
            height += line.getHeight();
        }
        return height;
    }

    private List<GraphicsOperator> getCellFill() {
        List<GraphicsOperator> result = new ArrayList<>();

        if (cell.getGrayFill() < 1) {
            result.add(new SetGrayFillColor(new PDFReal(cell.getGrayFill())));
            result.add(new CreateRectangularPath(location));
            result.add(new FillPath());
        }

        return result;
    }

    private List<GraphicsOperator> getCellBorders() {
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

    private List<GraphicsOperator> getCellContents() {
        List<GraphicsOperator> result = new ArrayList<>();

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
        boolean hasText = !contentLines.isEmpty();

        if (hasText) {
            result.add(new BeginText());
            result.add(new AbsolutePositionText(new PDFReal(startX), new PDFReal(startY)));
        }

        for (ContentLine line : contentLines) {
            result.addAll(line.getOperators());
        }

        if (hasText) {
            result.add(new EndText());
        }

        Object debug = System.getProperties().get("DrawCellPadding");
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
