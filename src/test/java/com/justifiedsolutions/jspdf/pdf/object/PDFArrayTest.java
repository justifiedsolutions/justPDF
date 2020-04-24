/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.object;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class PDFArrayTest {

    @Test
    void sizeAndAdd() {
        PDFArray array = new PDFArray();
        assertEquals(0, array.size());
        array.add(PDFBoolean.TRUE);
        assertEquals(1, array.size());
    }

    @Test
    void isEmpty() {
        PDFArray array = new PDFArray();
        assertTrue(array.isEmpty());
        array.add(PDFBoolean.TRUE);
        assertFalse(array.isEmpty());
    }

    @Test
    void contains() {
        PDFArray array = new PDFArray();
        assertFalse(array.contains(PDFBoolean.TRUE));
        array.add(PDFBoolean.TRUE);
        assertTrue(array.contains(PDFBoolean.TRUE));
    }

    @Test
    void remove() {
        PDFArray array = new PDFArray();
        assertFalse(array.contains(PDFBoolean.TRUE));
        array.add(PDFBoolean.TRUE);
        assertTrue(array.contains(PDFBoolean.TRUE));
        array.remove(PDFBoolean.TRUE);
        assertFalse(array.contains(PDFBoolean.TRUE));
    }

    @Test
    void clear() {
        PDFArray array = new PDFArray();
        assertEquals(0, array.size());
        array.add(PDFBoolean.TRUE);
        assertEquals(1, array.size());
        array.clear();
        assertEquals(0, array.size());
    }

    @Test
    void get() {
        PDFArray array = new PDFArray();
        assertEquals(0, array.size());
        array.add(PDFBoolean.TRUE);
        assertEquals(PDFBoolean.TRUE, array.get(0));
    }

    @Test
    void writeToPDFEmpty() throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.write('[');
        expected.write(' ');
        expected.write(']');

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PDFArray array = new PDFArray();
        array.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }

    @Test
    void writeToPDFIntegerBoolean() throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.write('[');
        expected.write(' ');
        expected.writeBytes("42".getBytes(StandardCharsets.UTF_8));
        expected.write(' ');
        expected.writeBytes("true".getBytes(StandardCharsets.UTF_8));
        expected.write(' ');
        expected.write(']');

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PDFArray array = new PDFArray();
        array.add(new PDFInteger(42));
        array.add(PDFBoolean.TRUE);
        array.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }
}