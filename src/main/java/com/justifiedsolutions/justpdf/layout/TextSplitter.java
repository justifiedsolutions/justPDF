/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import java.text.BreakIterator;

/**
 * Splits text to the maximum string possible based on the specified width.
 */
class TextSplitter {
    private final float lineWidth;

    /**
     * Creates a new TextSplitter.
     *
     * @param lineWidth the width that the text must fit inside
     */
    TextSplitter(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    /**
     * Splits the specified string and returns the longest substring that will fit on the line.
     *
     * @param input         the input to split
     * @param wrapper       the font for the text
     * @param doHyphenation true if the input should be hyphenated if it doesn't fit on the line
     * @return the longest substring
     */
    String split(String input, PDFFontWrapper wrapper, boolean doHyphenation) {
        BreakIterator breakDetector = BreakIterator.getLineInstance();
        breakDetector.setText(input);

        boolean hyphenate = false;
        char[] chars = input.toCharArray();
        int boundary = breakDetector.next();
        while (boundary != BreakIterator.DONE) {
            String value = new String(chars, 0, boundary);
            float valueWidth = wrapper.getStringWidth(value);
            if (valueWidth > lineWidth) {
                int end = boundary;
                boundary = breakDetector.previous();
                if (doHyphenation) {
                    String tmp = new String(chars, boundary, (end - boundary)).stripTrailing();
                    int hyphenBoundary = hyphenate(tmp, wrapper, chars, boundary);
                    if (hyphenBoundary > boundary) {
                        boundary = hyphenBoundary;
                        hyphenate = true;
                    }
                }
                break;
            }
            boundary = breakDetector.next();
        }

        String value = input;
        if (boundary != BreakIterator.DONE) {
            value = new String(chars, 0, boundary);
            value = value.stripTrailing();
            if (hyphenate) {
                value += "-";
            }
        }
        return value;
    }

    private int hyphenate(String tmp, PDFFontWrapper wrapper, char[] chars, int boundary) {
        int result = boundary;
        Hyphenator hyphenator = new Hyphenator();
        hyphenator.setText(tmp);
        int hyphenBreak = hyphenator.last();
        while (hyphenBreak != Hyphenator.DONE) {
            String tmpValue = new String(chars, 0, boundary + hyphenBreak) + "-";
            float tmpValueWidth = wrapper.getStringWidth(tmpValue);
            if (tmpValueWidth < lineWidth) {
                result += hyphenBreak;
                break;
            }
            hyphenBreak = hyphenator.previous();
        }
        return result;
    }

}
