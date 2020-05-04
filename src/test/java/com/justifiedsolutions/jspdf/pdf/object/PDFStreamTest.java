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

public class PDFStreamTest {

    @Test
    public void writeToPDF() throws IOException {
        byte[] data = "I am a leaf on the wind. Watch how I soar.".getBytes();

        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.writeBytes("<</Length ".getBytes(StandardCharsets.US_ASCII));
        expected.writeBytes(String.valueOf(data.length).getBytes(StandardCharsets.US_ASCII));
        expected.writeBytes(">>\nstream\n".getBytes(StandardCharsets.US_ASCII));
        expected.writeBytes(data);
        expected.writeBytes("\nendstream\n".getBytes(StandardCharsets.US_ASCII));

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PDFStream stream = new PDFStream(data);
        stream.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }
}