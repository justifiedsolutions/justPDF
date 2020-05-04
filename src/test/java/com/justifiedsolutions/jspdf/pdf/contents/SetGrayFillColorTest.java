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
public class SetGrayFillColorTest {
    public final PDFReal g = new PDFReal(.2f);
    public final PDFReal x = new PDFReal(.9f);

    public SetGrayFillColor operator;

    @Mock
    public GraphicsState graphicsState;

    @BeforeEach
    public void setup() {
        operator = new SetGrayFillColor(g);
    }

    @Test
    public void changesStateFalse() {
        DeviceGray colorSpace = new DeviceGray(g);
        when(graphicsState.getFillColorSpace()).thenReturn(colorSpace);
        assertFalse(operator.changesState(graphicsState));
    }

    @Test
    public void changesStateTrue() {
        DeviceGray colorSpace = new DeviceGray(x);
        when(graphicsState.getFillColorSpace()).thenReturn(colorSpace);
        assertTrue(operator.changesState(graphicsState));
    }

    @Test
    public void changeState() {
        DeviceGray colorSpace = new DeviceGray(g);
        operator.changeState(graphicsState);
        verify(graphicsState).setFillColorSpace(colorSpace);
    }

    @Test
    public void writeToPDF() throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        g.writeToPDF(expected);
        expected.write(' ');
        expected.write('g');
        expected.write('\n');

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }
}