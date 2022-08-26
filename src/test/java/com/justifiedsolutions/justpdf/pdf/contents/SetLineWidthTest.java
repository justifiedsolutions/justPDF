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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SetLineWidthTest {

    private final PDFReal width = new PDFReal(.5f);
    private final SetLineWidth operator = new SetLineWidth(width);
    private GraphicsState graphicsState;

    @BeforeEach
    void setup() {
        graphicsState = new GraphicsState();
        graphicsState.setLineWidth(width);
    }

    @Test
    void changesStateFalse() {
        graphicsState.setLineWidth(width);
        assertFalse(operator.changesState(graphicsState));
    }

    @Test
    void changesStateTrue() {
        graphicsState.setLineWidth(new PDFReal(.2f));
        assertTrue(operator.changesState(graphicsState));
    }

    @Test
    void changeState() {
        operator.changeState(graphicsState);
        assertEquals(width, graphicsState.getLineWidth());
    }

    @Test
    void writeToPDF() throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        width.writeToPDF(expected);
        expected.write(' ');
        expected.write('w');
        expected.write('\n');

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }

    @Test
    @SuppressWarnings({ "unlikely-arg-type", "PMD.SimplifiableTestAssertion" })
    void equals() {
        PDFReal foo = new PDFReal(1);
        PDFReal bar = new PDFReal(2);
        SetLineWidth operator = new SetLineWidth(foo);
        assertTrue(operator.equals(operator));
        assertFalse(operator.equals(null));
        assertFalse(operator.equals(Boolean.TRUE));
        SetLineWidth op1 = new SetLineWidth(foo);
        SetLineWidth op2 = new SetLineWidth(bar);
        assertTrue(operator.equals(op1));
        assertEquals(operator.hashCode(), op1.hashCode());
        assertFalse(operator.equals(op2));
    }
}