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

public class PDFStringTest {

    @Test
    public void writeToPDFString() throws IOException {
        String value = "string";
        testPDFString(value, value);
    }

    @Test
    public void writeToPDFEmpty() throws IOException {
        String value = "";
        testPDFString(value, value);
    }

    @Test
    public void writeToPDFNewLine() throws IOException {
        testPDFString("foo\\nbar", "foo\nbar");
    }

    private void testPDFString(String value, String input) throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.write('(');
        if (value != null && !value.isEmpty()) {
            byte[] data = value.getBytes(StandardCharsets.UTF_16BE);
            expected.write('\ufeff');
            expected.writeBytes(data);
        }
        expected.write(')');

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PDFString string = new PDFString(input);
        string.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }
}