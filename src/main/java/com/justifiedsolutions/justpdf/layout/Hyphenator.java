/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import java.util.Arrays;
import java.util.Objects;

/**
 * Class that takes a word and finds the location of all possible hyphenation points in that word. The algorithm is
 * based on the Knuth-Liang algorithm.
 */
class Hyphenator {
    static final int DONE = Integer.MIN_VALUE;

    private static final HyphenProcessor PROCESSOR = new HyphenProcessor();

    private int[] hyphens = {};
    private int index = DONE;


    /**
     * Set a new text string to be scanned. The current scan position is reset to first().
     *
     * @param text new text to scan
     * @throws NullPointerException if text is {@code null}
     */
    void setText(String text) {
        Objects.requireNonNull(text);
        hyphens = PROCESSOR.hyphenate(text);
        first();
    }

    /**
     * Returns the first hyphen. The iterator's current position is set to the first hyphen.
     *
     * @return The character index of the first hyphen
     */
    int first() {
        if (hyphens.length > 0) {
            index = 0;
            return hyphens[index];
        }
        index = DONE;
        return DONE;
    }

    /**
     * Returns the last hyphen. The iterator's current position is set to the last hyphen.
     *
     * @return The character index of the last hyphen
     */
    int last() {
        if (hyphens.length > 0) {
            index = (hyphens.length - 1);
            return hyphens[index];
        }
        index = DONE;
        return DONE;
    }

    /**
     * Returns the hyphen following the current hyphen. If the current hyphen is the last hyphen, it returns
     * Hyphenator.DONE and the iterator's current position is unchanged. Otherwise, the iterator's current position is
     * set to the hyphen following the current hyphen.
     *
     * @return The character index of the next hyphen or {@code Hyphenator.DONE} if the current boundary is the last
     * text boundary.
     */
    int next() {
        if (index == (hyphens.length - 1) || index == DONE) {
            return DONE;
        }

        index++;
        if (index < hyphens.length) {
            return hyphens[index];
        }
        return DONE;
    }

    /**
     * Returns the hyphen preceding the current hyphen. If the current hyphen is the first hyphen, it returns
     * Hyphenator.DONE and the iterator's current position is unchanged. Otherwise, the iterator's current position is
     * set to the hyphen preceding the current hyphen.
     *
     * @return The character index of the previous hyphen or Hyphenator.DONE if the current hyphen is the first hyphen.
     */
    int previous() {
        if (index == 0 || index == DONE) {
            return DONE;
        }

        index--;
        if (index >= 0) {
            return hyphens[index];
        }
        return DONE;
    }

    /**
     * Returns an array of the indexes of all of the hyphens.
     *
     * @return the indexes of all of the hyphens
     */
    int[] all() {
        return Arrays.copyOf(hyphens, hyphens.length);
    }

}
