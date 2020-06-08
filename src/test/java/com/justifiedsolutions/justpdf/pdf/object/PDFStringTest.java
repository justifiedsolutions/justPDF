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
        byte[] expected = {'(', (byte) 'i', (byte) 't', (byte) '\'', (byte) 's', ')'};
        testPDFString(value, expected);
    }

    @Test
    public void writeToPDFString() throws IOException {
        String value = "string";
        byte[] expected = {'(', (byte) 's', (byte) 't', (byte) 'r', (byte) 'i', (byte) 'n', (byte) 'g', ')'};
        testPDFString(value, expected);
    }

    @Test
    public void writeToPDFEmpty() throws IOException {
        String value = "";
        byte[] expected = {'(', ')'};
        testPDFString(value, expected);
    }

    @Test
    public void escapedCharTest() throws IOException {
        char[] special = {'\n', '\r', '\t', '\b', '\f', '(', ')', '\\'};
        for (char c : special) {
            String input = String.format("f%cb", c);
            byte[] expected = {'(', (byte) 'f', (byte) '\\', (byte) c, (byte) 'b', ')'};
            testPDFString(input, expected);
        }
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

    private void testPDFString(String input, byte[] expected) throws IOException {
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PDFString string = new PDFString(input);
        string.writeToPDF(actual);

        assertArrayEquals(expected, actual.toByteArray());
    }
}