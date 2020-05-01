/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

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
public class SetLineCapStyleTest {

    private final SetLineCapStyle operator = new SetLineCapStyle(LineCapStyle.ROUND_CAP);
    @Mock
    private GraphicsState graphicsState;

    @Test
    public void changesStateFalse() {
        when(graphicsState.getLineCap()).thenReturn(LineCapStyle.ROUND_CAP);
        assertFalse(operator.changesState(graphicsState));
    }

    @Test
    public void changesStateTrue() {
        when(graphicsState.getLineCap()).thenReturn(LineCapStyle.PROJECTING_SQUARE);
        assertTrue(operator.changesState(graphicsState));
    }

    @Test
    public void changeState() {
        operator.changeState(graphicsState);
        verify(graphicsState).setLineCap(LineCapStyle.ROUND_CAP);
    }

    @Test
    public void writeToPDF() throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        LineCapStyle.ROUND_CAP.style().writeToPDF(expected);
        expected.write(' ');
        expected.write('J');
        expected.write('\n');

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }
}