/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.object;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class PDFRectangleTest {

    @Test
    public void testCornersWidthHeight() {
        PDFReal llx = new PDFReal(0);
        PDFReal lly = new PDFReal(0);
        PDFReal urx = new PDFReal(10);
        PDFReal ury = new PDFReal(10);
        PDFReal width = new PDFReal(10);
        PDFReal height = new PDFReal(10);
        PDFRectangle rect = new PDFRectangle(llx, lly, urx, ury);
        assertEquals(llx, rect.getLLx());
        assertEquals(lly, rect.getLLy());
        assertEquals(urx, rect.getURx());
        assertEquals(ury, rect.getURy());
        assertEquals(width, rect.getWidth());
        assertEquals(height, rect.getHeight());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void equals() {
        PDFRectangle r1 = new PDFRectangle(0, 0, 10, 10);

        assertTrue(r1.equals(r1));
        assertFalse(r1.equals(null));
        assertFalse(r1.equals(PDFBoolean.TRUE));

        PDFRectangle r2 = new PDFRectangle(0, 0, 10, 10);
        assertTrue(r1.equals(r2));
        assertEquals(r1.hashCode(), r2.hashCode());

        assertFalse(r1.equals(new PDFRectangle(1, 0, 10, 10)));
        assertFalse(r1.equals(new PDFRectangle(0, 1, 10, 10)));
        assertFalse(r1.equals(new PDFRectangle(0, 0, 11, 10)));
        assertFalse(r1.equals(new PDFRectangle(0, 0, 10, 11)));
    }

    @Test
    public void writeToPDFFloat() throws IOException {
        PDFRectangle rect = new PDFRectangle(0, 0, 10, 10);
        testRectangle(rect);
    }

    @Test
    public void writeToPDFObject() throws IOException {
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