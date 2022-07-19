/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SetLineCapStyleTest {

    private GraphicsState graphicsState;

    @BeforeEach
    public void setup() {
        graphicsState = new GraphicsState();
        graphicsState.setLineCap(LineCapStyle.ROUND_CAP);
    }

    @Test
    public void changesStateFalse() {
        SetLineCapStyle operator = new SetLineCapStyle(LineCapStyle.ROUND_CAP);
        assertFalse(operator.changesState(graphicsState));
    }

    @Test
    public void changesStateTrue() {
        SetLineCapStyle operator = new SetLineCapStyle(LineCapStyle.PROJECTING_SQUARE);
        assertTrue(operator.changesState(graphicsState));
    }

    @Test
    public void changeState() {
        SetLineCapStyle operator = new SetLineCapStyle(LineCapStyle.PROJECTING_SQUARE);
        operator.changeState(graphicsState);
        assertEquals(LineCapStyle.PROJECTING_SQUARE, graphicsState.getLineCap());
    }

    @Test
    public void writeToPDF() throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        LineCapStyle.ROUND_CAP.style().writeToPDF(expected);
        expected.write(' ');
        expected.write('J');
        expected.write('\n');

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        SetLineCapStyle operator = new SetLineCapStyle(LineCapStyle.ROUND_CAP);
        operator.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void equals() {
        SetLineCapStyle operator = new SetLineCapStyle(LineCapStyle.ROUND_CAP);
        assertTrue(operator.equals(operator));
        assertFalse(operator.equals(null));
        assertFalse(operator.equals(Boolean.TRUE));
        SetLineCapStyle op1 = new SetLineCapStyle(LineCapStyle.ROUND_CAP);
        SetLineCapStyle op2 = new SetLineCapStyle(LineCapStyle.PROJECTING_SQUARE);
        assertTrue(operator.equals(op1));
        assertEquals(operator.hashCode(), op1.hashCode());
        assertFalse(operator.equals(op2));
    }
}