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

public class PDFIndirectObjectTest {

    @Test
    public void getGenerationNumber() {
        PDFIndirectObject inObject = new PDFIndirectObject(PDFBoolean.TRUE);
        assertEquals(new PDFInteger(0), inObject.getGenerationNumber());
    }

    @Test
    public void getObject() {
        PDFObject expected = PDFBoolean.TRUE;
        PDFIndirectObject inObject = new PDFIndirectObject(expected);
        assertEquals(expected, inObject.getObject());
    }

    @Test
    public void getReference() throws IOException {
        PDFIndirectObject inObject = new PDFIndirectObject(PDFBoolean.TRUE);

        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.writeBytes(String.valueOf(inObject.getObjectNumber().getValue()).getBytes(StandardCharsets.US_ASCII));
        expected.writeBytes(" 0 R".getBytes(StandardCharsets.US_ASCII));

        PDFIndirectObject.Reference reference = inObject.getReference();
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        reference.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }

    @Test
    public void writeToPDF() throws IOException {
        PDFIndirectObject inObject = new PDFIndirectObject(PDFBoolean.TRUE);

        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.writeBytes(String.valueOf(inObject.getObjectNumber().getValue()).getBytes(StandardCharsets.US_ASCII));
        expected.writeBytes(" 0 obj\ntrue\nendobj\n\n".getBytes(StandardCharsets.US_ASCII));

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        inObject.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }
}