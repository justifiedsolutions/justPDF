/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeviceRGBTest {
    private final PDFReal r = new PDFReal(0);
    private final PDFReal g = new PDFReal(.2f);
    private final PDFReal b = new PDFReal(.6f);
    private final PDFReal x = new PDFReal(0.1f);

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void equals() {
        DeviceRGB rgb = new DeviceRGB(r, g, b);
        assertFalse(rgb.equals(null));
        assertFalse(rgb.equals(Boolean.TRUE));
    }

    @Test
    public void testEquals() {
        DeviceRGB rgb1 = new DeviceRGB(r, g, b);
        DeviceRGB rgb2 = new DeviceRGB(r, g, b);

        assertEquals(rgb1, rgb2);
    }

    @Test
    public void testEqualsSame() {
        DeviceRGB rgb1 = new DeviceRGB(r, g, b);

        assertEquals(rgb1, rgb1);
    }

    @Test
    public void testNotEqualsR() {
        DeviceRGB rgb1 = new DeviceRGB(r, g, b);
        DeviceRGB rgb2 = new DeviceRGB(x, g, b);

        assertNotEquals(rgb1, rgb2);
    }

    @Test
    public void testNotEqualsG() {
        DeviceRGB rgb1 = new DeviceRGB(r, g, b);
        DeviceRGB rgb2 = new DeviceRGB(r, x, b);

        assertNotEquals(rgb1, rgb2);
    }

    @Test
    public void testNotEqualsB() {
        DeviceRGB rgb1 = new DeviceRGB(r, g, b);
        DeviceRGB rgb2 = new DeviceRGB(r, g, x);

        assertNotEquals(rgb1, rgb2);
    }
}