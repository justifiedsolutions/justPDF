/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api;

import com.justifiedsolutions.justpdf.api.content.Chunk;
import com.justifiedsolutions.justpdf.api.content.Paragraph;
import com.justifiedsolutions.justpdf.api.font.Font;
import com.justifiedsolutions.justpdf.api.font.PDFFont;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PageNumberFooterTest {

    @Test
    public void isValidForPageNumberTrue() {
        PageNumberFooter footer = new PageNumberFooter(true, HorizontalAlignment.CENTER, new PDFFont());
        assertTrue(footer.isValidForPageNumber(1));
        assertTrue(footer.isValidForPageNumber(2));
    }

    @Test
    public void isValidForPageNumberFalse() {
        PageNumberFooter footer = new PageNumberFooter(false, HorizontalAlignment.CENTER, new PDFFont());
        assertFalse(footer.isValidForPageNumber(1));
        assertTrue(footer.isValidForPageNumber(2));
    }

    @Test
    public void getParagraph() {
        Font expectedFont = new PDFFont();
        HorizontalAlignment expectedAlignment = HorizontalAlignment.CENTER;
        PageNumberFooter footer = new PageNumberFooter(true, expectedAlignment, expectedFont);
        Paragraph actual = footer.getParagraph(1);
        assertEquals(expectedAlignment, actual.getAlignment());
        assertEquals(expectedFont, actual.getFont());
        assertEquals(1, actual.getContent().size());
        Chunk chunk = (Chunk) actual.getContent().get(0);
        assertEquals("Page 1", chunk.getText());
    }
}