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

public class PDFRealTest {

    @Test
    public void writeToPDFPositive() throws IOException {
        testPDFReal(3.14f);
    }

    @Test
    public void writeToPDFNegative() throws IOException {
        testPDFReal(-3.14f);
    }

    @Test
    public void writeToPDFZero() throws IOException {
        testPDFReal(0);
    }

    @Test
    public void writeToPDFOne() throws IOException {
        testPDFReal(1);
    }

    @Test
    public void writeToPDFNegOne() throws IOException {
        testPDFReal(-1);
    }

    @Test
    public void writeToPDFHalf() throws IOException {
        testPDFReal(.5f);
    }

    private void testPDFReal(float value) throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.writeBytes(String.valueOf(value).getBytes(StandardCharsets.US_ASCII));

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PDFReal real = new PDFReal(value);
        real.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }
}