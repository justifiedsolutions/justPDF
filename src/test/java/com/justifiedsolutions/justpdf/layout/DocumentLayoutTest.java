/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.Document;
import com.justifiedsolutions.justpdf.api.DocumentException;
import com.justifiedsolutions.justpdf.api.Margin;
import com.justifiedsolutions.justpdf.api.PageSize;
import com.justifiedsolutions.justpdf.api.content.Cell;
import com.justifiedsolutions.justpdf.api.content.Paragraph;
import com.justifiedsolutions.justpdf.api.content.Table;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DocumentLayoutTest {

    @Test
    void testLayoutEmptyDocument() {
        Margin margin = new Margin(72, 72, 72, 72);
        PageSize pageSize = PageSize.LETTER;
        Document document = new Document(pageSize, margin);
        assertThrows(IllegalStateException.class, () -> new DocumentLayout(document));
    }

    @Test
    void testLargeTableCanBreak() throws DocumentException {
        Margin margin = new Margin(72, 72, 72, 72);
        PageSize pageSize = PageSize.LETTER;
        Document document = new Document(pageSize, margin);

        Table table = new Table(1);
        table.setKeepTogether(true);
        Cell c1 = table.createCell();
        c1.setMinimumHeight(600);
        Cell c2 = table.createCell();
        c2.setMinimumHeight(40);
        Cell c3 = table.createCell();
        c3.setMinimumHeight(40);

        document.add(table);

        assertDoesNotThrow(() -> new DocumentLayout(document));
    }

    @Test
    void testLargeTableCanBreakNotEmptyPage() throws DocumentException {
        Margin margin = new Margin(72, 72, 72, 72);
        PageSize pageSize = PageSize.LETTER;
        Document document = new Document(pageSize, margin);

        Paragraph paragraph = new Paragraph("Some content");
        document.add(paragraph);

        Table table = new Table(1);
        table.setKeepTogether(true);
        Cell c1 = table.createCell();
        c1.setMinimumHeight(600);
        Cell c2 = table.createCell();
        c2.setMinimumHeight(40);
        Cell c3 = table.createCell();
        c3.setMinimumHeight(40);

        document.add(table);

        assertDoesNotThrow(() -> new DocumentLayout(document));
    }

    @Test
    void testLargeTableCanNotBreak() throws DocumentException {
        Margin margin = new Margin(72, 72, 72, 72);
        PageSize pageSize = PageSize.LETTER;
        Document document = new Document(pageSize, margin);

        Table table = new Table(1);
        table.setKeepTogether(true);
        Cell c1 = table.createCell();
        c1.setMinimumHeight(700);

        document.add(table);

        assertThrows(DocumentException.class, () -> new DocumentLayout(document));
    }
}