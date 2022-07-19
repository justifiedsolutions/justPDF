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
    public void setHyphenate() {
        chunk.setHyphenate(false);
        assertFalse(chunk.isHyphenate());
    }

    @Test
    @SuppressWarnings("unlikely-arg-type")
    public void equalsHashCode() {
        Chunk chunk2 = new Chunk();

        assertTrue(chunk.equals(chunk));
        assertFalse(chunk.equals(null));
        assertFalse(chunk.equals(Boolean.TRUE));

        assertTrue(chunk.equals(chunk2));
        assertEquals(chunk.hashCode(), chunk2.hashCode());

        Chunk chunk3 = new Chunk("foo");
        assertFalse(chunk.equals(chunk3));

        Chunk chunk4 = new Chunk();
        chunk4.setFont(new PDFFont(PDFFont.FontName.COURIER));
        assertFalse(chunk.equals(chunk4));

        Chunk chunk5 = new Chunk();
        chunk5.setHyphenate(false);
        assertFalse(chunk.equals(chunk5));
    }
}