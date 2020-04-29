/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.operator.text;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class MoveToNextLineTest {

    private MoveToNextLine operator;

    @BeforeEach
    public void setup() {
        operator = new MoveToNextLine();
    }

    @Test
    public void isCollapsable() {
        assertFalse(operator.isCollapsable(null));
    }

    @Test
    public void collapse() {
        assertNull(operator.collapse(null));
    }

    @Test
    public void writeToPDF() throws IOException {
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);
        assertArrayEquals("T*\n".getBytes(StandardCharsets.US_ASCII), actual.toByteArray());
    }
}