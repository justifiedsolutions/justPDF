/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFName;
import com.justifiedsolutions.justpdf.pdf.object.PDFReal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SetFontTest {

    private SetFont operator;

    @BeforeEach
    void setup() {
        operator = new SetFont(new PDFName("F1"), new PDFReal(12));
    }

    @Test
    void isCollapsableFalse() {
        TextOperator other = new MoveToNextLine();
        assertFalse(operator.isCollapsable(other));
    }

    @Test
    void isCollapsableTrue() {
        SetFont other = new SetFont(new PDFName("F1"), new PDFReal(14));
        assertTrue(operator.isCollapsable(other));
    }

    @Test
    void collapse() {
        SetFont other = new SetFont(new PDFName("F1"), new PDFReal(14));
        GraphicsOperator actual = operator.collapse(other);
        assertEquals(other, actual);
    }

    @Test
    void writeToPDF() throws IOException {
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);
        assertArrayEquals("/F1 12 Tf\n".getBytes(StandardCharsets.US_ASCII), actual.toByteArray());
    }

    @Test
    @SuppressWarnings({ "unlikely-arg-type", "PMD.SimplifiableTestAssertion" })
    void equals() {
        PDFReal small = new PDFReal(8);
        PDFReal large = new PDFReal(12);
        PDFName foo = new PDFName("foo");
        PDFName bar = new PDFName("bar");
        SetFont operator = new SetFont(foo, large);
        assertTrue(operator.equals(operator));
        assertFalse(operator.equals(null));
        assertFalse(operator.equals(Boolean.TRUE));
        SetFont op1 = new SetFont(foo, large);
        SetFont op2 = new SetFont(bar, large);
        SetFont op3 = new SetFont(foo, small);
        assertTrue(operator.equals(op1));
        assertEquals(operator.hashCode(), op1.hashCode());
        assertFalse(operator.equals(op2));
        assertFalse(operator.equals(op3));
    }
}