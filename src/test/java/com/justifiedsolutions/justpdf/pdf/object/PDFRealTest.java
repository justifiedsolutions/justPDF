/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.object;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PDFRealTest {

    @Test
    void truncate() {
        float input = .123456f;
        float expected = .12345f;
        assertEquals(expected, PDFReal.truncate(input));
    }

    @Test
    void writeToPDFPositive() throws IOException {
        float input = 3.14f;
        byte[] expected = { (byte) '3', (byte) '.', (byte) '1', (byte) '4' };
        testPDFReal(input, expected);
    }

    @Test
    void writeToPDFNegative() throws IOException {
        float input = -3.14f;
        byte[] expected = { (byte) '-', (byte) '3', (byte) '.', (byte) '1', (byte) '4' };
        testPDFReal(input, expected);
    }

    @Test
    void writeToPDFZero() throws IOException {
        float input = 0;
        byte[] expected = { (byte) '0' };
        testPDFReal(input, expected);
    }

    @Test
    void writeToPDFOne() throws IOException {
        float input = 1;
        byte[] expected = { (byte) '1' };
        testPDFReal(input, expected);
    }

    @Test
    void writeToPDFNegOne() throws IOException {
        float input = -1;
        byte[] expected = { (byte) '-', (byte) '1' };
        testPDFReal(input, expected);
    }

    @Test
    void writeToPDFHalf() throws IOException {
        float input = .5f;
        byte[] expected = { (byte) '.', (byte) '5' };
        testPDFReal(input, expected);
    }

    @Test
    void writeToPDFFrac5() throws IOException {
        float input = .55555557f;
        byte[] expected = { (byte) '.', (byte) '5', (byte) '5', (byte) '5', (byte) '5', (byte) '6' };
        testPDFReal(input, expected);
    }

    private void testPDFReal(float input, byte[] expected) throws IOException {
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PDFReal real = new PDFReal(input);
        real.writeToPDF(actual);

        assertArrayEquals(expected, actual.toByteArray());
    }
}