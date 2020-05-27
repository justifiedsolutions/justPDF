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

public class SetGrayStrokeColorTest {
    public final PDFReal g = new PDFReal(.2f);
    public final PDFReal x = new PDFReal(.9f);

    public SetGrayStrokeColor operator;
    public GraphicsState graphicsState;

    @BeforeEach
    public void setup() {
        graphicsState = new GraphicsState();
        operator = new SetGrayStrokeColor(g);
    }

    @Test
    public void changesStateFalse() {
        DeviceGray colorSpace = new DeviceGray(g);
        graphicsState.setStrokeColorSpace(colorSpace);
        assertFalse(operator.changesState(graphicsState));
    }

    @Test
    public void changesStateTrue() {
        DeviceGray colorSpace = new DeviceGray(x);
        graphicsState.setStrokeColorSpace(colorSpace);
        assertTrue(operator.changesState(graphicsState));
    }

    @Test
    public void changeState() {
        DeviceGray colorSpace = new DeviceGray(g);
        operator.changeState(graphicsState);
        assertEquals(colorSpace, graphicsState.getStrokeColorSpace());
    }

    @Test
    public void writeToPDF() throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        g.writeToPDF(expected);
        expected.write(' ');
        expected.write('G');
        expected.write('\n');

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }
}