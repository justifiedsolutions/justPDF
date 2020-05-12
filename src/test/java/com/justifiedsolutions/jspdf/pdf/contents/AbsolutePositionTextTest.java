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

public class AbsolutePositionTextTest {

    private AbsolutePositionText operator;

    @BeforeEach
    public void setup() {
        operator = new AbsolutePositionText(new PDFReal(10), new PDFReal(10));
    }

    @Test
    public void isCollapsableFalse() {
        TextOperator other = new MoveToNextLine();
        assertFalse(operator.isCollapsable(other));
    }

    @Test
    public void isCollapsableTrue() {
        AbsolutePositionText other = new AbsolutePositionText(new PDFReal(5), new PDFReal(5));
        assertTrue(operator.isCollapsable(other));
    }

    @Test
    public void collapse() {
        AbsolutePositionText other = new AbsolutePositionText(new PDFReal(5), new PDFReal(5));
        GraphicsOperator actual = operator.collapse(other);
        assertEquals(other, actual);
    }

    @Test
    public void writeToPDF() throws IOException {
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);
        assertArrayEquals("1 0 0 1 10 10 Tm\n".getBytes(StandardCharsets.US_ASCII), actual.toByteArray());
    }
}