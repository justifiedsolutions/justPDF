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
    private final PDFReal g = new PDFReal(.2f);
    private final PDFReal x = new PDFReal(.9f);
    private final DeviceGray gColorSpace = new DeviceGray(g);
    private final DeviceGray xColorSpace = new DeviceGray(x);

    private SetGrayStrokeColor operator;
    private GraphicsState graphicsState;

    @BeforeEach
    public void setup() {
        graphicsState = new GraphicsState();
        operator = new SetGrayStrokeColor(gColorSpace);
    }

    @Test
    public void changesStateFalse() {
        graphicsState.setStrokeColorSpace(gColorSpace);
        assertFalse(operator.changesState(graphicsState));
    }

    @Test
    public void changesStateTrue() {
        graphicsState.setStrokeColorSpace(xColorSpace);
        assertTrue(operator.changesState(graphicsState));
    }

    @Test
    public void changeState() {
        operator.changeState(graphicsState);
        assertEquals(gColorSpace, graphicsState.getStrokeColorSpace());
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