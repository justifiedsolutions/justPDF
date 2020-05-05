/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.api.content;

import com.justifiedsolutions.jspdf.api.font.Font;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A Phrase is a series of {@link Chunk}s. The Phrase has an associated {@link Font} and any Chunks added to the Phrase
 * inherit the Font of the Phrase unless they specify a Font themselves. A Phrase also specifies a leading value.
 *
 * @see <a href="https://techterms.com/definition/leading">Leading</a>
 */
public class Phrase implements TextContent {

    private final List<Chunk> chunks = new ArrayList<>();
    private float leading = Float.NaN;
    private Font font;

    /**
     * Creates an empty Phrase.
     */
    public Phrase() {
        // do nothing
    }

    /**
     * Creates a Phrase with the specified chunk.
     *
     * @param chunk the chunk
     */
    public Phrase(Chunk chunk) {
        this(chunk, null);
    }

    /**
     * Creates a Phrase with the specified chunk and font. The font is associated with the Phrase.
     *
     * @param chunk the chunk
     * @param font  the font
     */
    public Phrase(Chunk chunk, Font font) {
        add(chunk);
        setFont(font);
    }

    /**
     * Creates a Phrase with the specified text. The text is turned into a Chunk.
     *
     * @param text the text
     */
    public Phrase(String text) {
        this(text, null);
    }

    /**
     * Creates a Phrase with the specified text and font. The text is turned into a Chunk and the font is associated
     * with the Phrase.
     *
     * @param text the text
     * @param font the font
     */
    public Phrase(String text, Font font) {
        add(text);
        setFont(font);
    }

    /**
     * Get the leading for the Phrase. The default value is {@link Float#NaN} which dynamically calculates the leading.
     *
     * @return the leading
     */
    public float getLeading() {
        return leading;
    }

    /**
     * Set the leading for the Phrase.
     *
     * @param leading the leading
     */
    public void setLeading(float leading) {
        this.leading = leading;
    }

    @Override
    public Font getFont() {
        return font;
    }

    @Override
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * Get an {@linkplain Collections#unmodifiableList(List) unmodifiable list} of {@link Chunk}s for the Phrase.
     *
     * @return the list of Chunks
     */
    public List<Chunk> getChunks() {
        return Collections.unmodifiableList(chunks);
    }

    /**
     * Adds a {@link Chunk} to the Phrase.
     *
     * @param chunk the chunk to add
     */
    public void add(Chunk chunk) {
        if (chunk != null) {
            chunks.add(chunk);
        }
    }

    /**
     * Adds the text to the Phrase. This is a shortcut for creating a {@link Chunk} then adding it to the Phrase.
     * Passing <code>null</code> to this method is silently ignored.
     *
     * @param text the text to add
     */
    public void add(String text) {
        if (text != null) {
            add(new Chunk(text));
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(chunks, leading, font);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Phrase phrase = (Phrase) o;
        return Float.compare(phrase.leading, leading) == 0 &&
                chunks.equals(phrase.chunks) &&
                Objects.equals(font, phrase.font);
    }
}
