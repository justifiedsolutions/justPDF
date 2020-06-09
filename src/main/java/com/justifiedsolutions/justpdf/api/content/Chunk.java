/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api.content;


import com.justifiedsolutions.justpdf.api.Document;
import com.justifiedsolutions.justpdf.api.font.Font;

import java.util.Objects;

/**
 * Smallest part of text that can be added to a {@link Document}.
 */
public final class Chunk implements TextContent {

    /**
     * Adds a line break into a piece of content.
     */
    public static final Chunk LINE_BREAK = new Chunk("\n");
    private String text;
    private Font font;
    private boolean hyphenate = true;

    /**
     * Creates an empty Chunk.
     */
    public Chunk() {
        this("", null);
    }

    /**
     * Creates a Chunk with the specified text.
     *
     * @param text the text
     */
    public Chunk(String text) {
        this(text, null);
    }

    /**
     * Creates a Chunk with the specified text and font.
     *
     * @param text the text
     * @param font the font
     */
    public Chunk(String text, Font font) {
        setText(text);
        setFont(font);
    }

    /**
     * Append the specified text to existing text in this Chunk.
     *
     * @param text the text to append
     */
    public void append(String text) {
        if (text != null) {
            this.text += text;
        }
    }

    /**
     * The text in the Chunk
     *
     * @return the text
     */
    public String getText() {
        return this.text;
    }

    /**
     * Sets the text of the Chunk.
     *
     * @param text the new text for the Chunk
     */
    public void setText(String text) {
        this.text = (text != null) ? text : "";
    }

    @Override
    public Font getFont() {
        return this.font;
    }

    @Override
    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public boolean isHyphenate() {
        return hyphenate;
    }

    @Override
    public void setHyphenate(boolean hyphenate) {
        this.hyphenate = hyphenate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, font, hyphenate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Chunk chunk = (Chunk) o;
        return hyphenate == chunk.hyphenate &&
                text.equals(chunk.text) &&
                Objects.equals(font, chunk.font);
    }

    @Override
    public String toString() {
        return text;
    }
}
