/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.HorizontalAlignment;
import com.justifiedsolutions.justpdf.api.content.Chunk;
import com.justifiedsolutions.justpdf.pdf.contents.*;
import com.justifiedsolutions.justpdf.pdf.object.PDFReal;
import com.justifiedsolutions.justpdf.pdf.object.PDFString;

import java.util.ArrayList;
import java.util.List;

/**
 * Lays out a line of text.
 */
final class TextLine implements ContentLine {

    private final List<GraphicsOperator> operators = new ArrayList<>();

    private final float lineWidth;
    private final float leftIndent;
    private float remainingWidth;
    private float lineStart;
    private float previousLineStart;
    private float leading;
    private float lineHeight;
    private HorizontalAlignment alignment = HorizontalAlignment.LEFT;
    private int numSpaces;
    private int numChars;

    /**
     * Creates a new TextLine of the specified width and indentations from the margin.
     *
     * @param lineWidth   the width of the line
     * @param leftIndent  the amount of left indent
     * @param rightIndent the amount of right indent
     */
    TextLine(float lineWidth, float leftIndent, float rightIndent) {
        this.lineWidth = (lineWidth - (leftIndent + rightIndent));
        this.remainingWidth = this.lineWidth;
        this.leftIndent = leftIndent;
    }

    @Override
    public float getHeight() {
        return leading;
    }

    @Override
    public List<GraphicsOperator> getOperators() {
        List<GraphicsOperator> result = new ArrayList<>();
        result.add(new SetLeading(new PDFReal(leading)));
        result.add(new MoveToNextLine());
        result.addAll(getAlignmentOperators());
        result.addAll(operators);
        return result;
    }

    /**
     * Sets the leading to the maximum of the current leading and the specified leading.
     *
     * @param leading the possible new leading
     */
    void setLeading(float leading) {
        this.leading = Math.max(this.leading, leading);
    }

    /**
     * Sets the lineHeight multiplier. This will be multiplied by the font height to determine the leading for the
     * line.
     *
     * @param lineHeight the multiplier
     */
    void setLineHeight(float lineHeight) {
        this.lineHeight = lineHeight;
    }

    /**
     * Sets the alignment of the line.
     *
     * @param alignment the alignment
     */
    void setAlignment(HorizontalAlignment alignment) {
        this.alignment = alignment;
    }

    /**
     * Gets the start of this line as indentation from {@code 0}.
     *
     * @return the start of the line
     */
    float getLineStart() {
        return lineStart;
    }

    /**
     * Gets the start of the previous line as indentation from {@code 0}.
     *
     * @param previousLineStart the start of the previous line
     */
    void setPreviousLineStart(float previousLineStart) {
        this.previousLineStart = previousLineStart;
    }

    /**
     * Attempts to append the specified {@link Chunk} to the line. Any part of the Chunk that couldn't fit is returned.
     * If the entire Chunk could fit, then {@code null} is returned.
     *
     * @param chunk the Chunk to append
     * @return the remainder or null if it all fit
     */
    Chunk append(Chunk chunk) {
        PDFFontWrapper wrapper = PDFFontWrapper.getInstance(chunk.getFont());
        setLeading(wrapper.getMinimumLeading());
        setLeading(lineHeight * wrapper.getSize().getValue());

        // no room, it's all remainder
        if (remainingWidth == 0) {
            return chunk;
        }

        // end the line and return any remainder
        if (chunk.getText().startsWith("\n")) {
            remainingWidth = 0;
            if (chunk.getText().length() == 1) {
                return null;
            }
            return new Chunk(chunk.getText().substring(1), chunk.getFont());
        }

        // ignore empty chunk
        if (chunk.getText().isEmpty()) {
            return null;
        }

        return doAppend(chunk, wrapper);
    }

    private Chunk doAppend(Chunk chunk, PDFFontWrapper wrapper) {
        boolean firstWord = (lineWidth == remainingWidth);

        String chunkText;
        if (firstWord) {
            chunkText = chunk.getText().stripLeading();
        } else {
            chunkText = chunk.getText();
        }
        Chunk alteredChunk = new Chunk(chunkText, chunk.getFont());
        alteredChunk.setHyphenate(chunk.isHyphenate());

        String[] lineSplit = chunkText.split("\\R", 2);
        if (lineSplit.length == 2) {
            chunkText = lineSplit[0];
        }

        String split = splitText(chunkText, wrapper, chunk.isHyphenate());
        Chunk remainder = getRemainingChunk(alteredChunk, split);

        if (firstWord && split.isEmpty() && remainder != null) {
            float textWidth = wrapper.getStringWidth(chunk.getText());
            throw new IllegalArgumentException(String.format("Unable to format document. Content does not fit width" +
                    ".%n(%s), text width = %f, line width = %f", chunk.getText(), textWidth, lineWidth));
        }

        if (!split.isEmpty()) {
            operators.add(wrapper.getOperator());
            operators.add(wrapper.getColorSpaceOperator());
            operators.add(new ShowText(new PDFString(split)));
        }

        return remainder;
    }

    private Chunk getRemainingChunk(Chunk chunk, String lineText) {
        String chunkText = chunk.getText();
        int length = lineText.length();
        if (lineText.endsWith("-")) {
            length--;
        }
        String text = chunkText.substring(length);
        if (text.startsWith("\n") || text.startsWith("-")) {
            text = text.substring(1);
        }
        Chunk result = null;
        if (!text.isEmpty()) {
            result = new Chunk(text, chunk.getFont());
            result.setHyphenate(chunk.isHyphenate());
        }
        return result;
    }

    private String splitText(String input, PDFFontWrapper wrapper, boolean doHyphenation) {
        TextSplitter splitter = new TextSplitter(remainingWidth);
        String value = splitter.split(input, wrapper, doHyphenation);
        float valueWidth = wrapper.getStringWidth(value);
        int valueSpaces = getNumberOfSpaces(value);
        numSpaces += valueSpaces;
        numChars += (value.length() - valueSpaces);
        remainingWidth -= valueWidth;
        calculateLineStart();
        return value;
    }

    private void calculateLineStart() {
        if (HorizontalAlignment.LEFT == alignment || HorizontalAlignment.JUSTIFIED == alignment) {
            lineStart = leftIndent;
        } else if (HorizontalAlignment.CENTER == alignment) {
            lineStart = (remainingWidth / 2f);
        } else if (HorizontalAlignment.RIGHT == alignment) {
            lineStart = remainingWidth;
        }
    }

    private List<GraphicsOperator> getAlignmentOperators() {
        List<GraphicsOperator> result = new ArrayList<>();
        float start = (lineStart - previousLineStart);
        if (start != 0) {
            result.add(new PositionText(new PDFReal(start), new PDFReal(0)));
        }
        if (HorizontalAlignment.JUSTIFIED == alignment) {
            if ((lineWidth * .2f) < remainingWidth) {
                result.add(new SetWordSpacing(new PDFReal(0)));
                result.add(new SetCharacterSpacing(new PDFReal(0)));
            } else {
                float wordSpacing = PDFReal.truncate(remainingWidth / numSpaces);
                float remainder = remainingWidth - (wordSpacing * numSpaces);
                float charSpacing = PDFReal.truncate(remainder / numChars);
                result.add(new SetWordSpacing(new PDFReal(wordSpacing)));
                result.add(new SetCharacterSpacing(new PDFReal(charSpacing)));
            }
        }
        return result;
    }

    private int getNumberOfSpaces(String value) {
        int result = 0;
        char[] chars = value.toCharArray();
        for (char c : chars) {
            if (Character.isSpaceChar(c)) {
                result++;
            }
        }
        return result;
    }
}