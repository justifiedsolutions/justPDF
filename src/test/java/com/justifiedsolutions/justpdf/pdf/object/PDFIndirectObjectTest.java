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

    @Test
    public void compareTo() {
        PDFIndirectObject.resetObjectNumber();
        PDFIndirectObject io1 = new PDFIndirectObject(PDFBoolean.TRUE);
        PDFIndirectObject.resetObjectNumber();
        PDFIndirectObject io2 = new PDFIndirectObject(PDFBoolean.TRUE);
        assertEquals(-1, io1.compareTo(null));
        assertEquals(0, io1.compareTo(io2));
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void equals() {
        PDFIndirectObject.resetObjectNumber();
        PDFIndirectObject io1 = new PDFIndirectObject(PDFBoolean.TRUE);
        PDFIndirectObject.resetObjectNumber();
        PDFIndirectObject io2 = new PDFIndirectObject(PDFBoolean.TRUE);
        PDFIndirectObject io3 = new PDFIndirectObject(PDFBoolean.TRUE);
        PDFIndirectObject.resetObjectNumber();
        PDFIndirectObject io4 = new PDFIndirectObject(PDFBoolean.FALSE);

        assertTrue(io1.equals(io1));
        assertFalse(io1.equals(null));
        assertFalse(io1.equals(PDFBoolean.TRUE));

        assertTrue(io1.equals(io2));
        assertEquals(io1.hashCode(), io2.hashCode());
        assertFalse(io1.equals(io3));
        assertFalse(io1.equals(io4));
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void equalsReference() {
        PDFIndirectObject.resetObjectNumber();
        PDFIndirectObject.Reference io1 = new PDFIndirectObject(PDFBoolean.TRUE).getReference();
        PDFIndirectObject.resetObjectNumber();
        PDFIndirectObject.Reference io2 = new PDFIndirectObject(PDFBoolean.TRUE).getReference();
        PDFIndirectObject.Reference io3 = new PDFIndirectObject(PDFBoolean.TRUE).getReference();

        assertTrue(io1.equals(io1));
        assertFalse(io1.equals(null));
        assertFalse(io1.equals(PDFBoolean.TRUE));

        assertTrue(io1.equals(io2));
        assertEquals(io1.hashCode(), io2.hashCode());
        assertFalse(io1.equals(io3));
    }
}