/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This tests all 14 exception words plus words specified in Liang's Thesis.
 */
class HyphenatorTest {

    @Test
    void rugsSlashFurnishings() {
        String text = "Rugs/furnishings";
        int[] expected = { 8, 12 };
        testHyphenation(text, expected);
    }

    @Test
    void rugsSlashFurnishingsSlashSomething() {
        String text = "Rugs/furnishings/Something";
        int[] expected = { 8, 12, 21 };
        testHyphenation(text, expected);
    }

    @Test
    void checkingSlashSavings() {
        String text = "Checking/Savings";
        int[] expected = { 5, 12 };
        testHyphenation(text, expected);
    }

    @Test
    void test() {
        String text = "test";
        int[] expected = {};
        testHyphenation(text, expected);
    }

    @Test
    void whole() {
        String text = "whole";
        int[] expected = {};
        testHyphenation(text, expected);
    }

    @Test
    void squirmed() {
        String text = "squirmed";
        int[] expected = {};
        testHyphenation(text, expected);
    }

    @Test
    void analogous() {
        String text = "analogous";
        int[] expected = { 4, 5 };
        testHyphenation(text, expected);
    }

    @Test
    void at() {
        String text = "at";
        int[] expected = {};
        testHyphenation(text, expected);
    }

    @Test
    void computer() {
        String text = "computer";
        int[] expected = { 3, 6 };
        testHyphenation(text, expected);
    }

    @Test
    void algorithm() {
        String text = "algorithm";
        int[] expected = { 2, 4 };
        testHyphenation(text, expected);
    }

    @Test
    void hyphenation() {
        String text = "hyphenation";
        int[] expected = { 2, 6, 7 };
        testHyphenation(text, expected);
    }

    @Test
    void concatenation() {
        String text = "concatenation";
        int[] expected = { 3, 7, 9 };
        testHyphenation(text, expected);
    }

    @Test
    void mathematics() {
        String text = "mathematics";
        int[] expected = { 4, 5, 8 };
        testHyphenation(text, expected);
    }

    @Test
    void typesetting() {
        String text = "typesetting";
        int[] expected = { 4, 7 };
        testHyphenation(text, expected);
    }

    @Test
    void obligatory() {
        String text = "obligatory";
        int[] expected = { 5, 6 };
        testHyphenation(text, expected);
    }

    @Test
    void associate() {
        String text = "associate";
        int[] expected = { 2, 4 };
        testHyphenation(text, expected);
    }

    @Test
    void associates() {
        String text = "associates";
        int[] expected = { 2, 4 };
        testHyphenation(text, expected);
    }

    @Test
    void declination() {
        String text = "declination";
        int[] expected = { 3, 5, 7 };
        testHyphenation(text, expected);
    }

    @Test
    void philanthropic() {
        String text = "philanthropic";
        int[] expected = { 4, 6 };
        testHyphenation(text, expected);
    }

    @Test
    void present() {
        String text = "present";
        int[] expected = {};
        testHyphenation(text, expected);
    }

    @Test
    void presents() {
        String text = "presents";
        int[] expected = {};
        testHyphenation(text, expected);
    }

    @Test
    void project() {
        String text = "project";
        int[] expected = {};
        testHyphenation(text, expected);
    }

    @Test
    void projects() {
        String text = "projects";
        int[] expected = {};
        testHyphenation(text, expected);
    }

    @Test
    void reciprocity() {
        String text = "reciprocity";
        int[] expected = { 3, 4, 8, 9 };
        testHyphenation(text, expected);
    }

    @Test
    void recognizance() {
        String text = "recognizance";
        int[] expected = { 2, 5, 7 };
        testHyphenation(text, expected);
    }

    @Test
    void reformation() {
        String text = "reformation";
        int[] expected = { 3, 5, 7 };
        testHyphenation(text, expected);
    }

    @Test
    void retribution() {
        String text = "retribution";
        int[] expected = { 3, 5, 7 };
        testHyphenation(text, expected);
    }

    @Test
    void table() {
        String text = "table";
        int[] expected = { 2 };
        testHyphenation(text, expected);
    }

    private void testHyphenation(String input, int[] expected) {
        Hyphenator hyphenator = new Hyphenator();
        hyphenator.setText(input);

        assertArrayEquals(expected, hyphenator.all());

        int hyphen = hyphenator.first();
        for (int value : expected) {
            assertEquals(value, hyphen);
            hyphen = hyphenator.next();
        }
        assertEquals(Hyphenator.DONE, hyphenator.next());

        hyphen = hyphenator.last();
        for (int i = (expected.length - 1); i >= 0; i--) {
            assertEquals(expected[i], hyphen);
            hyphen = hyphenator.previous();
        }
        assertEquals(Hyphenator.DONE, hyphenator.previous());

        if (expected.length > 0) {
            assertEquals(expected[0], hyphenator.first());
            assertEquals(expected[expected.length - 1], hyphenator.last());
        } else {
            assertEquals(Hyphenator.DONE, hyphenator.first());
            assertEquals(Hyphenator.DONE, hyphenator.last());
        }

    }

}