/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api.content;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TableTest {

    @Test
    public void copyConstructor() {
        Table table = new Table(3);
        Table copy = new Table(table, table.getCells());
        assertEquals(table, copy);
    }

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
    public void setBorderWidth() {
        float input = .5f;
        Table table = new Table(4);
        table.setBorderWidth(input);
        assertEquals(input, table.getBorderWidth());
    }

    @Test
    public void createCell() {
        float bw = .5f;
        Table table = new Table(4);
        table.setBorderWidth(bw);
        Cell cell = table.createCell();
        assertNotNull(table.getCells());
        assertEquals(1, table.getCells().size());
        assertEquals(cell, table.getCells().get(0));
        assertEquals(bw, cell.getBorderWidth());
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

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testEquals() {
        Table t0 = new Table(2);

        assertTrue(t0.equals(t0));
        assertFalse(t0.equals(null));
        assertFalse(t0.equals(Boolean.TRUE));

        Table t1 = new Table(t0, t0.getCells());
        assertTrue(t0.equals(t1));
        assertEquals(t0.hashCode(), t1.hashCode());

        Table t3 = new Table(t0, t0.getCells());
        t3.setKeepTogether(true);
        assertFalse(t0.equals(t3));

        Table t4 = new Table(t0, t0.getCells());
        t4.setWidthPercentage(10);
        assertFalse(t0.equals(t4));

        Table t5 = new Table(t0, t0.getCells());
        t5.setSpacingBefore(10);
        assertFalse(t0.equals(t5));

        Table t6 = new Table(t0, t0.getCells());
        t6.setSpacingAfter(10);
        assertFalse(t0.equals(t6));

        Table t7 = new Table(t0, t0.getCells());
        t7.setBorderWidth(10);
        assertFalse(t0.equals(t7));

        Table t8 = new Table(2);
        assertFalse(t0.equals(t8));

        Table t9 = new Table(t0, t0.getCells());
        t9.createCell();
        assertFalse(t0.equals(t9));
    }
}