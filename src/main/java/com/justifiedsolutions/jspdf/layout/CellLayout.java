package com.justifiedsolutions.jspdf.layout;

import com.justifiedsolutions.jspdf.api.content.Cell;
import com.justifiedsolutions.jspdf.api.content.Chunk;
import com.justifiedsolutions.jspdf.api.content.Paragraph;
import com.justifiedsolutions.jspdf.api.content.Phrase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

class CellLayout {
    private final float cellWidth;
    private final Cell cell;
    private final List<ContentLine> contentLines = new ArrayList<>();

    CellLayout(float cellWidth, Cell cell) {
        this.cellWidth = cellWidth;
        this.cell = Objects.requireNonNull(cell);
        layout();
    }

    Cell getCell() {
        return cell;
    }

    float getMinimumCellHeight() {
        float height = cell.getPaddingTop() + cell.getPaddingBottom() + getTextHeight();
        return Math.max(height, cell.getMinimumHeight());
    }

    float getTextHeight() {
        float height = 0;
        for (ContentLine line : contentLines) {
            height += line.getHeight();
        }
        return height;
    }

    float getCellWidth() {
        return cellWidth;
    }

    List<ContentLine> getContentLines() {
        return Collections.unmodifiableList(contentLines);
    }

    private void layout() {
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

}
