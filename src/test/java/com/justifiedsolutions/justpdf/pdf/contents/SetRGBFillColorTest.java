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
    public final PDFReal r = new PDFReal(0);
    public final PDFReal g = new PDFReal(.2f);
    public final PDFReal b = new PDFReal(.6f);
    public final PDFReal x = new PDFReal(.9f);

    public SetRGBFillColor operator;
    public GraphicsState graphicsState;

    @BeforeEach
    public void setup() {
        operator = new SetRGBFillColor(r, g, b);
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
}