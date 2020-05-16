/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;
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
public class SetGrayStrokeColorTest {
    public final PDFReal g = new PDFReal(.2f);
    public final PDFReal x = new PDFReal(.9f);

    public SetGrayStrokeColor operator;

    @Mock
    public GraphicsState graphicsState;

    @BeforeEach
    public void setup() {
        operator = new SetGrayStrokeColor(g);
    }

    @Test
    public void changesStateFalse() {
        DeviceGray colorSpace = new DeviceGray(g);
        when(graphicsState.getStrokeColorSpace()).thenReturn(colorSpace);
        assertFalse(operator.changesState(graphicsState));
    }

    @Test
    public void changesStateTrue() {
        DeviceGray colorSpace = new DeviceGray(x);
        when(graphicsState.getStrokeColorSpace()).thenReturn(colorSpace);
        assertTrue(operator.changesState(graphicsState));
    }

    @Test
    public void changeState() {
        DeviceGray colorSpace = new DeviceGray(g);
        operator.changeState(graphicsState);
        verify(graphicsState).setStrokeColorSpace(colorSpace);
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