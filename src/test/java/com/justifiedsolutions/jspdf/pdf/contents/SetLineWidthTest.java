/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import com.justifiedsolutions.jspdf.pdf.object.PDFReal;
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
public class SetLineWidthTest {

    public final PDFReal width = new PDFReal(.5f);
    public final SetLineWidth operator = new SetLineWidth(width);
    @Mock
    public GraphicsState graphicsState;

    @Test
    public void changesStateFalse() {
        when(graphicsState.getLineWidth()).thenReturn(width);
        assertFalse(operator.changesState(graphicsState));
    }

    @Test
    public void changesStateTrue() {
        when(graphicsState.getLineWidth()).thenReturn(new PDFReal(.2f));
        assertTrue(operator.changesState(graphicsState));
    }

    @Test
    public void changeState() {
        operator.changeState(graphicsState);
        verify(graphicsState).setLineWidth(width);
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