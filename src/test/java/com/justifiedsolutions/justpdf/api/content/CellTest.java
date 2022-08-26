/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api.content;

import com.justifiedsolutions.justpdf.api.HorizontalAlignment;
import com.justifiedsolutions.justpdf.api.VerticalAlignment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CellTest {

    private Cell cell;

    @BeforeEach
    void setup() {
        cell = new Cell();
    }

    @Test
    void contentConstructor() {
        Phrase input = new Phrase("text");
        cell = new Cell(input);
        assertEquals(input, cell.getContent());
    }

    @Test
    void setContentChunk() {
        Chunk input = new Chunk("text");
        assertThrows(IllegalArgumentException.class, () -> cell.setContent(input));
    }

    @Test
    void setContentParagraph() {
        Paragraph input = new Paragraph("text");
        cell.setContent(input);
        assertEquals(input, cell.getContent());
    }

    @Test
    void setContentPhrase() {
        Phrase input = new Phrase("text");
        cell.setContent(input);
        assertEquals(input, cell.getContent());
    }

    @Test
    void rowSpan() {
        int input = 5;
        cell.setRowSpan(input);
        assertEquals(input, cell.getRowSpan());
    }

    @Test
    void columnSpan() {
        int input = 5;
        cell.setColumnSpan(input);
        assertEquals(input, cell.getColumnSpan());
    }

    @Test
    void horizontalAlignment() {
        HorizontalAlignment input = HorizontalAlignment.CENTER;
        cell.setHorizontalAlignment(input);
        assertEquals(input, cell.getHorizontalAlignment());
    }

    @Test
    void verticalAlignment() {
        VerticalAlignment input = VerticalAlignment.MIDDLE;
        cell.setVerticalAlignment(input);
        assertEquals(input, cell.getVerticalAlignment());
    }

    @Test
    void minHeight() {
        float input = 5;
        cell.setMinimumHeight(input);
        assertEquals(input, cell.getMinimumHeight());
    }

    @Test
    void padding() {
        float input = 5;
        cell.setPadding(input);
        assertEquals(input, cell.getPaddingTop());
        assertEquals(input, cell.getPaddingBottom());
        assertEquals(input, cell.getPaddingLeft());
        assertEquals(input, cell.getPaddingRight());
    }

    @Test
    void borders() {
        List<Cell.Border> expected = new ArrayList<>();

        cell.setBorders();
        assertEquals(expected, cell.getBorders());

        cell.setBorders(Cell.Border.TOP);
        expected.add(Cell.Border.TOP);
        assertEquals(expected, cell.getBorders());

        expected.clear();
        cell.setBorders(Cell.Border.TOP, Cell.Border.BOTTOM);
        expected.add(Cell.Border.TOP);
        expected.add(Cell.Border.BOTTOM);
        assertEquals(expected, cell.getBorders());

        expected.clear();
        cell.setBorders(Cell.Border.TOP, Cell.Border.BOTTOM, Cell.Border.LEFT, Cell.Border.RIGHT);
        expected.add(Cell.Border.ALL);
        assertEquals(expected, cell.getBorders());
    }

    @Test
    void grayFill() {
        float input = .25f;
        cell.setGrayFill(input);
        assertEquals(input, cell.getGrayFill());

        assertThrows(IllegalArgumentException.class, () -> cell.setGrayFill(-0.1f));
        assertThrows(IllegalArgumentException.class, () -> cell.setGrayFill(1.1f));
    }

    @Test
    void borderWidth() {
        float input = .5f;
        cell.setBorderWidth(input);
        assertEquals(input, cell.getBorderWidth());
    }
}