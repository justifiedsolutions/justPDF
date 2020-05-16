/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.content.*;
import com.justifiedsolutions.justpdf.api.font.PDFFont;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Lays out {@link TextContent} into a series of {@link ContentLine}s.
 */
class TextContentLayout implements ContentLayout {
    private final float lineWidth;
    private final float spacingBefore;
    private final float spacingAfter;
    private Paragraph paragraph;
    private float lineStart = 0;
    private boolean firstLine = true;

    /**
     * Creates a new ParagraphLayout. This should only be called by {@link TextContentLayoutFactory#getContentLayout(Content)}.
     *
     * @param lineWidth   the width of the lines to be created by this {@link ContentLayout}
     * @param textContent the {@link TextContent} to layout
     */
    TextContentLayout(float lineWidth, TextContent textContent) {
        this.paragraph = TextContentUtility.getParagraph(Objects.requireNonNull(textContent));
        this.lineWidth = lineWidth;

        this.spacingBefore = this.paragraph.getSpacingBefore();
        this.spacingAfter = this.paragraph.getSpacingAfter();

        initializeFonts();
    }

    @Override
    public float getMinimumHeight() {
        if (paragraph == null) {
            return -1;
        }

        float height = 0;
        TextContentLayout layout = new TextContentLayout(this.lineWidth, this.paragraph);
        ContentLine line = layout.getNextLine(0);
        if (this.paragraph.isKeepTogether()) {
            while (line != null) {
                height += line.getHeight();
                line = layout.getNextLine(0);
            }
        } else {
            height = line.getHeight();
        }
        return height;
    }

    @Override
    public ContentLine getNextLine(float verticalPosition) {
        if (paragraph == null) {
            return null;
        }

        List<TextContent> originalContent = new ArrayList<>(paragraph.getContent());

        float leftIndent = paragraph.getLeftIndent();
        if (firstLine) {
            leftIndent += paragraph.getFirstLineIndent();
        }

        TextLine line = new TextLine(lineWidth, leftIndent, paragraph.getRightIndent());
        line.setAlignment(paragraph.getAlignment());
        line.setLeading(paragraph.getLeading());
        line.setLineHeight(paragraph.getLineHeight());
        line.setPreviousLineStart(lineStart);

        for (TextContent content : paragraph.getContent()) {
            originalContent.remove(content);
            if (content instanceof Chunk) {
                Chunk chunk = (Chunk) content;
                Chunk remainder = line.append(chunk);
                if (remainder != null) {
                    originalContent.add(0, remainder);
                    paragraph = new Paragraph(paragraph, originalContent);
                    break;
                }
            } else if (content instanceof Phrase) {
                Phrase phrase = (Phrase) content;
                List<Chunk> originalChunks = new ArrayList<>(phrase.getChunks());
                for (Chunk chunk : phrase.getChunks()) {
                    originalChunks.remove(chunk);
                    Chunk remainder = line.append(chunk);
                    if (remainder != null) {
                        originalChunks.add(0, remainder);
                        phrase = new Phrase(phrase, originalChunks);
                        originalContent.add(0, phrase);
                        paragraph = new Paragraph(paragraph, originalContent);
                        break;
                    }
                }
            }
        }

        if (originalContent.isEmpty()) {
            paragraph = null;
        }

        firstLine = false;
        lineStart = line.getLineStart();
        return line;
    }

    @Override
    public Content getRemainingContent() {
        return paragraph;
    }

    @Override
    public float getSpacingBefore() {
        return spacingBefore;
    }

    @Override
    public float getSpacingAfter() {
        return spacingAfter;
    }

    private void initializeFonts() {
        if (paragraph.getFont() == null) {
            paragraph.setFont(new PDFFont());
        }

        for (Content content : paragraph.getContent()) {
            if (content instanceof Chunk) {
                Chunk chunk = (Chunk) content;
                if (chunk.getFont() == null) {
                    chunk.setFont(paragraph.getFont());
                }
            } else if (content instanceof Phrase) {
                Phrase phrase = (Phrase) content;
                if (phrase.getFont() == null) {
                    phrase.setFont(paragraph.getFont());
                }
                for (Chunk chunk : phrase.getChunks()) {
                    if (chunk.getFont() == null) {
                        chunk.setFont(phrase.getFont());
                    }
                }
            }
        }
    }
}
