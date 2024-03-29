/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.content.Chunk;
import com.justifiedsolutions.justpdf.api.font.PDFFont;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextLineHyphenTest {

    @BeforeAll
    public static void beforeAll() {
        System.setProperty("EnableHyphenation", "true");
    }

    @AfterAll
    public static void afterAll() {
        System.clearProperty("EnableHyphenation");
    }

    @Test
    void append() {
        Chunk input = new Chunk("hyphenation", new PDFFont());
        TextLine line = new TextLine(20, 0, 0);
        Chunk remainder = line.append(input);
        Chunk expected = new Chunk("phenation", new PDFFont());
        assertEquals(expected, remainder);
    }
}