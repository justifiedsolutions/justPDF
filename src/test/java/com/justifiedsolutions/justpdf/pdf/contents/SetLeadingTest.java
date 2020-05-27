/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class SetLeadingTest {

    private SetLeading operator;

    @BeforeEach
    public void setup() {
        operator = new SetLeading(new PDFReal(12));
    }

    @Test
    public void isCollapsableFalse() {
        TextOperator other = new MoveToNextLine();
        assertFalse(operator.isCollapsable(other));
    }

    @Test
    public void isCollapsableTrue() {
        SetLeading other = new SetLeading(new PDFReal(14));
        assertTrue(operator.isCollapsable(other));
    }

    @Test
    public void collapse() {
        SetLeading other = new SetLeading(new PDFReal(14));
        GraphicsOperator actual = operator.collapse(other);
        assertEquals(other, actual);
    }

    @Test
    public void writeToPDF() throws IOException {
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);
        assertArrayEquals("12 TL\n".getBytes(StandardCharsets.US_ASCII), actual.toByteArray());
    }

    @Test
    public void equals() {
        PDFReal foo = new PDFReal(1);
        PDFReal bar = new PDFReal(2);
        SetLeading operator = new SetLeading(foo);
        assertTrue(operator.equals(operator));
        assertFalse(operator.equals(null));
        assertFalse(operator.equals(Boolean.TRUE));
        SetLeading op1 = new SetLeading(foo);
        SetLeading op2 = new SetLeading(bar);
        assertTrue(operator.equals(op1));
        assertEquals(operator.hashCode(), op1.hashCode());
        assertFalse(operator.equals(op2));
    }
}