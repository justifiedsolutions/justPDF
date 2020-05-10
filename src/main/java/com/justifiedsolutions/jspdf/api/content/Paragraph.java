/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.api.content;

import com.justifiedsolutions.jspdf.api.HorizontalAlignment;
import com.justifiedsolutions.jspdf.api.font.Font;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A Paragraph is a series of {@link Chunk}s and {@link Phrase}s. The Paragraph has an associated {@link Font} and any
 * Chunks or Phrases added to the Paragraph inherit the Font of the Paragraph unless they specify a Font themselves.
 */
public class Paragraph implements TextContent {

    private final List<Content> content = new ArrayList<>();
    private float leading = 0f;
    private float lineHeight = 0f;
    private Font font;
    private float leftIndent = 0f;
    private float rightIndent = 0f;
    private float firstLineIndent = 0f;
    private float spacingBefore = 0f;
    private float spacingAfter = 0f;
    private boolean keepTogether = false;
    private HorizontalAlignment alignment = HorizontalAlignment.LEFT;

    /**
     * Creates an empty Paragraph with the default font.
     */
    public Paragraph() {
    }

    /**
     * Creates a new Paragraph adding the specified content using the default font.
     *
     * @param content the content
     * @throws IllegalArgumentException if content is not a Chunk or Phrase
     */
    public Paragraph(Content content) {
        this(content, null);
    }

    /**
     * Creates a new Paragraph adding the specified text using the default font.
     *
     * @param text the content
     */
    public Paragraph(String text) {
        this(text, null);
    }

    /**
     * Creates a new Paragraph adding the specified content and font.
     *
     * @param content the content
     * @param font    the font
     * @throws IllegalArgumentException if content is not a Chunk or Phrase
     */
    public Paragraph(Content content, Font font) {
        add(content);
        setFont(font);
    }

    /**
     * Creates a new Paragraph adding the specified text and font.
     *
     * @param text the content
     * @param font the font
     */
    public Paragraph(String text, Font font) {
        add(text);
        setFont(font);
    }

    /**
     * Copy constructor that copies all fields except for the content. It fills the content with the specified content.
     *
     * @param paragraph the Paragraph to copy
     * @param content   the content to copy
     */
    public Paragraph(Paragraph paragraph, List<Content> content) {
        this.leading = paragraph.leading;
        this.lineHeight = paragraph.lineHeight;
        this.font = paragraph.font;
        this.leftIndent = paragraph.leftIndent;
        this.rightIndent = paragraph.rightIndent;
        this.firstLineIndent = paragraph.firstLineIndent;
        this.spacingBefore = paragraph.spacingBefore;
        this.spacingAfter = paragraph.spacingAfter;
        this.keepTogether = paragraph.keepTogether;
        this.alignment = paragraph.alignment;
        this.content.addAll(content);
    }

    /**
     * Get the leading for the Paragraph. The default value is <code>0.0</code>.
     *
     * @return the leading
     */
    public float getLeading() {
        return leading;
    }

    /**
     * Set the leading for the Paragraph. You can have either leading or line height but not both. Setting one will set
     * the other to <code>0.0</code>.
     *
     * @param leading the leading
     */
    public void setLeading(float leading) {
        this.leading = leading;
        this.lineHeight = 0;
    }

    /**
     * Gets the line height for the Paragraph. This multiplied by the maximum height of the largest font in the
     * Paragraph. The default value is <code>0.0</code>.
     *
     * @return the line height
     */
    public float getLineHeight() {
        return lineHeight;
    }

    /**
     * Sets the line height for the Paragraph. This multiplied by the maximum height of the largest font in the
     * Paragraph. You can have either leading or line height but not both. Setting one will set the other to
     * <code>0.0</code>.
     *
     * @param lineHeight the line height
     */
    public void setLineHeight(float lineHeight) {
        this.lineHeight = lineHeight;
        this.leading = 0;
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
     * Gets the left indent of the Paragraph. The default is <code>0</code>.
     *
     * @return left indent
     */
    public float getLeftIndent() {
        return leftIndent;
    }

    /**
     * Sets the left indent of the Paragraph.
     *
     * @param leftIndent left indent
     */
    public void setLeftIndent(float leftIndent) {
        this.leftIndent = leftIndent;
    }

    /**
     * Gets the right indent of the Paragraph. The default is <code>0</code>    .
     *
     * @return right indent
     */
    public float getRightIndent() {
        return rightIndent;
    }

    /**
     * Sets the right indent of the Paragraph.
     *
     * @param rightIndent right indent
     */
    public void setRightIndent(float rightIndent) {
        this.rightIndent = rightIndent;
    }

    /**
     * Specifies the amount of the first line indentation from the left indent. The default is <code>0</code>.
     *
     * @return the amount of indentation
     */
    public float getFirstLineIndent() {
        return firstLineIndent;
    }

    /**
     * Specifies the amount of the first line indentation from the left indent.
     *
     * @param firstLineIndent the amount of indentation
     */
    public void setFirstLineIndent(float firstLineIndent) {
        this.firstLineIndent = firstLineIndent;
    }

    /**
     * Gets the amount of vertical spacing before the Paragraph. The default is <code>0</code>.
     *
     * @return the amount of vertical spacing before the Paragraph
     */
    public float getSpacingBefore() {
        return spacingBefore;
    }

    /**
     * Sets the amount of vertical spacing before the Paragraph.
     *
     * @param spacingBefore vertical spacing before
     */
    public void setSpacingBefore(float spacingBefore) {
        this.spacingBefore = spacingBefore;
    }

    /**
     * Gets the amount of vertical spacing after the Paragraph. The default is <code>0</code>.
     *
     * @return the amount of vertical spacing after the Paragraph
     */
    public float getSpacingAfter() {
        return spacingAfter;
    }

    /**
     * Sets the amount of vertical spacing after the Paragraph.
     *
     * @param spacingAfter vertical spacing after
     */
    public void setSpacingAfter(float spacingAfter) {
        this.spacingAfter = spacingAfter;
    }

    /**
     * Specifies if the Paragraph should be broken between pages. The default is
     * <code>false</code>.
     *
     * @return true if the paragraph should be kept together
     */
    public boolean isKeepTogether() {
        return keepTogether;
    }

    /**
     * Specifies if the Paragraph should be broken between pages.
     *
     * @param keepTogether true if the paragraph should be kept together
     */
    public void setKeepTogether(boolean keepTogether) {
        this.keepTogether = keepTogether;
    }

    /**
     * Gets the {@link HorizontalAlignment} of the Paragraph. Default is {@link HorizontalAlignment#LEFT}.
     *
     * @return the alignment of the paragraph or <code>null</code> if it isn't specified
     */
    public HorizontalAlignment getAlignment() {
        return alignment;
    }

    /**
     * Sets the {@link HorizontalAlignment} of the Paragraph.
     *
     * @param alignment the alignment or <code>null</code> if it isn't specified
     */
    public void setAlignment(HorizontalAlignment alignment) {
        this.alignment = alignment;
    }

    /**
     * Get an {@linkplain Collections#unmodifiableList(List) unmodifiable list} of {@link Content}s for the Paragraph.
     *
     * @return the list of Content
     */
    public List<Content> getContent() {
        return Collections.unmodifiableList(content);
    }

    /**
     * Adds the specified content to the Paragraph.
     *
     * @param content the content
     * @throws IllegalArgumentException if content is not a Chunk or Phrase
     */
    public void add(Content content) {
        if (content == null) {
            return;
        }
        if ((content instanceof Chunk) || (content instanceof Phrase)) {
            this.content.add(content);
        } else {
            throw new IllegalArgumentException("Invalid content type: " + content.getClass());
        }
    }

    /**
     * Adds the specified text to the Paragraph as a Chunk.
     *
     * @param text the text
     */
    public void add(String text) {
        if (text != null) {
            add(new Chunk(text));
        }
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(content, leading, lineHeight, font, leftIndent, rightIndent, firstLineIndent,
                        spacingBefore, spacingAfter, keepTogether, alignment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Paragraph paragraph = (Paragraph) o;
        return Float.compare(paragraph.leading, leading) == 0 &&
                Float.compare(paragraph.lineHeight, lineHeight) == 0 &&
                Float.compare(paragraph.leftIndent, leftIndent) == 0 &&
                Float.compare(paragraph.rightIndent, rightIndent) == 0 &&
                Float.compare(paragraph.firstLineIndent, firstLineIndent) == 0 &&
                Float.compare(paragraph.spacingBefore, spacingBefore) == 0 &&
                Float.compare(paragraph.spacingAfter, spacingAfter) == 0 &&
                keepTogether == paragraph.keepTogether &&
                content.equals(paragraph.content) &&
                Objects.equals(font, paragraph.font) &&
                alignment == paragraph.alignment;
    }
}
