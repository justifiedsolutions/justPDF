/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class BeginTextTest {

    @Test
    public void writeToPDF() throws IOException {
        byte[] expected = {(byte) 'B', (byte) 'T', (byte) '\n'};
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        new BeginText().writeToPDF(actual);
        assertArrayEquals(expected, actual.toByteArray());
    }
}