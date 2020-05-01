/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import com.justifiedsolutions.jspdf.pdf.object.PDFReal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTextTest {

    private PositionText operator;

    @BeforeEach
    public void setup() {
        operator = new PositionText(new PDFReal(10), new PDFReal(10));
    }

    @Test
    public void isCollapsableFalse() {
        TextOperator other = new MoveToNextLine();
        assertFalse(operator.isCollapsable(other));
    }

    @Test
    public void isCollapsableTrue() {
        PositionText other = new PositionText(new PDFReal(5), new PDFReal(5));
        assertTrue(operator.isCollapsable(other));
    }

    @Test
    public void collapse() {
        PositionText expected = new PositionText(new PDFReal(15), new PDFReal(15));
        PositionText other = new PositionText(new PDFReal(5), new PDFReal(5));
        TextOperator actual = operator.collapse(other);
        assertEquals(expected, actual);
    }

    @Test
    public void writeToPDF() throws IOException {
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);
        assertArrayEquals("10 10 Td\n".getBytes(StandardCharsets.US_ASCII), actual.toByteArray());
    }
}