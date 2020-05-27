/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SetCMYKStrokeColorTest {
    public final PDFReal c = new PDFReal(0);
    public final PDFReal m = new PDFReal(.2f);
    public final PDFReal y = new PDFReal(.6f);
    public final PDFReal k = new PDFReal(1);
    public final PDFReal x = new PDFReal(.9f);

    public SetCMYKStrokeColor operator;
    public GraphicsState graphicsState;

    @BeforeEach
    public void setup() {
        operator = new SetCMYKStrokeColor(c, m, y, k);
        graphicsState = new GraphicsState();
    }

    @Test
    public void changesStateFalse() {
        DeviceCMYK colorSpace = new DeviceCMYK(c, m, y, k);
        graphicsState.setStrokeColorSpace(colorSpace);
        assertFalse(operator.changesState(graphicsState));
    }

    @Test
    public void changesStateTrue() {
        DeviceCMYK colorSpace = new DeviceCMYK(c, m, y, x);
        graphicsState.setStrokeColorSpace(colorSpace);
        assertTrue(operator.changesState(graphicsState));
    }

    @Test
    public void changeState() {
        DeviceCMYK colorSpace = new DeviceCMYK(c, m, y, k);
        operator.changeState(graphicsState);
        assertEquals(colorSpace, graphicsState.getStrokeColorSpace());
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
        expected.write('K');
        expected.write('\n');

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }
}