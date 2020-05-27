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

public class SetRGBStrokeColorTest {
    public final PDFReal r = new PDFReal(0);
    public final PDFReal g = new PDFReal(.2f);
    public final PDFReal b = new PDFReal(.6f);
    public final PDFReal x = new PDFReal(.9f);

    public SetRGBStrokeColor operator;
    public GraphicsState graphicsState;

    @BeforeEach
    public void setup() {
        operator = new SetRGBStrokeColor(r, g, b);
        graphicsState = new GraphicsState();
    }

    @Test
    public void changesStateFalse() {
        DeviceRGB colorSpace = new DeviceRGB(r, g, b);
        graphicsState.setStrokeColorSpace(colorSpace);
        assertFalse(operator.changesState(graphicsState));
    }

    @Test
    public void changesStateTrue() {
        DeviceRGB colorSpace = new DeviceRGB(r, g, x);
        graphicsState.setStrokeColorSpace(colorSpace);
        assertTrue(operator.changesState(graphicsState));
    }

    @Test
    public void changeState() {
        DeviceRGB colorSpace = new DeviceRGB(r, g, b);
        operator.changeState(graphicsState);
        assertEquals(colorSpace, graphicsState.getStrokeColorSpace());
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
        expected.write('R');
        expected.write('G');
        expected.write('\n');

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }

    @Test
    public void equals() {
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