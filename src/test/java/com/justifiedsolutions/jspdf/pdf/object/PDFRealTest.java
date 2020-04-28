/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.object;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class PDFRealTest {

    @Test
    public void writeToPDFPositive() throws IOException {
        float input = 3.14f;
        byte[] expected = {(byte) '3', (byte) '.', (byte) '1', (byte) '4'};
        testPDFReal(input, expected);
    }

    @Test
    public void writeToPDFNegative() throws IOException {
        float input = -3.14f;
        byte[] expected = {(byte) '-', (byte) '3', (byte) '.', (byte) '1', (byte) '4'};
        testPDFReal(input, expected);
    }

    @Test
    public void writeToPDFZero() throws IOException {
        float input = 0;
        byte[] expected = {(byte) '0'};
        testPDFReal(input, expected);
    }

    @Test
    public void writeToPDFOne() throws IOException {
        float input = 1;
        byte[] expected = {(byte) '1'};
        testPDFReal(input, expected);
    }

    @Test
    public void writeToPDFNegOne() throws IOException {
        float input = -1;
        byte[] expected = {(byte) '-', (byte) '1'};
        testPDFReal(input, expected);
    }

    @Test
    public void writeToPDFHalf() throws IOException {
        float input = .5f;
        byte[] expected = {(byte) '.', (byte) '5'};
        testPDFReal(input, expected);
    }

    @Test
    public void writeToPDFFrac5() throws IOException {
        float input = .55555557f;
        byte[] expected = {(byte) '.', (byte) '5', (byte) '5', (byte) '5', (byte) '5', (byte) '6'};
        testPDFReal(input, expected);
    }

    private void testPDFReal(float input, byte[] expected) throws IOException {
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PDFReal real = new PDFReal(input);
        real.writeToPDF(actual);

        assertArrayEquals(expected, actual.toByteArray());
    }
}