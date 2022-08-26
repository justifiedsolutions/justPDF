/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StartPathTest {

    private final PDFReal x = new PDFReal(10);
    private final PDFReal y = new PDFReal(20);
    private final PDFReal z = new PDFReal(30);

    @Test
    void writeToPDF() throws IOException {
        byte[] expected = "10 20 m\n".getBytes(StandardCharsets.US_ASCII);
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        new StartPath(x, y).writeToPDF(actual);
        assertArrayEquals(expected, actual.toByteArray());
    }

    @Test
    void collapsable() {
        StartPath op1 = new StartPath(x, y);
        StartPath op2 = new StartPath(x, z);
        BeginText bt = new BeginText();

        assertTrue(op1.isCollapsable(op2));
        assertFalse(op1.isCollapsable(bt));

        assertEquals(op2, op1.collapse(op2));
    }

    @Test
    @SuppressWarnings({ "unlikely-arg-type", "PMD.SimplifiableTestAssertion" })
    void equals() {
        StartPath operator = new StartPath(x, y);
        assertTrue(operator.equals(operator));
        assertFalse(operator.equals(null));
        assertFalse(operator.equals(Boolean.TRUE));
        StartPath op1 = new StartPath(x, y);
        StartPath op2 = new StartPath(x, z);
        StartPath op3 = new StartPath(z, y);
        assertTrue(operator.equals(op1));
        assertEquals(operator.hashCode(), op1.hashCode());
        assertFalse(operator.equals(op2));
        assertFalse(operator.equals(op3));
    }
}