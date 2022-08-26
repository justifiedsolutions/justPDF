/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.content.Cell;
import com.justifiedsolutions.justpdf.api.content.Paragraph;
import com.justifiedsolutions.justpdf.api.content.Phrase;
import com.justifiedsolutions.justpdf.api.content.Table;
import com.justifiedsolutions.justpdf.api.font.Font;
import com.justifiedsolutions.justpdf.api.font.PDFFont;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CellWidthHelperTest {

    @Test
    void getMinimumWidthHyphenate() {
        Font font = new PDFFont();
        String text = "Respondant is me";
        Paragraph paragraph = new Paragraph(text, font);
        paragraph.setHyphenate(true);
        Table table = new Table(1);
        Cell cell = table.createCell(paragraph);
        cell.setPadding(0f);

        PDFFontWrapper fontWrapper = PDFFontWrapper.getInstance(font);
        String substring = "spon-";
        float expected = fontWrapper.getStringWidth(substring);

        float actual = CellWidthHelper.getMinimumWidth(cell);
        assertEquals(expected, actual, .00001);
    }

    @Test
    void getMinimumWidthNoHyphenate() {
        Font font = new PDFFont();
        String text = "Respondant";
        Paragraph paragraph = new Paragraph(text, font);
        paragraph.setHyphenate(false);
        Table table = new Table(1);
        Cell cell = table.createCell(paragraph);
        cell.setPadding(0f);

        PDFFontWrapper fontWrapper = PDFFontWrapper.getInstance(font);
        String substring = "Respondant";
        float expected = fontWrapper.getStringWidth(substring);

        float actual = CellWidthHelper.getMinimumWidth(cell);
        assertEquals(expected, actual, .00001);
    }

    @Test
    void getMinimumWidthMultipleChunks() {
        Font font = new PDFFont();
        String text = "Respondant";
        Paragraph paragraph = new Paragraph(text, font);
        paragraph.add(new Phrase("This is extra cell text."));
        paragraph.setHyphenate(false);
        Table table = new Table(1);
        Cell cell = table.createCell(paragraph);
        cell.setPadding(0f);

        PDFFontWrapper fontWrapper = PDFFontWrapper.getInstance(font);
        String substring = "Respondant";
        float expected = fontWrapper.getStringWidth(substring);

        float actual = CellWidthHelper.getMinimumWidth(cell);
        assertEquals(expected, actual, .00001);
    }

    @Test
    void calculateColumnWidthsMinLessPreferred() {
        Column col1 = new Column(1, 10);
        Column col2 = new Column(2, 10);

        col1.setMinWidth(5);
        col2.setMinWidth(8);

        CellWidthHelper.calculateColumnWidths(List.of(col1, col2));

        assertEquals(col1.getPreferredWidth(), col1.getActualWidth());
        assertEquals(col2.getPreferredWidth(), col2.getActualWidth());
    }

    @Test
    void calculateColumnWidthsNotEnoughRoom() {
        Column col1 = new Column(1, 10);
        Column col2 = new Column(2, 10);

        col1.setMinWidth(11);
        col2.setMinWidth(11);

        List<Column> columns = List.of(col1, col2);

        assertThrows(IllegalStateException.class, () -> CellWidthHelper.calculateColumnWidths(columns));
    }

    @Test
    void calculateColumnWidthsOneHasRoom() {
        Column col1 = new Column(1, 10);
        Column col2 = new Column(2, 10);

        col1.setMinWidth(5);
        col2.setMinWidth(11);

        CellWidthHelper.calculateColumnWidths(List.of(col1, col2));

        assertEquals(9, col1.getActualWidth());
        assertEquals(11, col2.getActualWidth());
    }

    @Test
    void calculateColumnWidthsTwoHaveRoom() {
        Column col1 = new Column(1, 10);
        Column col2 = new Column(2, 10);
        Column col3 = new Column(3, 10);

        col1.setMinWidth(9);
        col2.setMinWidth(11);
        col3.setMinWidth(9);

        CellWidthHelper.calculateColumnWidths(List.of(col1, col2, col3));

        assertEquals(9.5, col1.getActualWidth());
        assertEquals(11, col2.getActualWidth());
        assertEquals(9.5, col3.getActualWidth());
    }

    @Test
    void calculateColumnWidthsTwoHaveRoom2() {
        Column col1 = new Column(1, 9.25f);
        Column col2 = new Column(2, 10);
        Column col3 = new Column(3, 10);

        col1.setMinWidth(9);
        col2.setMinWidth(11);
        col3.setMinWidth(9);

        CellWidthHelper.calculateColumnWidths(List.of(col1, col2, col3));

        assertEquals(9, col1.getActualWidth());
        assertEquals(11, col2.getActualWidth());
        assertEquals(9.25, col3.getActualWidth());
    }

}