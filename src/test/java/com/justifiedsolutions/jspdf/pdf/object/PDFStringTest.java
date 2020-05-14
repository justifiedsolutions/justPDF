/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.object;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class PDFStringTest {

    @Test
    public void writeToPDFString() throws IOException {
        String value = "string";
        byte[] expected = {'(', 0, (byte) 's', 0, (byte) 't', 0, (byte) 'r', 0, (byte) 'i', 0, (byte) 'n', 0, (byte) 'g', ')'};
        testPDFString(value, expected);
    }

    @Test
    public void writeToPDFEmpty() throws IOException {
        String value = "";
        byte[] expected = {'(', ')'};
        testPDFString(value, expected);
    }

    @Test
    public void writeToPDFNewLine() throws IOException {
        String value = "f\nb";
        byte[] expected = {'(', 0, (byte) 'f', (byte) '\\', (byte) 'n', 0, (byte) 'b', ')'};
        testPDFString(value, expected);
    }

    @Test
    public void writeToPDFUnbalancedParen() throws IOException {
        String value = "f(b";
        byte[] expected = {'(', 0, (byte) 'f', (byte) '\\', (byte) '(', 0, (byte) 'b', ')'};
        testPDFString(value, expected);
    }

    private void testPDFString(String input, byte[] expected) throws IOException {
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PDFString string = new PDFString(input);
        string.writeToPDF(actual);

        assertArrayEquals(expected, actual.toByteArray());
    }
}