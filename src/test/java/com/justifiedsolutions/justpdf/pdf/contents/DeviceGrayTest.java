/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeviceGrayTest {

    @Test
    public void testCreateHigh() {
        PDFReal g = new PDFReal(1.1f);
        assertThrows(IllegalArgumentException.class, () -> new DeviceGray(g));
    }

    @Test
    public void testCreateLow() {
        PDFReal g = new PDFReal(-.1f);
        assertThrows(IllegalArgumentException.class, () -> new DeviceGray(g));
    }

    @Test
    public void testEquals() {
        DeviceGray g1 = new DeviceGray(new PDFReal(.33f));
        DeviceGray g2 = new DeviceGray(new PDFReal(.33f));

        assertEquals(g1, g2);
    }
}