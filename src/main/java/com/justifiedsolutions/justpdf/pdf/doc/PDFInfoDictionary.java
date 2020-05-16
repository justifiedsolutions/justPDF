/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.doc;

import com.justifiedsolutions.justpdf.pdf.object.PDFDictionary;
import com.justifiedsolutions.justpdf.pdf.object.PDFDocEncodedString;
import com.justifiedsolutions.justpdf.pdf.object.PDFName;
import com.justifiedsolutions.justpdf.pdf.object.PDFObject;

/**
 * Models the Document Information Dictionary in a PDF document.
 *
 * @see "ISO 32000-1:2008, 14.3.3"
 */
public class PDFInfoDictionary extends PDFDictionary {
    public static final PDFName TITLE = new PDFName("Title");
    public static final PDFName AUTHOR = new PDFName("Author");
    public static final PDFName SUBJECT = new PDFName("Subject");
    public static final PDFName KEYWORDS = new PDFName("Keywords");
    public static final PDFName CREATOR = new PDFName("Creator");
    public static final PDFName PRODUCER = new PDFName("Producer");
    public static final PDFName CREATION_DATE = new PDFName("CreationDate");

    /**
     * Associates the specified value to the specified key. If the key already exists in the dictionary, the value will
     * replace the existing value.
     *
     * @param key   the key
     * @param value the new value
     * @throws IllegalArgumentException if the value is not a PDFDocEncodedString
     */
    @Override
    public void put(PDFName key, PDFObject value) {
        if (value instanceof PDFDocEncodedString) {
            super.put(key, value);
        } else {
            throw new IllegalArgumentException("value must be a PDFDocEncodedString.");
        }
    }
}
