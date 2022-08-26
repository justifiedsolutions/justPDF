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

class SetRGBStrokeColorTest {
    private final PDFReal r = new PDFReal(0);
    private final PDFReal g = new PDFReal(.2f);
    private final PDFReal b = new PDFReal(.6f);
    private final PDFReal x = new PDFReal(.9f);

    private SetRGBStrokeColor operator;
    private GraphicsState graphicsState;

    @BeforeEach
    void setup() {
        operator = new SetRGBStrokeColor(new DeviceRGB(r, g, b));
        graphicsState = new GraphicsState();
    }

    @Test
    void changesStateFalse() {
        DeviceRGB colorSpace = new DeviceRGB(r, g, b);
        graphicsState.setStrokeColorSpace(colorSpace);
        assertFalse(operator.changesState(graphicsState));
    }

    @Test
    void changesStateTrue() {
        DeviceRGB colorSpace = new DeviceRGB(r, g, x);
        graphicsState.setStrokeColorSpace(colorSpace);
        assertTrue(operator.changesState(graphicsState));
    }

    @Test
    void changeState() {
        DeviceRGB colorSpace = new DeviceRGB(r, g, b);
        operator.changeState(graphicsState);
        assertEquals(colorSpace, graphicsState.getStrokeColorSpace());
    }

    @Test
    void writeToPDF() throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        r.writeToPDF(expected);
        expected.write(' ');
        g.writeToPDF(expected);
        expected.write(' ');
        b.writeToPDF(expected);
        expected.write(' ');
        expected.write('R');
        expected.write('G');
        expected.write('\n');

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }

    @Test
    @SuppressWarnings({ "unlikely-arg-type", "PMD.SimplifiableTestAssertion" })
    void equals() {
        DeviceRGB foo = new DeviceRGB(r, g, b);
        DeviceRGB bar = new DeviceRGB(r, g, x);
        SetRGBStrokeColor operator = new SetRGBStrokeColor(foo);
        assertTrue(operator.equals(operator));
        assertFalse(operator.equals(null));
        assertFalse(operator.equals(Boolean.TRUE));
        SetRGBStrokeColor op1 = new SetRGBStrokeColor(foo);
        SetRGBStrokeColor op2 = new SetRGBStrokeColor(bar);
        assertTrue(operator.equals(op1));
        assertEquals(operator.hashCode(), op1.hashCode());
        assertFalse(operator.equals(op2));
    }
}