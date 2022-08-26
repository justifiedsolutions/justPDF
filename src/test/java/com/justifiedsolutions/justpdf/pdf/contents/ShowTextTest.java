/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShowTextTest {

    private ShowText operator;

    @BeforeEach
    void setup() {
        operator = new ShowText(new PDFString("string"));
    }

    @Test
    void isCollapsableFalse() {
        TextOperator other = new MoveToNextLine();
        assertFalse(operator.isCollapsable(other));
    }

    @Test
    void isCollapsableTrue() {
        ShowText other = new ShowText(new PDFString(" along"));
        assertTrue(operator.isCollapsable(other));
    }

    @Test
    void collapse() {
        ShowText expected = new ShowText(new PDFString("string along"));
        ShowText other = new ShowText(new PDFString(" along"));
        GraphicsOperator actual = operator.collapse(other);
        assertEquals(expected, actual);
    }

    @Test
    void writeToPDF() throws IOException {
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);
        PDFString text = new PDFString("string");
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        text.writeToPDF(expected);
        expected.writeBytes("Tj\n".getBytes(StandardCharsets.US_ASCII));
        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }

    @Test
    @SuppressWarnings({ "unlikely-arg-type", "PMD.SimplifiableTestAssertion" })
    void equals() {
        PDFString foo = new PDFString("foo");
        PDFString bar = new PDFString("bar");
        ShowText operator = new ShowText(foo);
        assertTrue(operator.equals(operator));
        assertFalse(operator.equals(null));
        assertFalse(operator.equals(Boolean.TRUE));
        ShowText op1 = new ShowText(foo);
        ShowText op2 = new ShowText(bar);
        assertTrue(operator.equals(op1));
        assertEquals(operator.hashCode(), op1.hashCode());
        assertFalse(operator.equals(op2));
    }
}