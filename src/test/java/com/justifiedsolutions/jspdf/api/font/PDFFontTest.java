/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.api.font;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PDFFontTest {

    @Test
    public void constructorDefault() {
        PDFFont font = new PDFFont();
        assertEquals(PDFFont.FontName.HELVETICA, font.getName());
        assertEquals(12, font.getSize());
        assertEquals(Color.BLACK, font.getColor());
    }

    @Test
    public void constructorName() {
        PDFFont font = new PDFFont(PDFFont.FontName.COURIER);
        assertEquals(PDFFont.FontName.COURIER, font.getName());
        assertEquals(12, font.getSize());
        assertEquals(Color.BLACK, font.getColor());
    }

    @Test
    public void constructorNameSize() {
        PDFFont font = new PDFFont(PDFFont.FontName.COURIER, 24);
        assertEquals(PDFFont.FontName.COURIER, font.getName());
        assertEquals(24, font.getSize());
        assertEquals(Color.BLACK, font.getColor());
    }

    @Test
    public void constructorNameSizeColor() {
        PDFFont font = new PDFFont(PDFFont.FontName.COURIER, 24, Color.BLUE);
        assertEquals(PDFFont.FontName.COURIER, font.getName());
        assertEquals(24, font.getSize());
        assertEquals(Color.BLUE, font.getColor());
    }

    @Test
    public void equalsHashCode() {
        PDFFont f1 = new PDFFont();

        assertEquals(f1, f1);
        assertEquals(f1.hashCode(), f1.hashCode());

        assertNotEquals(f1, null);
        assertNotEquals(f1, Boolean.TRUE);

        PDFFont f2 = new PDFFont();
        assertEquals(f1, f2);
        assertEquals(f1.hashCode(), f2.hashCode());

        PDFFont f3 = new PDFFont(PDFFont.FontName.COURIER);
        assertNotEquals(f1, f3);

        PDFFont f4 = new PDFFont(PDFFont.DEFAULT_NAME, 13);
        assertNotEquals(f1, f4);

        PDFFont f5 = new PDFFont(PDFFont.DEFAULT_NAME, PDFFont.DEFAULT_SIZE, Color.BLUE);
        assertNotEquals(f1, f5);
    }
}