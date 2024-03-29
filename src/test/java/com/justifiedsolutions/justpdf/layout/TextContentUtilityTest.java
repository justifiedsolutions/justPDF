/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.content.Chunk;
import com.justifiedsolutions.justpdf.api.content.Paragraph;
import com.justifiedsolutions.justpdf.api.content.Phrase;
import com.justifiedsolutions.justpdf.api.content.TextContent;
import com.justifiedsolutions.justpdf.api.font.Font;
import com.justifiedsolutions.justpdf.api.font.PDFFont;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TextContentUtilityTest {

    @Test
    void getParagraphParagraph() {
        Paragraph paragraph = new Paragraph();
        assertSame(paragraph, TextContentUtility.getParagraph(paragraph));
    }

    @Test
    void getParagraphPhrase() {
        Font defaultFont = new PDFFont();
        Phrase phrase = new Phrase("foo", defaultFont);
        Paragraph actual = TextContentUtility.getParagraph(phrase);
        assertEquals(phrase.getLeading(), actual.getLeading());
        assertEquals(1, actual.getContent().size());
        assertEquals(phrase.getChunks().get(0), actual.getContent().get(0));
    }

    @Test
    void getParagraphChunk() {
        Font defaultFont = new PDFFont();
        Chunk chunk = new Chunk("foo", defaultFont);
        Paragraph actual = TextContentUtility.getParagraph(chunk);
        assertEquals(chunk.getFont(), actual.getFont());
        assertEquals(chunk.isHyphenate(), actual.isHyphenate());
        assertEquals(1, actual.getContent().size());
        assertEquals(chunk, actual.getContent().get(0));
    }

    @Test
    void getParagraphNull() {
        assertNull(TextContentUtility.getParagraph(null));
    }

    @Test
    void getParagraphUnknown() {
        TextContent unknown = new TextContent() {
            @Override
            public Font getFont() {
                return null;
            }

            @Override
            public void setFont(Font font) {
                // do nothing
            }

            @Override
            public boolean isHyphenate() {
                return false;
            }

            @Override
            public void setHyphenate(boolean hyphenate) {
                // do nothing
            }
        };
        assertThrows(IllegalArgumentException.class, () -> TextContentUtility.getParagraph(unknown));
    }

    @Test
    void initializeFontsParagraph() {
        Font defaultFont = new PDFFont();
        Font courier = new PDFFont(PDFFont.FontName.COURIER);

        Paragraph paragraph = new Paragraph();
        TextContentUtility.initializeFonts(paragraph);
        assertEquals(defaultFont, paragraph.getFont());

        paragraph = new Paragraph("foo", courier);
        TextContentUtility.initializeFonts(paragraph);
        assertEquals(courier, paragraph.getFont());

        Phrase phrase = new Phrase("foo");
        paragraph = new Paragraph(phrase);
        TextContentUtility.initializeFonts(paragraph);
        assertEquals(defaultFont, paragraph.getFont());
        assertEquals(defaultFont, phrase.getFont());
        assertEquals(defaultFont, phrase.getChunks().get(0).getFont());
    }

    @Test
    void initializeFontsPhrase() {
        Font defaultFont = new PDFFont();
        Font courier = new PDFFont(PDFFont.FontName.COURIER);

        Phrase phrase = new Phrase("foo");
        TextContentUtility.initializeFonts(phrase, defaultFont);
        assertEquals(defaultFont, phrase.getFont());
        assertEquals(defaultFont, phrase.getChunks().get(0).getFont());

        Chunk foo = new Chunk("foo", courier);
        Phrase p2 = new Phrase(foo);
        TextContentUtility.initializeFonts(p2, defaultFont);
        assertEquals(defaultFont, p2.getFont());
        assertEquals(courier, p2.getChunks().get(0).getFont());

        Phrase p3 = new Phrase(foo, courier);
        TextContentUtility.initializeFonts(p3, defaultFont);
        assertEquals(courier, p3.getFont());
        assertEquals(courier, p3.getChunks().get(0).getFont());
    }

    @Test
    void initializeFontsChunk() {
        Font defaultFont = new PDFFont();
        Font courier = new PDFFont(PDFFont.FontName.COURIER);

        Chunk chunk = new Chunk();
        TextContentUtility.initializeFonts(chunk, defaultFont);
        assertEquals(defaultFont, chunk.getFont());

        Chunk chunk1 = new Chunk("foo", courier);
        TextContentUtility.initializeFonts(chunk1, defaultFont);
        assertEquals(courier, chunk1.getFont());
    }
}