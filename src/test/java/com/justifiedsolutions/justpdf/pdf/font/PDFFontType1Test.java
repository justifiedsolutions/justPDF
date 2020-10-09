/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.font;

import com.justifiedsolutions.justpdf.pdf.object.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PDFFontType1Test {

    @Test
    public void testCourier() {
        PDFFont font = PDFFontType1.getInstance(PDFFontType1.FontName.COURIER);
        PDFObject type = font.get(PDFFont.TYPE);
        PDFObject subtype = font.get(PDFFont.SUBTYPE);
        PDFObject baseFont = font.get(PDFFont.BASE_FONT);
        PDFObject encoding = font.get(PDFFontType1.ENCODING);

        assertEquals(PDFFont.FONT, type);
        assertEquals(PDFFontType1.TYPE1, subtype);
        assertEquals(new PDFName("Courier"), baseFont);
        assertEquals(PDFFontType1.WIN_ANSI_ENCODING, encoding);
    }

    @Test
    public void testHelveticaBold() {
        PDFFont font = PDFFontType1.getInstance(PDFFontType1.FontName.HELVETICA_BOLD);
        PDFObject type = font.get(PDFFont.TYPE);
        PDFObject subtype = font.get(PDFFont.SUBTYPE);
        PDFObject baseFont = font.get(PDFFont.BASE_FONT);
        PDFObject encoding = font.get(PDFFontType1.ENCODING);

        assertEquals(PDFFont.FONT, type);
        assertEquals(PDFFontType1.TYPE1, subtype);
        assertEquals(new PDFName("Helvetica-Bold"), baseFont);
        assertEquals(PDFFontType1.WIN_ANSI_ENCODING, encoding);
    }

    @Test
    public void testSymbol() {
        PDFFont font = PDFFontType1.getInstance(PDFFontType1.FontName.SYMBOL);
        PDFObject type = font.get(PDFFont.TYPE);
        PDFObject subtype = font.get(PDFFont.SUBTYPE);
        PDFObject baseFont = font.get(PDFFont.BASE_FONT);
        PDFObject encoding = font.get(PDFFontType1.ENCODING);

        assertEquals(PDFFont.FONT, type);
        assertEquals(PDFFontType1.TYPE1, subtype);
        assertEquals(new PDFName("Symbol"), baseFont);
        assertNull(encoding);
    }

}