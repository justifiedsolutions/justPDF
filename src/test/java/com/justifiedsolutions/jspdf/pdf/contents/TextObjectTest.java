/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import com.justifiedsolutions.jspdf.pdf.object.PDFName;
import com.justifiedsolutions.jspdf.pdf.object.PDFReal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TextObjectTest {

    private TextObject textObject;

    @BeforeEach
    public void setup() {
        textObject = new TextObject();
    }

    @Test
    public void writeToPDFNoOperators() throws IOException {
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        textObject.writeToPDF(actual);

        assertArrayEquals("".getBytes(StandardCharsets.US_ASCII), actual.toByteArray());
    }

    @Test
    public void writeToPDF1Operator2() throws IOException {
        textObject.addOperator(new SetFont(new PDFName("F1"), new PDFReal(16)));
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        textObject.writeToPDF(actual);

        assertArrayEquals("BT\n/F1 16 Tf\nET".getBytes(StandardCharsets.US_ASCII), actual.toByteArray());
    }

    @Test
    public void writeToPDF2OperatorsCollapsable() throws IOException {
        textObject.addOperator(new SetFont(new PDFName("F1"), new PDFReal(12)));
        textObject.addOperator(new SetFont(new PDFName("F1"), new PDFReal(16)));
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        textObject.writeToPDF(actual);

        assertArrayEquals("BT\n/F1 16 Tf\nET".getBytes(StandardCharsets.US_ASCII), actual.toByteArray());
    }

    @Test
    public void writeToPDF2OperatorsNotCollapsable() throws IOException {
        textObject.addOperator(new SetFont(new PDFName("F1"), new PDFReal(12)));
        textObject.addOperator(new SetLeading(new PDFReal(14)));
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        textObject.writeToPDF(actual);

        assertArrayEquals("BT\n/F1 12 Tf\n14 TL\nET".getBytes(StandardCharsets.US_ASCII), actual.toByteArray());
    }
}