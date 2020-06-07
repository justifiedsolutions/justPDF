/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.object;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PDFStringTest {

    @Test
    public void writeToPDFApostrophe() throws IOException {
        String value = "it's";
        byte[] expected = {-2, -1, 0, '(', 0, (byte) 'i', 0, (byte) 't', 0, (byte) '\'', 0, (byte) 's', 0, ')'};
        testPDFString(value, expected);
    }

    @Test
    public void writeToPDFString() throws IOException {
        String value = "string";
        byte[] expected = {-2, -1, 0, '(', 0, (byte) 's', 0, (byte) 't', 0, (byte) 'r', 0, (byte) 'i', 0, (byte) 'n', 0, (byte) 'g', 0, ')'};
        testPDFString(value, expected);
    }

    @Test
    public void writeToPDFEmpty() throws IOException {
        String value = "";
        byte[] expected = {-2, -1, 0, '(', 0, ')'};
        testPDFString(value, expected);
    }

    @Test
    public void escapedCharTest() throws IOException {
        char[] special = {'\n', '\r', '\t', '\b', '\f', '(', ')', '\\'};
        for (char c : special) {
            String input = String.format("f%cb", c);
            byte[] expected = {-2, -1, 0, '(', 0, (byte) 'f', 0, (byte) '\\', 0, (byte) c, 0, (byte) 'b', 0, ')'};
            testPDFString(input, expected);
        }
    }

    private void testPDFString(String input, byte[] expected) throws IOException {
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PDFString string = new PDFString(input);
        string.writeToPDF(actual);

        assertArrayEquals(expected, actual.toByteArray());
    }

    @Test
    public void testEquals() {
        PDFString s0 = new PDFString("foo");
        assertTrue(s0.equals(s0));
        assertFalse(s0.equals(null));
        assertFalse(s0.equals(PDFBoolean.TRUE));

        PDFString s1 = new PDFString("foo");
        assertTrue(s0.equals(s1));
        assertEquals(s0.hashCode(), s1.hashCode());

        PDFString s2 = new PDFString("bar");
        assertFalse(s0.equals(s2));
    }
}