/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api.content;

import com.justifiedsolutions.justpdf.api.HorizontalAlignment;
import com.justifiedsolutions.justpdf.api.font.Font;
import com.justifiedsolutions.justpdf.api.font.PDFFont;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParagraphTest {

    private Paragraph paragraph;

    @BeforeEach
    public void setup() {
        paragraph = new Paragraph();
    }

    @Test
    public void constructors() {
        String string = "foo";
        Chunk content = new Chunk(string);
        Font font = new PDFFont();

        Paragraph p1 = new Paragraph(string, font);
        assertEquals(content, p1.getContent().get(0));
        assertEquals(font, p1.getFont());

        Paragraph p2 = new Paragraph(content, font);
        assertEquals(content, p2.getContent().get(0));
        assertEquals(font, p2.getFont());
    }

    @Test
    public void setLeading() {
        float input = 20;
        paragraph.setLeading(input);
        assertEquals(input, paragraph.getLeading());
    }

    @Test
    public void setLineHeight() {
        float input = 20;
        paragraph.setLineHeight(input);
        assertEquals(input, paragraph.getLineHeight());
    }

    @Test
    public void setFont() {
        Font input = new PDFFont();
        paragraph.setFont(input);
        assertEquals(input, paragraph.getFont());
    }

    @Test
    public void setLeftIndent() {
        float input = 20;
        paragraph.setLeftIndent(input);
        assertEquals(input, paragraph.getLeftIndent());
    }

    @Test
    public void setRightIndent() {
        float input = 20;
        paragraph.setRightIndent(input);
        assertEquals(input, paragraph.getRightIndent());
    }

    @Test
    public void setFirstLineIndent() {
        float input = 20;
        paragraph.setFirstLineIndent(input);
        assertEquals(input, paragraph.getFirstLineIndent());
    }

    @Test
    public void setSpacingBefore() {
        float input = 20;
        paragraph.setSpacingBefore(input);
        assertEquals(input, paragraph.getSpacingBefore());
    }

    @Test
    public void setSpacingAfter() {
        float input = 20;
        paragraph.setSpacingAfter(input);
        assertEquals(input, paragraph.getSpacingAfter());
    }

    @Test
    public void setKeepTogether() {
        paragraph.setKeepTogether(true);
        assertTrue(paragraph.isKeepTogether());
        paragraph.setKeepTogether(false);
        assertFalse(paragraph.isKeepTogether());
    }

    @Test
    public void setAlignment() {
        assertEquals(HorizontalAlignment.LEFT, paragraph.getAlignment());
        HorizontalAlignment input = HorizontalAlignment.JUSTIFIED;
        paragraph.setAlignment(input);
        assertEquals(input, paragraph.getAlignment());
    }

    @Test
    public void addContentChunk() {
        Chunk input = new Chunk();
        paragraph.add(input);
        List<TextContent> actual = paragraph.getContent();
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(input, actual.get(0));
    }

    @Test
    public void addContentPhrase() {
        Phrase input = new Phrase();
        paragraph.add(input);
        List<TextContent> actual = paragraph.getContent();
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(input, actual.get(0));
    }

    @Test
    public void addContentParagraph() {
        assertThrows(IllegalArgumentException.class, () -> paragraph.add(new Paragraph()));
    }

    @Test
    public void addContentNull() {
        paragraph.add((TextContent) null);
        List<TextContent> actual = paragraph.getContent();
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    public void addString() {
        String input = "foo";
        paragraph.add(input);
        List<TextContent> actual = paragraph.getContent();
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(new Chunk(input), actual.get(0));
    }

    @Test
    public void addStringNull() {
        paragraph.add((String) null);
        List<TextContent> actual = paragraph.getContent();
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    public void testEqualsHashCode() {
        Paragraph paragraph2 = new Paragraph();

        assertEquals(paragraph, paragraph);
        assertEquals(paragraph.hashCode(), paragraph.hashCode());
        assertEquals(paragraph2, paragraph);
        assertEquals(paragraph2.hashCode(), paragraph.hashCode());

        assertNotEquals(paragraph, new Phrase(""));
        assertNotEquals(paragraph, null);
    }
}