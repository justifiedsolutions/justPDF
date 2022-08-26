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

class AppendToPathTest {

    private final PDFReal x = new PDFReal(10);
    private final PDFReal y = new PDFReal(20);

    @Test
    void writeToPDF() throws IOException {
        byte[] expected = "10 20 l\n".getBytes(StandardCharsets.US_ASCII);
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        new AppendToPath(x, y).writeToPDF(actual);
        assertArrayEquals(expected, actual.toByteArray());
    }

    @Test
    @SuppressWarnings({ "unlikely-arg-type", "PMD.SimplifiableTestAssertion" })
    void equals() {
        PDFReal x = new PDFReal(1);
        PDFReal y = new PDFReal(2);
        PDFReal z = new PDFReal(3);
        AppendToPath operator = new AppendToPath(x, y);
        assertTrue(operator.equals(operator));
        assertFalse(operator.equals(null));
        assertFalse(operator.equals(Boolean.TRUE));
        AppendToPath op1 = new AppendToPath(x, y);
        AppendToPath op2 = new AppendToPath(x, z);
        AppendToPath op3 = new AppendToPath(z, y);
        assertTrue(operator.equals(op1));
        assertEquals(operator.hashCode(), op1.hashCode());
        assertFalse(operator.equals(op2));
        assertFalse(operator.equals(op3));
    }
}