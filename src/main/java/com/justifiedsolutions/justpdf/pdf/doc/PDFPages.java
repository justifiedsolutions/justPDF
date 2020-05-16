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
class PDFPages extends PDFDictionary {

    static final PDFName TYPE = new PDFName("Type");
    static final PDFName PAGES = new PDFName("Pages");
    static final PDFName COUNT = new PDFName("Count");
    static final PDFName KIDS = new PDFName("Kids");

    final PDFArray kids = new PDFArray();

    /**
     * Creates a new PDFPages node.
     */
    PDFPages() {
        put(TYPE, PAGES);
        put(COUNT, new PDFInteger(kids.size()));
        put(KIDS, kids);
    }

    /**
     * Adds a reference to a PDFPage.
     *
     * @param reference the reference
     */
    void addPage(PDFIndirectObject.Reference reference) {
        kids.add(reference);
        put(COUNT, new PDFInteger(kids.size()));
    }
}
