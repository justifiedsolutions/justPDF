/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.doc;

import com.justifiedsolutions.jspdf.pdf.object.PDFIndirectObject;
import com.justifiedsolutions.jspdf.pdf.object.PDFInteger;
import com.justifiedsolutions.jspdf.pdf.object.PDFNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class PDFXRefTableTest {

    @BeforeEach
    public void reset() {
        PDFIndirectObject.resetObjectNumber();
    }

    @Test
    public void writeToPDF1() throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.writeBytes("xref\n".getBytes(StandardCharsets.US_ASCII));
        expected.writeBytes("0 2\n".getBytes(StandardCharsets.US_ASCII));
        expected.writeBytes("0000000000 65535 f \n".getBytes(StandardCharsets.US_ASCII));
        expected.writeBytes("0000000042 00000 n \n".getBytes(StandardCharsets.US_ASCII));

        PDFIndirectObject indirectObject = new PDFIndirectObject(PDFNull.NULL);
        indirectObject.setByteOffset(new PDFInteger(42));
        List<PDFIndirectObject> indirectObjects = new ArrayList<>();
        indirectObjects.add(indirectObject);
        PDFXRefTable xrefTable = new PDFXRefTable();
        xrefTable.setIndirectObjects(indirectObjects);

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        xrefTable.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }

    @Test
    public void writeToPDF5() throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.writeBytes("xref\n".getBytes(StandardCharsets.US_ASCII));
        expected.writeBytes("0 6\n".getBytes(StandardCharsets.US_ASCII));
        expected.writeBytes("0000000000 65535 f \n".getBytes(StandardCharsets.US_ASCII));
        expected.writeBytes("0000000050 00000 n \n".getBytes(StandardCharsets.US_ASCII));
        expected.writeBytes("0000000100 00000 n \n".getBytes(StandardCharsets.US_ASCII));
        expected.writeBytes("0000000150 00000 n \n".getBytes(StandardCharsets.US_ASCII));
        expected.writeBytes("0000000200 00000 n \n".getBytes(StandardCharsets.US_ASCII));
        expected.writeBytes("0000000250 00000 n \n".getBytes(StandardCharsets.US_ASCII));

        List<PDFIndirectObject> indirectObjects = new ArrayList<>();
        PDFIndirectObject indirectObject1 = new PDFIndirectObject(PDFNull.NULL);
        indirectObject1.setByteOffset(new PDFInteger(50));
        indirectObjects.add(indirectObject1);
        PDFIndirectObject indirectObject2 = new PDFIndirectObject(PDFNull.NULL);
        indirectObject2.setByteOffset(new PDFInteger(100));
        indirectObjects.add(indirectObject2);
        PDFIndirectObject indirectObject3 = new PDFIndirectObject(PDFNull.NULL);
        indirectObject3.setByteOffset(new PDFInteger(150));
        indirectObjects.add(indirectObject3);
        PDFIndirectObject indirectObject4 = new PDFIndirectObject(PDFNull.NULL);
        indirectObject4.setByteOffset(new PDFInteger(200));
        indirectObjects.add(indirectObject4);
        PDFIndirectObject indirectObject5 = new PDFIndirectObject(PDFNull.NULL);
        indirectObject5.setByteOffset(new PDFInteger(250));
        indirectObjects.add(indirectObject5);

        PDFXRefTable xrefTable = new PDFXRefTable();
        xrefTable.setIndirectObjects(indirectObjects);

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        xrefTable.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }
}