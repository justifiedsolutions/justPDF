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

public class SetLineWidthTest {

    public final PDFReal width = new PDFReal(.5f);
    public final SetLineWidth operator = new SetLineWidth(width);
    public GraphicsState graphicsState;

    @BeforeEach
    public void setup() {
        graphicsState = new GraphicsState();
        graphicsState.setLineWidth(width);
    }

    @Test
    public void changesStateFalse() {
        graphicsState.setLineWidth(width);
        assertFalse(operator.changesState(graphicsState));
    }

    @Test
    public void changesStateTrue() {
        graphicsState.setLineWidth(new PDFReal(.2f));
        assertTrue(operator.changesState(graphicsState));
    }

    @Test
    public void changeState() {
        operator.changeState(graphicsState);
        assertEquals(width, graphicsState.getLineWidth());
    }

    @Test
    public void writeToPDF() throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        width.writeToPDF(expected);
        expected.write(' ');
        expected.write('w');
        expected.write('\n');

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }
}