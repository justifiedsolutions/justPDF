/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.layout;

import com.justifiedsolutions.jspdf.api.content.Chunk;
import com.justifiedsolutions.jspdf.api.content.Content;
import com.justifiedsolutions.jspdf.api.content.Phrase;
import com.justifiedsolutions.jspdf.api.font.PDFFont;

import java.util.*;

class PhraseLayout implements ContentLayout {
    private final float lineWidth;
    private Phrase phrase;

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

        float leading = phrase.getLeading();
        leading = Math.max(leading, PDFFontWrapper.getInstance(phrase.getFont()).getMinimumLeading());
        for (Chunk chunk : phrase.getChunks()) {
            leading = Math.max(leading, PDFFontWrapper.getInstance(chunk.getFont()).getMinimumLeading());
        }
        return leading;
    }

    @Override
    public ContentLine getNextLine() {
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
                updatePhrase(originalChunks);
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

    private void updatePhrase(List<Chunk> remainingChunks) {
        Phrase remainingPhrase = new Phrase();
        remainingPhrase.setFont(phrase.getFont());
        remainingPhrase.setLeading(phrase.getLeading());
        for (Chunk oChunk : remainingChunks) {
            remainingPhrase.add(oChunk);
        }
        phrase = remainingPhrase;
    }
}
