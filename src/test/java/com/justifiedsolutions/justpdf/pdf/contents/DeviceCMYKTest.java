/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DeviceCMYKTest {
    private final PDFReal c = new PDFReal(0);
    private final PDFReal m = new PDFReal(.2f);
    private final PDFReal y = new PDFReal(.6f);
    private final PDFReal k = new PDFReal(1);
    private final PDFReal x = new PDFReal(0.1f);

    @Test
    public void testEquals() {
        DeviceCMYK color1 = new DeviceCMYK(c, m, y, k);
        DeviceCMYK color2 = new DeviceCMYK(c, m, y, k);

        assertEquals(color1, color2);
    }

    @Test
    public void testEqualsSame() {
        DeviceCMYK color1 = new DeviceCMYK(c, m, y, k);

        assertEquals(color1, color1);
    }

    @Test
    public void testNotEqualsC() {
        DeviceCMYK color1 = new DeviceCMYK(c, m, y, k);
        DeviceCMYK color2 = new DeviceCMYK(x, m, y, k);

        assertNotEquals(color1, color2);
    }

    @Test
    public void testNotEqualsM() {
        DeviceCMYK color1 = new DeviceCMYK(c, m, y, k);
        DeviceCMYK color2 = new DeviceCMYK(c, x, y, k);

        assertNotEquals(color1, color2);
    }

    @Test
    public void testNotEqualsY() {
        DeviceCMYK color1 = new DeviceCMYK(c, m, y, k);
        DeviceCMYK color2 = new DeviceCMYK(c, m, x, k);

        assertNotEquals(color1, color2);
    }

    @Test
    public void testNotEqualsK() {
        DeviceCMYK color1 = new DeviceCMYK(c, m, y, k);
        DeviceCMYK color2 = new DeviceCMYK(c, m, y, x);

        assertNotEquals(color1, color2);
    }
}