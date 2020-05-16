/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api.content;

import com.justifiedsolutions.justpdf.api.font.Font;
import com.justifiedsolutions.justpdf.api.font.PDFFont;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PhraseTest {

    private Phrase phrase;

    @BeforeEach
    public void setup() {
        phrase = new Phrase();
    }

    @Test
    public void constructors() {
        String string = "foo";
        Chunk content = new Chunk(string);
        Font font = new PDFFont();

        Phrase p;

        p = new Phrase(string);
        assertEquals(content, p.getChunks().get(0));

        p = new Phrase(content);
        assertEquals(content, p.getChunks().get(0));

        p = new Phrase(string, font);
        assertEquals(content, p.getChunks().get(0));
        assertEquals(font, p.getFont());

        p = new Phrase(content, font);
        assertEquals(content, p.getChunks().get(0));
        assertEquals(font, p.getFont());
    }

    @Test
    public void setLeading() {
        float input = 20;
        phrase.setLeading(input);
        assertEquals(input, phrase.getLeading());
    }

    @Test
    public void setFont() {
        Font input = new PDFFont();
        phrase.setFont(input);
        assertEquals(input, phrase.getFont());
    }

    @Test
    public void addContentChunk() {
        Chunk input = new Chunk();
        phrase.add(input);
        assertEquals(input, phrase.getChunks().get(0));
    }

    @Test
    public void addContentNull() {
        phrase.add((Chunk) null);
        assertEquals(0, phrase.getChunks().size());
    }

    @Test
    public void addString() {
        String input = "foo";
        phrase.add(input);
        assertEquals(new Chunk(input), phrase.getChunks().get(0));
    }

    @Test
    public void addStringNull() {
        phrase.add((String) null);
        assertEquals(0, phrase.getChunks().size());
    }

    @Test
    public void testEqualsHashCode() {
        Phrase phrase2 = new Phrase();

        assertEquals(phrase, phrase);
        assertEquals(phrase.hashCode(), phrase.hashCode());
        assertEquals(phrase2, phrase);
        assertEquals(phrase2.hashCode(), phrase.hashCode());

        assertNotEquals(phrase, new Chunk());
        assertNotEquals(phrase, null);
    }
}