/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class FillAndStrokePathTest {

    @Test
    public void writeToPDF() throws IOException {
        byte[] expected = {(byte) 'B', (byte) '\n'};
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        new FillAndStrokePath().writeToPDF(actual);
        assertArrayEquals(expected, actual.toByteArray());
    }
}