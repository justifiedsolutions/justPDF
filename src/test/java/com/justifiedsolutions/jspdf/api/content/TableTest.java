/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.api.content;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TableTest {

    @Test
    public void createWithBadWidthPositive() {
        float[] columns = {25f, 25f, 25f, 25f};
        assertThrows(IllegalArgumentException.class, () -> new Table(columns));
    }

    @Test
    public void createWithBadWidthNegative() {
        float[] columns = {-25f, -25f, -25f, -25f};
        assertThrows(IllegalArgumentException.class, () -> new Table(columns));
    }

    @Test
    public void getNumberOfColumns() {
        int input = 4;
        Table table = new Table(input);
        assertEquals(input, table.getNumberOfColumns());
    }

    @Test
    public void getRelativeColumnWidths() {
        float[] columns = {.25f, .25f, .25f, .25f};
        Table table = new Table(columns);
        assertArrayEquals(columns, table.getRelativeColumnWidths());
    }

    @Test
    public void setKeepTogether() {
        Table table = new Table(4);
        table.setKeepTogether(true);
        assertTrue(table.isKeepTogether());
        table.setKeepTogether(false);
        assertFalse(table.isKeepTogether());
    }

    @Test
    public void setWidthPercentage() {
        float input = .95f;
        Table table = new Table(4);
        table.setWidthPercentage(input);
        assertEquals(input, table.getWidthPercentage());
    }

    @Test
    public void setSpacingBefore() {
        float input = 9;
        Table table = new Table(4);
        table.setSpacingBefore(input);
        assertEquals(input, table.getSpacingBefore());
    }

    @Test
    public void setSpacingAfter() {
        float input = 10;
        Table table = new Table(4);
        table.setSpacingAfter(input);
        assertEquals(input, table.getSpacingAfter());
    }

    @Test
    public void createCell() {
        Table table = new Table(4);
        Cell cell = table.createCell();
        assertNotNull(table.getCells());
        assertEquals(1, table.getCells().size());
        assertEquals(cell, table.getCells().get(0));
    }

    @Test
    public void createCellContent() {
        Phrase content = new Phrase("");
        Table table = new Table(4);
        Cell cell = table.createCell(content);
        assertNotNull(table.getCells());
        assertEquals(1, table.getCells().size());
        assertEquals(cell, table.getCells().get(0));
        assertEquals(content, cell.getContent());
    }
}