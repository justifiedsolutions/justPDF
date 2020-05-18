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

        assertNotEquals(chunk, new Phrase(""));
        assertNotEquals(chunk, null);
    }
}