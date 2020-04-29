/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.operator.text;

import com.justifiedsolutions.jspdf.pdf.object.PDFString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class ShowTextTest {

    private ShowText operator;

    @BeforeEach
    public void setup() {
        operator = new ShowText(new PDFString("string"));
    }

    @Test
    public void isCollapsableFalse() {
        TextOperator other = new MoveToNextLine();
        assertFalse(operator.isCollapsable(other));
    }

    @Test
    public void isCollapsableTrue() {
        ShowText other = new ShowText(new PDFString(" along"));
        assertTrue(operator.isCollapsable(other));
    }

    @Test
    public void collapse() {
        ShowText expected = new ShowText(new PDFString("string along"));
        ShowText other = new ShowText(new PDFString(" along"));
        TextOperator actual = operator.collapse(other);
        assertEquals(expected, actual);
    }

    @Test
    public void writeToPDF() throws IOException {
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);
        PDFString text = new PDFString("string");
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        text.writeToPDF(expected);
        expected.writeBytes("Tj\n".getBytes(StandardCharsets.US_ASCII));
        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }
}