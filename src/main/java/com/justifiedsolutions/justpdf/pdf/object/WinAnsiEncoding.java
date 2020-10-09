/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.object;

import java.util.HashMap;
import java.util.Map;

/**
 * An encoding that represents the Windows ANSI or CP1252 encoding.
 */
public class WinAnsiEncoding extends Encoding {
    private static final Map<Character, Integer> WINANSI_ENCODING = new HashMap<>();

    static {
        WINANSI_ENCODING.put('\u20AC', 128);
        WINANSI_ENCODING.put('\u201A', 130);
        WINANSI_ENCODING.put('\u0192', 131);
        WINANSI_ENCODING.put('\u201E', 132);
        WINANSI_ENCODING.put('\u2026', 133);
        WINANSI_ENCODING.put('\u2020', 134);
        WINANSI_ENCODING.put('\u2021', 135);
        WINANSI_ENCODING.put('\u02C6', 136);
        WINANSI_ENCODING.put('\u2030', 137);
        WINANSI_ENCODING.put('\u0160', 138);
        WINANSI_ENCODING.put('\u2039', 139);
        WINANSI_ENCODING.put('\u0152', 140);
        WINANSI_ENCODING.put('\u017D', 142);
        WINANSI_ENCODING.put('\u2018', 145);
        WINANSI_ENCODING.put('\u2019', 146);
        WINANSI_ENCODING.put('\u201C', 147);
        WINANSI_ENCODING.put('\u201D', 148);
        WINANSI_ENCODING.put('\u2022', 149);
        WINANSI_ENCODING.put('\u2013', 150);
        WINANSI_ENCODING.put('\u2014', 151);
        WINANSI_ENCODING.put('\u02DC', 152);
        WINANSI_ENCODING.put('\u2122', 153);
        WINANSI_ENCODING.put('\u0161', 154);
        WINANSI_ENCODING.put('\u203A', 155);
        WINANSI_ENCODING.put('\u0153', 156);
        WINANSI_ENCODING.put('\u017E', 158);
        WINANSI_ENCODING.put('\u0178', 159);
    }

    @Override
    protected int getMinCharacter() {
        return 128;
    }

    @Override
    protected int getMaxCharacter() {
        return 159;
    }

    @Override
    protected Map<Character, Integer> getEncodingMap() {
        return WINANSI_ENCODING;
    }
}
