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

import static org.junit.jupiter.api.Assertions.*;

public class SetRGBFillColorTest {
    private final PDFReal r = new PDFReal(0);
    private final PDFReal g = new PDFReal(.2f);
    private final PDFReal b = new PDFReal(.6f);
    private final PDFReal x = new PDFReal(.9f);

    private SetRGBFillColor operator;
    private GraphicsState graphicsState;

    @BeforeEach
    public void setup() {
        operator = new SetRGBFillColor(new DeviceRGB(r, g, b));
        graphicsState = new GraphicsState();
    }

    @Test
    public void changesStateFalse() {
        DeviceRGB colorSpace = new DeviceRGB(r, g, b);
        graphicsState.setFillColorSpace(colorSpace);
        assertFalse(operator.changesState(graphicsState));
    }

    @Test
    public void changesStateTrue() {
        DeviceRGB colorSpace = new DeviceRGB(r, g, x);
        graphicsState.setFillColorSpace(colorSpace);
        assertTrue(operator.changesState(graphicsState));
    }

    @Test
    public void changeState() {
        DeviceRGB colorSpace = new DeviceRGB(r, g, b);
        operator.changeState(graphicsState);
        assertEquals(colorSpace, graphicsState.getFillColorSpace());
    }

    @Test
    public void writeToPDF() throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        r.writeToPDF(expected);
        expected.write(' ');
        g.writeToPDF(expected);
        expected.write(' ');
        b.writeToPDF(expected);
        expected.write(' ');
        expected.write('r');
        expected.write('g');
        expected.write('\n');

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void equals() {
        DeviceRGB foo = new DeviceRGB(r, g, b);
        DeviceRGB bar = new DeviceRGB(r, g, x);
        SetRGBFillColor operator = new SetRGBFillColor(foo);
        assertTrue(operator.equals(operator));
        assertFalse(operator.equals(null));
        assertFalse(operator.equals(Boolean.TRUE));
        SetRGBFillColor op1 = new SetRGBFillColor(foo);
        SetRGBFillColor op2 = new SetRGBFillColor(bar);
        assertTrue(operator.equals(op1));
        assertEquals(operator.hashCode(), op1.hashCode());
        assertFalse(operator.equals(op2));
    }
}