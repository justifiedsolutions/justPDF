/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.object;

/**
 * Models a PDFString that utilizes PDF Doc Encoding in a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.3.4"
 * @see "ISO 32000-1:2008, 7.9.2"
 * @see "ISO 32000-1:2008, D.3"
 */
public class PDFDocEncodedString extends PDFString {

    private static final Encoding PDF_DOC_ENCODING = new PDFDocEncoding();

    /**
     * Creates a new PDFDocEncodedString representing the specified value. These strings are not allowed in content
     * streams. Instead they are used for things like dictionary values outside of the content.
     *
     * @param value the String value
     */
    public PDFDocEncodedString(String value) {
        super(value);
    }

    @Override
    protected byte[] encode() {
        return PDF_DOC_ENCODING.encodeString(getValue());
    }
}