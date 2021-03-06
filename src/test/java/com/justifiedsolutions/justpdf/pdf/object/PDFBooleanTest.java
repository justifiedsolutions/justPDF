/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.object;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class PDFBooleanTest {

    @Test
    public void writeToPDFTrue() throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.writeBytes("true".getBytes(StandardCharsets.US_ASCII));

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PDFBoolean.TRUE.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }

    @Test
    public void writeToPDFFalse() throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.writeBytes("false".getBytes(StandardCharsets.US_ASCII));

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PDFBoolean.FALSE.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }

    @Test
    public void equals() {
        assertTrue(PDFBoolean.TRUE.equals(PDFBoolean.TRUE));
        assertEquals(PDFBoolean.TRUE.hashCode(), PDFBoolean.TRUE.hashCode());
        assertFalse(PDFBoolean.TRUE.equals(null));
        assertFalse(PDFBoolean.TRUE.equals("true"));
        assertFalse(PDFBoolean.TRUE.equals(PDFBoolean.FALSE));

    }
}