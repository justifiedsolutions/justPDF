/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.object;

import java.util.HashMap;
import java.util.Map;

/**
 * An encoding that represents the PDF Document encoding.
 */
public class PDFDocEncoding extends Encoding {

    private static final Map<Character, Integer> PDF_DOC_ENCODING = new HashMap<>();

    static {
        PDF_DOC_ENCODING.put('\u2022', 128);
        PDF_DOC_ENCODING.put('\u2020', 129);
        PDF_DOC_ENCODING.put('\u2021', 130);
        PDF_DOC_ENCODING.put('\u2026', 131);
        PDF_DOC_ENCODING.put('\u2014', 132);
        PDF_DOC_ENCODING.put('\u2013', 133);
        PDF_DOC_ENCODING.put('\u0192', 134);
        PDF_DOC_ENCODING.put('\u2044', 135);
        PDF_DOC_ENCODING.put('\u2039', 136);
        PDF_DOC_ENCODING.put('\u203A', 137);
        PDF_DOC_ENCODING.put('\u2212', 138);
        PDF_DOC_ENCODING.put('\u2030', 139);
        PDF_DOC_ENCODING.put('\u201E', 140);
        PDF_DOC_ENCODING.put('\u201C', 141);
        PDF_DOC_ENCODING.put('\u201D', 142);
        PDF_DOC_ENCODING.put('\u2018', 143);
        PDF_DOC_ENCODING.put('\u2019', 144);
        PDF_DOC_ENCODING.put('\u201A', 145);
        PDF_DOC_ENCODING.put('\u2122', 146);
        PDF_DOC_ENCODING.put('\uFB01', 147);
        PDF_DOC_ENCODING.put('\uFB02', 148);
        PDF_DOC_ENCODING.put('\u0141', 149);
        PDF_DOC_ENCODING.put('\u0152', 150);
        PDF_DOC_ENCODING.put('\u0160', 151);
        PDF_DOC_ENCODING.put('\u0178', 152);
        PDF_DOC_ENCODING.put('\u017D', 153);
        PDF_DOC_ENCODING.put('\u0131', 154);
        PDF_DOC_ENCODING.put('\u0142', 155);
        PDF_DOC_ENCODING.put('\u0153', 156);
        PDF_DOC_ENCODING.put('\u0161', 157);
        PDF_DOC_ENCODING.put('\u017E', 158);
        PDF_DOC_ENCODING.put('\u20AC', 160);
    }

    @Override
    protected int getMinCharacter() {
        return 128;
    }

    @Override
    protected int getMaxCharacter() {
        return 160;
    }

    @Override
    protected Map<Character, Integer> getEncodingMap() {
        return PDF_DOC_ENCODING;
    }
}
