/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.doc;

import com.justifiedsolutions.justpdf.pdf.object.PDFIndirectObject;
import com.justifiedsolutions.justpdf.pdf.object.PDFInteger;
import com.justifiedsolutions.justpdf.pdf.object.PDFNull;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PDFTrailerTest {

    @Test
    void hasInfo() {
        PDFTrailer trailer = new PDFTrailer();
        assertFalse(trailer.hasInfo());

        PDFIndirectObject indirectObject = new PDFIndirectObject(PDFNull.NULL);
        trailer.setInfo(indirectObject.getReference());
        assertTrue(trailer.hasInfo());
    }

    @Test
    void writeToPDF() throws IOException {
        PDFTrailer trailer = new PDFTrailer();
        PDFIndirectObject info = new PDFIndirectObject(PDFNull.NULL);
        PDFIndirectObject catalog = new PDFIndirectObject(PDFNull.NULL);
        PDFInteger size = new PDFInteger(3);
        PDFInteger totalBytes = new PDFInteger(42);

        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.writeBytes("trailer\n<</Info ".getBytes(StandardCharsets.US_ASCII));
        info.getReference().writeToPDF(expected);
        expected.writeBytes("/Root ".getBytes(StandardCharsets.US_ASCII));
        catalog.getReference().writeToPDF(expected);
        expected.writeBytes("/Size 3>>\nstartxref\n42\n%%EOF".getBytes(StandardCharsets.US_ASCII));

        trailer.setInfo(info.getReference());
        trailer.setRoot(catalog.getReference());
        trailer.setSize(size);
        trailer.setTotalBytes(totalBytes);
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        trailer.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }
}