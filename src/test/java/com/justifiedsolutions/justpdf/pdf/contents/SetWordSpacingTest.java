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

public class SetWordSpacingTest {

    private SetWordSpacing operator;

    @BeforeEach
    public void setup() {
        operator = new SetWordSpacing(new PDFReal(12));
    }

    @Test
    public void isCollapsableFalse() {
        TextOperator other = new MoveToNextLine();
        assertFalse(operator.isCollapsable(other));
    }

    @Test
    public void isCollapsableTrue() {
        SetWordSpacing other = new SetWordSpacing(new PDFReal(14));
        assertTrue(operator.isCollapsable(other));
    }

    @Test
    public void collapse() {
        SetWordSpacing other = new SetWordSpacing(new PDFReal(14));
        GraphicsOperator actual = operator.collapse(other);
        assertEquals(other, actual);
    }

    @Test
    public void changeState() {
        PDFReal foo = new PDFReal(1);
        PDFReal bar = new PDFReal(2);
        GraphicsState graphicsState = new GraphicsState();
        graphicsState.setWordSpacing(foo);
        SetWordSpacing operator = new SetWordSpacing(foo);
        assertFalse(operator.changesState(graphicsState));
        graphicsState.setWordSpacing(bar);
        assertTrue(operator.changesState(graphicsState));
        operator.changeState(graphicsState);
        assertEquals(foo, graphicsState.getWordSpacing());
    }

    @Test
    public void writeToPDF() throws IOException {
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);
        assertArrayEquals("12 Tw\n".getBytes(StandardCharsets.US_ASCII), actual.toByteArray());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void equals() {
        PDFReal foo = new PDFReal(1);
        PDFReal bar = new PDFReal(2);
        SetWordSpacing operator = new SetWordSpacing(foo);
        assertTrue(operator.equals(operator));
        assertFalse(operator.equals(null));
        assertFalse(operator.equals(Boolean.TRUE));
        SetWordSpacing op1 = new SetWordSpacing(foo);
        SetWordSpacing op2 = new SetWordSpacing(bar);
        assertTrue(operator.equals(op1));
        assertEquals(operator.hashCode(), op1.hashCode());
        assertFalse(operator.equals(op2));
    }
}