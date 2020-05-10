/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.layout;

import com.justifiedsolutions.jspdf.api.HorizontalAlignment;
import com.justifiedsolutions.jspdf.api.content.Chunk;
import com.justifiedsolutions.jspdf.pdf.contents.*;
import com.justifiedsolutions.jspdf.pdf.object.PDFReal;
import com.justifiedsolutions.jspdf.pdf.object.PDFString;

import java.util.ArrayList;
import java.util.List;

/**
 * Lays out a line of text.
 */
class TextLine implements ContentLine {

    private final List<GraphicsOperator> operators = new ArrayList<>();

    private final float lineWidth;
    private float remainingWidth;
    private float lineStart = 0;
    private float previousLineStart = 0;

    private float leading = 0;
    private float lineHeight = 0;
    private HorizontalAlignment alignment = HorizontalAlignment.LEFT;
    private float leftIndent;

    private int numSpaces = 0;
    private int numChars = 0;

    /**
     * Creates a new TextLine of the specified width.
     *
     * @param lineWidth the width of the line
     */
    TextLine(float lineWidth) {
        this.lineWidth = lineWidth;
        this.remainingWidth = lineWidth;
    }

    /**
     * Creates a new TextLine of the specified width and indentations from the margin.
     *
     * @param lineWidth   the width of the line
     * @param leftIndent  the amount of left indent
     * @param rightIndent the amount of right indent
     */
    TextLine(float lineWidth, float leftIndent, float rightIndent) {
        this(lineWidth - (leftIndent + rightIndent));
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
     * Sets the lineHeight multiplier. This will be multiplied by the font height to determine the leading for the line.
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
     * Gets the start of this line as indentation from <code>0</code>.
     *
     * @return the start of the line
     */
    float getLineStart() {
        return lineStart;
    }

    /**
     * Gets the start of the previous line as indentation from <code>0</code>.
     *
     * @param previousLineStart the start of the previous line
     */
    void setPreviousLineStart(float previousLineStart) {
        this.previousLineStart = previousLineStart;
    }

    /**
     * Attempts to append the specified {@link Chunk} to the line. Any part of the Chunk that couldn't fit is returned.
     * If the entire Chunk could fit, then <code>null</code> is returned.
     *
     * @param chunk the Chunk to append
     * @return the remainder or null if it all fit
     */
    Chunk append(Chunk chunk) {
        if (remainingWidth == 0) {
            return chunk;
        }

        if (Chunk.LINE_BREAK.equals(chunk)) {
            remainingWidth = 0;
            return null;
        }

        if (chunk.getText().isEmpty()) {
            return null;
        }

        PDFFontWrapper wrapper = PDFFontWrapper.getInstance(chunk.getFont());
        setLeading(wrapper.getMinimumLeading());
        setLeading(lineHeight * wrapper.getSize().getValue());

        String chunkText;
        boolean firstWord = (lineWidth == remainingWidth);
        if (firstWord) {
            chunkText = chunk.getText().stripLeading();
        } else {
            chunkText = chunk.getText();
        }

        List<String> split = splitText(chunkText, wrapper);

        if (!split.get(0).isEmpty()) {
            operators.add(new FontWrapperOperator(wrapper));
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
        List<String> result = new ArrayList<>();
        char[] chars = input.toCharArray();

        boolean reachedEOL = false;
        int splitPoint = 0;
        int lastWhitespace = 0;
        int spaceCount = 0;
        float textWidthAtLastWhitespace = 0;
        float textWidth = 0;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            if (Character.isWhitespace(c)) {
                lastWhitespace = i;
                textWidthAtLastWhitespace = textWidth;
                if (c == 32) {
                    spaceCount++;
                }
            }

            float charWidth = wrapper.getCharacterWidth(c);
            if ((textWidth + charWidth) < remainingWidth) {
                textWidth += charWidth;
            } else {
                splitPoint = lastWhitespace;
                textWidth = textWidthAtLastWhitespace;
                reachedEOL = true;
                break;
            }
        }

        numSpaces += (spaceCount - 1);
        numChars += splitPoint;
        remainingWidth -= textWidth;
        calculateLineStart();
        if (!reachedEOL) {
            result.add(input);
        } else {
            result.add(new String(chars, 0, splitPoint));
            result.add(new String(chars, splitPoint, (chars.length - splitPoint)));
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
        List<GraphicsOperator> operators = new ArrayList<>();
        float start = (lineStart - previousLineStart);
        if (start != 0) {
            operators.add(new PositionText(new PDFReal(start), new PDFReal(0)));
        }
        if (HorizontalAlignment.JUSTIFIED == alignment) {
            if ((lineWidth * .25f) < remainingWidth) {
                operators.add(new SetWordSpacing(new PDFReal(0)));
                operators.add(new SetCharacterSpacing(new PDFReal(0)));
            } else {
                float wordSpacing = remainingWidth / (float) numSpaces;
                operators.add(new SetWordSpacing(new PDFReal(wordSpacing)));
                float remainder = remainingWidth - (wordSpacing * numSpaces);
                float charSpacing = remainder / (float) numChars;
                operators.add(new SetCharacterSpacing(new PDFReal(charSpacing)));
            }
        }
        return operators;
    }

}
