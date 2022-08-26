/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.object;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PDFIntegerTest {

    @Test
    void compareTo() {
        PDFInteger value = new PDFInteger(0);
        assertEquals(-1, value.compareTo(null));
        assertEquals(0, value.compareTo(new PDFInteger(0)));
    }

    @Test
    void writeToPDFPositive() throws IOException {
        testPDFInteger(100);
    }

    @Test
    void writeToPDFNegative() throws IOException {
        testPDFInteger(-100);
    }

    @Test
    void writeToPDFZero() throws IOException {
        testPDFInteger(0);
    }

    private void testPDFInteger(int value) throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.writeBytes(String.valueOf(value).getBytes(StandardCharsets.US_ASCII));

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PDFInteger integer = new PDFInteger(value);
        integer.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }
}