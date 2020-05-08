/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.layout;

import com.justifiedsolutions.jspdf.api.content.Chunk;
import com.justifiedsolutions.jspdf.pdf.contents.GraphicsOperator;
import com.justifiedsolutions.jspdf.pdf.contents.MoveToNextLine;
import com.justifiedsolutions.jspdf.pdf.contents.SetLeading;
import com.justifiedsolutions.jspdf.pdf.contents.ShowText;
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

    private float leading = 0;

    /**
     * Creates a new TextLine of the specified width
     *
     * @param lineWidth the width of the line
     */
    TextLine(float lineWidth) {
        this.lineWidth = lineWidth;
        this.remainingWidth = lineWidth;
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
        float textWidth = 0;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];

            if (Character.isWhitespace(c)) {
                lastWhitespace = i;
            }

            float charWidth = wrapper.getCharacterWidth(c);
            if ((textWidth + charWidth) < remainingWidth) {
                textWidth += charWidth;
            } else {
                splitPoint = lastWhitespace;
                reachedEOL = true;
                break;
            }
        }

        if (!reachedEOL) {
            remainingWidth += textWidth;
            result.add(input);
        } else {
            result.add(new String(chars, 0, splitPoint));
            result.add(new String(chars, splitPoint, (chars.length - splitPoint)));
        }
        return result;
    }
}
