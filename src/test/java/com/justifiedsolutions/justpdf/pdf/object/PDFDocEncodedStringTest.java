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

public class PDFDocEncodedStringTest {

    @Test
    public void writeToPDFString() throws IOException {
        String input = "string\u2122";
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        os.writeBytes("(string".getBytes(StandardCharsets.US_ASCII));
        os.write(146);
        os.write(')');
        testPDFString(input, os.toByteArray());
    }

    @Test
    public void writeToPDFEmpty() throws IOException {
        String input = "";
        String expected = "()";
        testPDFString(input, expected);
    }

    @Test
    public void writeToPDFNewLine() throws IOException {
        testPDFString("foo\nbar", "(foo\\nbar)");
    }

    private void testPDFString(String input, String expected) throws IOException {
        testPDFString(input, expected.getBytes(StandardCharsets.US_ASCII));
    }

    private void testPDFString(String input, byte[] expected) throws IOException {
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PDFDocEncodedString string = new PDFDocEncodedString(input);
        string.writeToPDF(actual);

        assertArrayEquals(expected, actual.toByteArray());
    }
}