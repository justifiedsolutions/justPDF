/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import com.justifiedsolutions.jspdf.pdf.object.PDFReal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SetRGBStrokeColorTest {
    public final PDFReal r = new PDFReal(0);
    public final PDFReal g = new PDFReal(.2f);
    public final PDFReal b = new PDFReal(.6f);
    public final PDFReal x = new PDFReal(.9f);

    public SetRGBStrokeColor operator;

    @Mock
    public GraphicsState graphicsState;

    @BeforeEach
    public void setup() {
        operator = new SetRGBStrokeColor(r, g, b);
    }

    @Test
    public void changesStateFalse() {
        DeviceRGB colorSpace = new DeviceRGB(r, g, b);
        when(graphicsState.getStrokeColorSpace()).thenReturn(colorSpace);
        assertFalse(operator.changesState(graphicsState));
    }

    @Test
    public void changesStateTrue() {
        DeviceRGB colorSpace = new DeviceRGB(r, g, x);
        when(graphicsState.getStrokeColorSpace()).thenReturn(colorSpace);
        assertTrue(operator.changesState(graphicsState));
    }

    @Test
    public void changeState() {
        DeviceRGB colorSpace = new DeviceRGB(r, g, b);
        operator.changeState(graphicsState);
        verify(graphicsState).setStrokeColorSpace(colorSpace);
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
}