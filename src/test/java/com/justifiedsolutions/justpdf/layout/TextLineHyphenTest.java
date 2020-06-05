package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.content.Chunk;
import com.justifiedsolutions.justpdf.api.font.PDFFont;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextLineHyphenTest {

    @BeforeAll
    static void beforeAll() {
        System.setProperty("EnableHyphenation", "true");
    }

    @AfterAll
    static void afterAll() {
        System.clearProperty("EnableHyphenation");
    }

    @Test
    public void append() {
        Chunk input = new Chunk("hyphenation", new PDFFont());
        TextLine line = new TextLine(20, 0, 0);
        Chunk remainder = line.append(input);
        Chunk expected = new Chunk("phenation", new PDFFont());
        assertEquals(expected, remainder);
    }
}