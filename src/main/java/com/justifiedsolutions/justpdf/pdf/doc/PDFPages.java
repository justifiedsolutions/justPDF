/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.doc;

import com.justifiedsolutions.justpdf.pdf.object.*;

/**
 * Models a Page Tree Node in a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.7.3.2"
 */
final class PDFPages extends PDFDictionary {

    static final PDFName TYPE_NAME = new PDFName("Type");
    static final PDFName PAGES_NAME = new PDFName("Pages");
    static final PDFName COUNT_NAME = new PDFName("Count");
    static final PDFName KIDS_NAME = new PDFName("Kids");

    private final PDFArray kids = new PDFArray();

    /**
     * Creates a new PDFPages node.
     */
    PDFPages() {
        put(TYPE_NAME, PAGES_NAME);
        put(COUNT_NAME, new PDFInteger(kids.size()));
        put(KIDS_NAME, kids);
    }

    /**
     * Adds a reference to a PDFPage.
     *
     * @param reference the reference
     */
    void addPage(PDFIndirectObject.Reference reference) {
        kids.add(reference);
        put(COUNT_NAME, new PDFInteger(kids.size()));
    }
}
