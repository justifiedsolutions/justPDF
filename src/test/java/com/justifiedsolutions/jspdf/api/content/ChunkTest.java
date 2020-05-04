/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.api.content;

import com.justifiedsolutions.jspdf.api.font.Font;
import com.justifiedsolutions.jspdf.api.font.PDFFont;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChunkTest {

    private Chunk chunk;

    @BeforeEach
    public void setup() {
        chunk = new Chunk();
    }

    @Test
    public void append() {
        String input = "foo";
        chunk.append(input);
        assertEquals(input, chunk.getText());
        chunk.append(null);
        assertEquals(input, chunk.getText());
    }

    @Test
    public void setText() {
        String input = "foo";
        chunk.setText(input);
        assertEquals(input, chunk.getText());
        input = "bar";
        chunk.setText(input);
        assertEquals(input, chunk.getText());
    }

    @Test
    public void setFont() {
        Font font = new PDFFont();
        chunk.setFont(font);
        assertEquals(font, chunk.getFont());
    }

    @Test
    public void isPageBreak() {
        assertTrue(Chunk.PAGE_BREAK.isPageBreak());
        assertFalse(Chunk.LINE_BREAK.isPageBreak());
        assertFalse(chunk.isPageBreak());
    }

    @Test
    public void equalsHashCode() {
        Chunk chunk2 = new Chunk();

        assertEquals(chunk, chunk);
        assertEquals(chunk.hashCode(), chunk.hashCode());
        assertEquals(chunk2, chunk);
        assertEquals(chunk2.hashCode(), chunk.hashCode());
        chunk2.append("foo");
        assertNotEquals(chunk2, chunk);
        chunk2.setText("");
        chunk2.setFont(new PDFFont());
        assertNotEquals(chunk2, chunk);

        assertNotEquals(Chunk.PAGE_BREAK, chunk);
        assertNotEquals(chunk, new Phrase(""));
        assertNotEquals(chunk, null);
    }
}