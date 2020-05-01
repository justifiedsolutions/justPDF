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
public class SetCMYKFillColorTest {
    private final PDFReal c = new PDFReal(0);
    private final PDFReal m = new PDFReal(.2f);
    private final PDFReal y = new PDFReal(.6f);
    private final PDFReal k = new PDFReal(1);
    private final PDFReal x = new PDFReal(.9f);

    private SetCMYKFillColor operator;

    @Mock
    private GraphicsState graphicsState;

    @BeforeEach
    public void setup() {
        operator = new SetCMYKFillColor(c, m, y, k);
    }

    @Test
    public void changesStateFalse() {
        DeviceCMYK colorSpace = new DeviceCMYK(c, m, y, k);
        when(graphicsState.getFillColorSpace()).thenReturn(colorSpace);
        assertFalse(operator.changesState(graphicsState));
    }

    @Test
    public void changesStateTrue() {
        DeviceCMYK colorSpace = new DeviceCMYK(c, m, y, x);
        when(graphicsState.getFillColorSpace()).thenReturn(colorSpace);
        assertTrue(operator.changesState(graphicsState));
    }

    @Test
    public void changeState() {
        DeviceCMYK colorSpace = new DeviceCMYK(c, m, y, k);
        operator.changeState(graphicsState);
        verify(graphicsState).setFillColorSpace(colorSpace);
    }

    @Test
    public void writeToPDF() throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        c.writeToPDF(expected);
        expected.write(' ');
        m.writeToPDF(expected);
        expected.write(' ');
        y.writeToPDF(expected);
        expected.write(' ');
        k.writeToPDF(expected);
        expected.write(' ');
        expected.write('k');
        expected.write('\n');

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }
}