/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeviceGrayTest {

    @Test
    void testCreateHigh() {
        PDFReal g = new PDFReal(1.1f);
        assertThrows(IllegalArgumentException.class, () -> new DeviceGray(g));
    }

    @Test
    void testCreateLow() {
        PDFReal g = new PDFReal(-.1f);
        assertThrows(IllegalArgumentException.class, () -> new DeviceGray(g));
    }

    @Test
    @SuppressWarnings({ "unlikely-arg-type", "PMD.SimplifiableTestAssertion" })
    void equals() {
        PDFReal zero = new PDFReal(0);
        PDFReal one = new PDFReal(1);
        DeviceGray black = new DeviceGray(zero);
        DeviceGray b2 = new DeviceGray(zero);
        DeviceGray white = new DeviceGray(one);
        assertTrue(black.equals(black));
        assertFalse(black.equals(null));
        assertFalse(black.equals(Boolean.TRUE));
        assertTrue(black.equals(b2));
        assertEquals(black.hashCode(), b2.hashCode());
        assertFalse(black.equals(white));
    }
}