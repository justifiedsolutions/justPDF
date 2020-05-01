/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.object;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class PDFRectangleTest {

    @Test
    void writeToPDFFloat() throws IOException {
        PDFRectangle rect = new PDFRectangle(0, 0, 10, 10);
        testRectangle(rect);
    }

    @Test
    void writeToPDFObject() throws IOException {
        PDFReal llx = new PDFReal(0);
        PDFReal lly = new PDFReal(0);
        PDFReal urx = new PDFReal(10);
        PDFReal ury = new PDFReal(10);
        PDFRectangle rect = new PDFRectangle(llx, lly, urx, ury);
        testRectangle(rect);
    }

    private void testRectangle(PDFRectangle rect) throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.writeBytes("[ 0 0 10 10 ]".getBytes(StandardCharsets.US_ASCII));

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        rect.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }
}