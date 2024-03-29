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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParagraphTest {

    private Paragraph paragraph;

    @BeforeEach
    void setup() {
        paragraph = new Paragraph();
    }

    @Test
    void constructors() {
        String string = "foo";
        Chunk content = new Chunk(string);
        Font font = new PDFFont();

        Paragraph p1 = new Paragraph(string, font);
        assertEquals(content, p1.getContent().get(0));
        assertEquals(font, p1.getFont());

        Paragraph p2 = new Paragraph(content, font);
        assertEquals(content, p2.getContent().get(0));
        assertEquals(font, p2.getFont());

        Paragraph copy = new Paragraph(paragraph, paragraph.getContent());
        assertEquals(paragraph, copy);
    }

    @Test
    void setLeading() {
        float input = 20;
        paragraph.setLeading(input);
        assertEquals(input, paragraph.getLeading());
    }

    @Test
    void setLineHeight() {
        float input = 20;
        paragraph.setLineHeight(input);
        assertEquals(input, paragraph.getLineHeight());
    }

    @Test
    void setFont() {
        Font input = new PDFFont();
        paragraph.setFont(input);
        assertEquals(input, paragraph.getFont());
    }

    @Test
    void setLeftIndent() {
        float input = 20;
        paragraph.setLeftIndent(input);
        assertEquals(input, paragraph.getLeftIndent());
    }

    @Test
    void setRightIndent() {
        float input = 20;
        paragraph.setRightIndent(input);
        assertEquals(input, paragraph.getRightIndent());
    }

    @Test
    void setFirstLineIndent() {
        float input = 20;
        paragraph.setFirstLineIndent(input);
        assertEquals(input, paragraph.getFirstLineIndent());
    }

    @Test
    void setSpacingBefore() {
        float input = 20;
        paragraph.setSpacingBefore(input);
        assertEquals(input, paragraph.getSpacingBefore());
    }

    @Test
    void setSpacingAfter() {
        float input = 20;
        paragraph.setSpacingAfter(input);
        assertEquals(input, paragraph.getSpacingAfter());
    }

    @Test
    void setKeepTogether() {
        paragraph.setKeepTogether(true);
        assertTrue(paragraph.isKeepTogether());
        paragraph.setKeepTogether(false);
        assertFalse(paragraph.isKeepTogether());
    }

    @Test
    void setHyphenate() {
        assertTrue(paragraph.isHyphenate());
        paragraph.setHyphenate(false);
        assertFalse(paragraph.isHyphenate());
    }

    @Test
    void setAlignment() {
        assertEquals(HorizontalAlignment.LEFT, paragraph.getAlignment());
        HorizontalAlignment input = HorizontalAlignment.JUSTIFIED;
        paragraph.setAlignment(input);
        assertEquals(input, paragraph.getAlignment());
    }

    @Test
    void addContentChunk() {
        Chunk input = new Chunk();
        paragraph.add(input);
        List<TextContent> actual = paragraph.getContent();
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(input, actual.get(0));
    }

    @Test
    void addContentPhrase() {
        Phrase input = new Phrase();
        paragraph.add(input);
        List<TextContent> actual = paragraph.getContent();
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(input, actual.get(0));
    }

    @Test
    void addContentParagraph() {
        TextContent content = new Paragraph();
        assertThrows(IllegalArgumentException.class, () -> paragraph.add(content));
    }

    @Test
    void addContentNull() {
        paragraph.add((TextContent) null);
        List<TextContent> actual = paragraph.getContent();
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    void addString() {
        String input = "foo";
        paragraph.add(input);
        List<TextContent> actual = paragraph.getContent();
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(new Chunk(input), actual.get(0));
    }

    @Test
    void addStringNull() {
        paragraph.add((String) null);
        List<TextContent> actual = paragraph.getContent();
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    void testEqualsHashCode() {
        Paragraph paragraph2 = new Paragraph(paragraph, paragraph.getContent());

        assertEquals(paragraph, paragraph);
        assertEquals(paragraph.hashCode(), paragraph.hashCode());
        assertEquals(paragraph2, paragraph);
        assertEquals(paragraph2.hashCode(), paragraph.hashCode());

        assertNotEquals(paragraph, new Phrase(""));
        assertNotEquals(paragraph, null);

        Paragraph p4 = new Paragraph(paragraph, paragraph.getContent());
        p4.setLeading(10);
        assertNotEquals(paragraph, p4);

        Paragraph p5 = new Paragraph(paragraph, paragraph.getContent());
        p5.setLineHeight(10);
        assertNotEquals(paragraph, p5);

        Paragraph p6 = new Paragraph(paragraph, paragraph.getContent());
        p6.setLeftIndent(10);
        assertNotEquals(paragraph, p6);

        Paragraph p7 = new Paragraph(paragraph, paragraph.getContent());
        p7.setRightIndent(10);
        assertNotEquals(paragraph, p7);

        Paragraph p8 = new Paragraph(paragraph, paragraph.getContent());
        p8.setFirstLineIndent(10);
        assertNotEquals(paragraph, p8);

        Paragraph p9 = new Paragraph(paragraph, paragraph.getContent());
        p9.setSpacingBefore(10);
        assertNotEquals(paragraph, p9);

        Paragraph p10 = new Paragraph(paragraph, paragraph.getContent());
        p10.setSpacingAfter(10);
        assertNotEquals(paragraph, p10);

        Paragraph p11 = new Paragraph(paragraph, paragraph.getContent());
        p11.setKeepTogether(true);
        assertNotEquals(paragraph, p11);

        Paragraph p12 = new Paragraph(paragraph, paragraph.getContent());
        p12.add("foo");
        assertNotEquals(paragraph, p12);

        Paragraph p13 = new Paragraph(paragraph, paragraph.getContent());
        p13.setFont(new PDFFont(PDFFont.FontName.COURIER));
        assertNotEquals(paragraph, p13);

        Paragraph p14 = new Paragraph(paragraph, paragraph.getContent());
        p14.setAlignment(HorizontalAlignment.JUSTIFIED);
        assertNotEquals(paragraph, p14);

        Paragraph p15 = new Paragraph(paragraph, paragraph.getContent());
        p15.setHyphenate(false);
        assertNotEquals(paragraph, p15);
    }
}