/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api.content;

import com.justifiedsolutions.justpdf.api.font.Font;
import com.justifiedsolutions.justpdf.api.font.PDFFont;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

        Phrase copy = new Phrase(p, p.getChunks());
        assertEquals(p, copy);
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
    public void setHyphenate() {
        assertTrue(phrase.isHyphenate());
        phrase.setHyphenate(false);
        assertFalse(phrase.isHyphenate());
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
    public void testToString() {
        String text = "Welcome to the jungle!";
        phrase.add("Welcome ");
        phrase.add("to ");
        phrase.add("the jungle!");
        assertEquals(text, phrase.toString());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void testEqualsHashCode() {
        Phrase phrase2 = new Phrase(phrase, phrase.getChunks());

        assertTrue(phrase.equals(phrase));
        assertFalse(phrase.equals(null));
        assertFalse(phrase.equals(Boolean.TRUE));

        assertEquals(phrase.hashCode(), phrase2.hashCode());

        Phrase p4 = new Phrase(phrase, phrase.getChunks());
        p4.setLeading(5);
        assertFalse(phrase.equals(p4));

        Phrase p5 = new Phrase(phrase, phrase.getChunks());
        p5.add("foo");
        assertFalse(phrase.equals(p5));

        Phrase p6 = new Phrase(phrase, phrase.getChunks());
        p6.setFont(new PDFFont(PDFFont.FontName.COURIER));
        assertFalse(phrase.equals(p6));

        Phrase p7 = new Phrase(phrase, phrase.getChunks());
        p7.setHyphenate(false);
        assertFalse(phrase.equals(p7));
    }
}