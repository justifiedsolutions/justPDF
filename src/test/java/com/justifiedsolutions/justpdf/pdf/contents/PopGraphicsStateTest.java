/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class PopGraphicsStateTest {

    @Test
    void writeToPDF() throws IOException {
        byte[] expected = { (byte) 'Q', (byte) '\n' };
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        new PopGraphicsState().writeToPDF(actual);
        assertArrayEquals(expected, actual.toByteArray());
    }
}