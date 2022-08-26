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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PDFStreamTest {

    @Test
    void writeToPDF() throws IOException {
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

    @Test
    void addFilters() {
        PDFStream stream = new PDFStream(new byte[] {});
        stream.addFilter(null);
        assertNull(stream.getFilter());
        stream.addFilter(new PDFArray());
        assertNull(stream.getFilter());
        PDFArray filter = new PDFArray();
        filter.add(new PDFName("foo"));
        stream.addFilter(filter);
        assertEquals(filter, stream.getFilter());
    }

    @Test
    void addDecodeParams() {
        PDFStream stream = new PDFStream(new byte[] {});
        stream.addDecodeParams(null);
        assertNull(stream.getDecodeParams());
        stream.addDecodeParams(new PDFArray());
        assertNull(stream.getDecodeParams());
        PDFArray params = new PDFArray();
        params.add(new PDFDictionary());
        stream.addDecodeParams(params);
        assertEquals(params, stream.getDecodeParams());
        params.add(new PDFDictionary());
        stream.addDecodeParams(params);
        assertEquals(params, stream.getDecodeParams());
    }

    @Test
    @SuppressWarnings({ "unlikely-arg-type", "PMD.SimplifiableTestAssertion" })
    void equals() {
        byte[] b1 = { 0, 1, 2, 3, 4 };
        byte[] b2 = { 1, 2, 3, 4, 5 };
        PDFStream s1 = new PDFStream(b1);
        assertTrue(s1.equals(s1));
        assertFalse(s1.equals(null));
        assertFalse(s1.equals(PDFBoolean.TRUE));

        PDFStream s2 = new PDFStream(b1);
        assertTrue(s1.equals(s2));
        assertEquals(s1.hashCode(), s2.hashCode());

        PDFStream s3 = new PDFStream(b2);
        assertFalse(s1.equals(s3));
    }
}