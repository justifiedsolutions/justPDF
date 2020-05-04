/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.api.content;


import com.justifiedsolutions.jspdf.api.Document;
import com.justifiedsolutions.jspdf.api.font.Font;

import java.util.Objects;

/**
 * Smallest part of text that can be added to a {@link Document}.
 */
public class Chunk implements TextContent {

    public static final Chunk LINE_BREAK = new Chunk("\n");
    public static final Chunk PAGE_BREAK = new Chunk(true);

    private String text;
    private Font font;
    private boolean pageBreak = false;

    /**
     * Creates an empty Chunk.
     */
    public Chunk() {
        this(null, null);
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

    private Chunk(boolean pageBreak) {
        this.pageBreak = pageBreak;
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

    /**
     * Gets the font associated with this Chunk.
     *
     * @return the font
     */
    public Font getFont() {
        return this.font;
    }

    /**
     * Sets the font associated with this Chunk
     *
     * @param font the new font
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * Specifies if this Chunk represents a page break.
     *
     * @return true if it is a page break
     */
    public boolean isPageBreak() {
        return pageBreak;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, font, pageBreak);
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
        return pageBreak == chunk.pageBreak &&
                Objects.equals(text, chunk.text) &&
                Objects.equals(font, chunk.font);
    }
}
