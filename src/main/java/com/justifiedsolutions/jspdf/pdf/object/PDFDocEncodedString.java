/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.object;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Models a PDFString that utilizes PDF Doc Encoding in a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.3.4"
 * @see "ISO 32000-1:2008, 7.9.2"
 * @see "ISO 32000-1:2008, D.3"
 */
public class PDFDocEncodedString extends PDFString {
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


    /**
     * Creates a new PDFDocEncodedString representing the specified value. These strings are not allowed in content
     * streams. Instead they are used for things like dictionary values outside of the content.
     *
     * @param value the String value
     */
    public PDFDocEncodedString(String value) {
        super(value);
    }

    private static byte[] encodeString(String text) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();

        char[] textChars = text.toCharArray();
        for (char character : textChars) {
            if (character < 128 || (character > 160 && character < 256)) {
                result.write(character);
            } else {
                Integer charCode = PDF_DOC_ENCODING.get(character);
                if (charCode != null) {
                    result.write(charCode);
                }
            }
        }
        return result.toByteArray();
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        String escapedValue = escapeValue();
        byte[] encodedValue = encodeString(escapedValue);
        pdf.write('(');
        pdf.write(encodedValue);
        pdf.write(')');
    }
}