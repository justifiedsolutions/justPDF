/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import com.justifiedsolutions.jspdf.pdf.object.PDFReal;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class StartPathTest {

    private final PDFReal x = new PDFReal(10);
    private final PDFReal y = new PDFReal(20);

    @Test
    public void writeToPDF() throws IOException {
        byte[] expected = "10 20 m\n".getBytes(StandardCharsets.US_ASCII);
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        new StartPath(x, y).writeToPDF(actual);
        assertArrayEquals(expected, actual.toByteArray());
    }
}