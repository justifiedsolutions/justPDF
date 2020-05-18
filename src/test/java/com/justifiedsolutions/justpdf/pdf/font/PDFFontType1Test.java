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
        PDFObject firstChar = font.get(PDFFontType1.FIRST_CHAR);
        PDFObject lastChar = font.get(PDFFontType1.LAST_CHAR);
        PDFObject widths = font.get(PDFFontType1.WIDTHS);
        PDFObject descriptor = font.get(PDFFontType1.FONT_DESCRIPTOR);

        assertEquals(PDFFont.FONT, type);
        assertEquals(PDFFontType1.TYPE1, subtype);
        assertEquals(new PDFName("Courier"), baseFont);
        assertEquals(PDFFontType1.WIN_ANSI_ENCODING, encoding);
        assertEquals(new PDFInteger(32), firstChar);
        assertEquals(new PDFInteger(251), lastChar);

        assertTrue(widths instanceof PDFArray);
        PDFArray widthsArray = (PDFArray) widths;
        PDFInteger expectedWidth = new PDFInteger(600);
        assertEquals(149, widthsArray.size());
        for (int i = 0; i < 149; i++) {
            PDFObject arrayEntry = widthsArray.get(i);
            assertEquals(expectedWidth, arrayEntry, String.valueOf(i));
        }

        assertTrue(descriptor instanceof PDFFontDescriptor);
        PDFFontDescriptor fontDescriptor = (PDFFontDescriptor) descriptor;
        assertEquals(PDFFontDescriptor.FONT_DESCRIPTOR, fontDescriptor.get(PDFFontDescriptor.TYPE));
        assertEquals(new PDFName("Courier"), fontDescriptor.get(PDFFontDescriptor.FONT_NAME));
        assertEquals(new PDFRectangle(-23, -250, 715, 805), fontDescriptor.get(PDFFontDescriptor.FONT_BBOX));
        assertEquals(new PDFReal(562), fontDescriptor.get(PDFFontDescriptor.CAP_HEIGHT));
        assertEquals(new PDFReal(629), fontDescriptor.get(PDFFontDescriptor.ASCENT));
        assertEquals(new PDFReal(-157), fontDescriptor.get(PDFFontDescriptor.DESCENT));
        assertEquals(new PDFReal(51), fontDescriptor.get(PDFFontDescriptor.STEM_H));
        assertEquals(new PDFReal(51), fontDescriptor.get(PDFFontDescriptor.STEM_V));
        assertEquals(new PDFReal(0), fontDescriptor.get(PDFFontDescriptor.ITALIC_ANGLE));
        assertEquals(new PDFInteger(33), fontDescriptor.get(PDFFontDescriptor.FLAGS));
    }

    @Test
    public void testHelveticaBold() {
        PDFFont font = PDFFontType1.getInstance(PDFFontType1.FontName.HELVETICA_BOLD);
        PDFObject type = font.get(PDFFont.TYPE);
        PDFObject subtype = font.get(PDFFont.SUBTYPE);
        PDFObject baseFont = font.get(PDFFont.BASE_FONT);
        PDFObject encoding = font.get(PDFFontType1.ENCODING);
        PDFObject firstChar = font.get(PDFFontType1.FIRST_CHAR);
        PDFObject lastChar = font.get(PDFFontType1.LAST_CHAR);
        PDFObject widths = font.get(PDFFontType1.WIDTHS);
        PDFObject descriptor = font.get(PDFFontType1.FONT_DESCRIPTOR);

        assertEquals(PDFFont.FONT, type);
        assertEquals(PDFFontType1.TYPE1, subtype);
        assertEquals(new PDFName("Helvetica-Bold"), baseFont);
        assertEquals(PDFFontType1.WIN_ANSI_ENCODING, encoding);
        assertEquals(new PDFInteger(32), firstChar);
        assertEquals(new PDFInteger(251), lastChar);

        assertTrue(widths instanceof PDFArray);
        PDFArray widthsArray = (PDFArray) widths;
        assertEquals(new PDFInteger(278), widthsArray.get(0));
        assertEquals(new PDFInteger(333), widthsArray.get(1));
        assertEquals(new PDFInteger(611), widthsArray.get(148));

        assertTrue(descriptor instanceof PDFFontDescriptor);
        PDFFontDescriptor fontDescriptor = (PDFFontDescriptor) descriptor;
        assertEquals(PDFFontDescriptor.FONT_DESCRIPTOR, fontDescriptor.get(PDFFontDescriptor.TYPE));
        assertEquals(new PDFName("Helvetica-Bold"), fontDescriptor.get(PDFFontDescriptor.FONT_NAME));
        assertEquals(new PDFRectangle(-170, -228, 1003, 962), fontDescriptor.get(PDFFontDescriptor.FONT_BBOX));
        assertEquals(new PDFReal(718), fontDescriptor.get(PDFFontDescriptor.CAP_HEIGHT));
        assertEquals(new PDFReal(718), fontDescriptor.get(PDFFontDescriptor.ASCENT));
        assertEquals(new PDFReal(-207), fontDescriptor.get(PDFFontDescriptor.DESCENT));
        assertEquals(new PDFReal(118), fontDescriptor.get(PDFFontDescriptor.STEM_H));
        assertEquals(new PDFReal(140), fontDescriptor.get(PDFFontDescriptor.STEM_V));
        assertEquals(new PDFReal(0), fontDescriptor.get(PDFFontDescriptor.ITALIC_ANGLE));
        assertEquals(new PDFInteger(32), fontDescriptor.get(PDFFontDescriptor.FLAGS));
    }

    @Test
    public void testSymbol() {
        PDFFont font = PDFFontType1.getInstance(PDFFontType1.FontName.SYMBOL);
        PDFObject type = font.get(PDFFont.TYPE);
        PDFObject subtype = font.get(PDFFont.SUBTYPE);
        PDFObject baseFont = font.get(PDFFont.BASE_FONT);
        PDFObject encoding = font.get(PDFFontType1.ENCODING);
        PDFObject firstChar = font.get(PDFFontType1.FIRST_CHAR);
        PDFObject lastChar = font.get(PDFFontType1.LAST_CHAR);
        PDFObject widths = font.get(PDFFontType1.WIDTHS);
        PDFObject descriptor = font.get(PDFFontType1.FONT_DESCRIPTOR);

        assertEquals(PDFFont.FONT, type);
        assertEquals(PDFFontType1.TYPE1, subtype);
        assertEquals(new PDFName("Symbol"), baseFont);
        assertNull(encoding);
        assertEquals(new PDFInteger(32), firstChar);
        assertEquals(new PDFInteger(254), lastChar);

        assertTrue(widths instanceof PDFArray);
        PDFArray widthsArray = (PDFArray) widths;
        assertEquals(new PDFInteger(250), widthsArray.get(0));
        assertEquals(new PDFInteger(333), widthsArray.get(1));
        assertEquals(new PDFInteger(494), widthsArray.get(188));

        assertTrue(descriptor instanceof PDFFontDescriptor);
        PDFFontDescriptor fontDescriptor = (PDFFontDescriptor) descriptor;
        assertEquals(PDFFontDescriptor.FONT_DESCRIPTOR, fontDescriptor.get(PDFFontDescriptor.TYPE));
        assertEquals(new PDFName("Symbol"), fontDescriptor.get(PDFFontDescriptor.FONT_NAME));
        assertEquals(new PDFRectangle(-180, -293, 1090, 1010), fontDescriptor.get(PDFFontDescriptor.FONT_BBOX));
        assertNull(fontDescriptor.get(PDFFontDescriptor.CAP_HEIGHT));
        assertNull(fontDescriptor.get(PDFFontDescriptor.ASCENT));
        assertNull(fontDescriptor.get(PDFFontDescriptor.DESCENT));
        assertEquals(new PDFReal(92), fontDescriptor.get(PDFFontDescriptor.STEM_H));
        assertEquals(new PDFReal(85), fontDescriptor.get(PDFFontDescriptor.STEM_V));
        assertEquals(new PDFReal(0), fontDescriptor.get(PDFFontDescriptor.ITALIC_ANGLE));
        assertEquals(new PDFInteger(4), fontDescriptor.get(PDFFontDescriptor.FLAGS));
    }

}