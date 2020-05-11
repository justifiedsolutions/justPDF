/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.layout;

import com.justifiedsolutions.jspdf.api.content.Chunk;
import com.justifiedsolutions.jspdf.api.content.Content;
import com.justifiedsolutions.jspdf.api.content.Phrase;
import com.justifiedsolutions.jspdf.api.font.PDFFont;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Lays out a {@link Phrase} into a series of {@link ContentLine}s.
 */
class PhraseLayout implements ContentLayout {
    private final float lineWidth;
    private Phrase phrase;

    /**
     * Creates a new PhraseLayout. This should only be called by {@link PhraseLayoutFactory#getContentLayout(Content)}.
     *
     * @param lineWidth the width of the lines to be created by this {@link ContentLayout}
     * @param phrase    the Phrase to layout
     */
    PhraseLayout(float lineWidth, Phrase phrase) {
        this.lineWidth = lineWidth;
        this.phrase = Objects.requireNonNull(phrase);

        initializeFonts();
    }

    @Override
    public float getMinimumHeight() {
        if (phrase == null) {
            return -1;
        }

        PhraseLayout layout = new PhraseLayout(this.lineWidth, this.phrase);
        return layout.getNextLine(0).getHeight();
    }

    @Override
    public ContentLine getNextLine(float verticalPosition) {
        if (phrase == null) {
            return null;
        }

        List<Chunk> originalChunks = new ArrayList<>(phrase.getChunks());

        TextLine line = new TextLine(lineWidth);
        line.setLeading(phrase.getLeading());
        for (Chunk chunk : phrase.getChunks()) {
            originalChunks.remove(chunk);
            Chunk remainder = line.append(chunk);
            if (remainder != null) {
                originalChunks.add(0, remainder);
                phrase = new Phrase(phrase, originalChunks);
                break;
            }
        }

        if (originalChunks.isEmpty()) {
            phrase = null;
        }

        return line;
    }

    @Override
    public Content getRemainingContent() {
        return phrase;
    }

    @Override
    public float getSpacingBefore() {
        return 0;
    }

    @Override
    public float getSpacingAfter() {
        return 0;
    }

    private void initializeFonts() {
        if (phrase.getFont() == null) {
            phrase.setFont(new PDFFont());
        }

        for (Chunk chunk : phrase.getChunks()) {
            if (chunk.getFont() == null) {
                chunk.setFont(phrase.getFont());
            }
        }
    }
}
