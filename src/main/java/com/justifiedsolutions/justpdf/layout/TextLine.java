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

import java.text.BreakIterator;
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
        chunkText = chunkText.replaceAll("\\r?\\n", "\n");

        List<String> split = splitText(chunkText, wrapper);

        if (firstWord && split.get(0).isEmpty() && split.size() == 2) {
            throw new IllegalArgumentException("Unable to format document. Content does not fit width.\n("
                    + chunk.getText() + "), width = " + lineWidth);
        }

        if (!split.get(0).isEmpty()) {
            operators.add(wrapper.getOperator());
            operators.add(wrapper.getColorSpaceOperator());
            operators.add(new ShowText(new PDFString(split.get(0))));
        } else {
            return chunk;
        }

        if (split.size() == 2) {
            return new Chunk(split.get(1), chunk.getFont());
        }
        return null;
    }

    private List<String> splitText(String input, PDFFontWrapper wrapper) {
        BreakIterator breakDetector = BreakIterator.getLineInstance();
        breakDetector.setText(input);

        int newlineIndex = input.indexOf('\n');
        if (newlineIndex < 0) {
            newlineIndex = Integer.MAX_VALUE;
        }

        boolean hyphenate = false;
        char[] chars = input.toCharArray();
        int boundary = breakDetector.next();
        while ((boundary != BreakIterator.DONE) && (boundary < newlineIndex)) {
            String value = new String(chars, 0, boundary);
            float valueWidth = wrapper.getStringWidth(value);
            if (valueWidth > remainingWidth) {
                int end = boundary;
                boundary = breakDetector.previous();
                String tmp = new String(chars, boundary, (end - boundary)).stripTrailing();
                int hyphenBoundary = hyphenate(tmp, wrapper, chars, boundary);
                if (hyphenBoundary > boundary) {
                    boundary = hyphenBoundary;
                    hyphenate = true;
                }
                break;
            }
            boundary = breakDetector.next();
        }

        String value;
        String remainder = null;
        if (boundary == BreakIterator.DONE) {
            value = input;
        } else {
            value = new String(chars, 0, boundary);
            value = value.stripTrailing();
            if (hyphenate) {
                value += "-";
            }
            remainder = new String(chars, boundary, chars.length - boundary);
        }
        float valueWidth = wrapper.getStringWidth(value);
        int valueSpaces = getNumberOfSpaces(value);
        numSpaces += valueSpaces;
        numChars += (value.length() - valueSpaces);
        remainingWidth -= valueWidth;
        calculateLineStart();

        List<String> result = new ArrayList<>();
        result.add(value);
        if (remainder != null) {
            result.add(remainder);
        }

        return result;
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
                float wordSpacing = PDFReal.truncate(remainingWidth / (float) numSpaces);
                float remainder = remainingWidth - (wordSpacing * numSpaces);
                float charSpacing = PDFReal.truncate(remainder / (float) numChars);
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

    private int hyphenate(String tmp, PDFFontWrapper wrapper, char[] chars, int boundary) {
        int result = boundary;
        Object enable = System.getProperties().get("EnableHyphenation");
        if (enable == null) {
            return result;
        }

        if (!tmp.matches(".*\\p{Punct}.*")) {
            Hyphenator hyphenator = new Hyphenator();
            hyphenator.setText(tmp);
            int hyphenBreak = hyphenator.last();
            while (hyphenBreak != Hyphenator.DONE) {
                String tmpValue = new String(chars, 0, boundary + hyphenBreak) + "-";
                float tmpValueWidth = wrapper.getStringWidth(tmpValue);
                if (tmpValueWidth < remainingWidth) {
                    result += hyphenBreak;
                    break;
                }
                hyphenBreak = hyphenator.previous();
            }
        }
        return result;
    }
}