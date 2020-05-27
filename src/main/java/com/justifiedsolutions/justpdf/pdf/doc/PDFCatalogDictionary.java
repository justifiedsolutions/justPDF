/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.doc;

import com.justifiedsolutions.justpdf.pdf.object.PDFDictionary;
import com.justifiedsolutions.justpdf.pdf.object.PDFName;

/**
 * Models the document catalog of a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.7.2"
 */
final class PDFCatalogDictionary extends PDFDictionary {
    public static final PDFName TYPE = new PDFName("Type");
    public static final PDFName VERSION = new PDFName("Version");
    public static final PDFName PAGES = new PDFName("Pages");

    static final PDFName CATALOG = new PDFName("Catalog");
    static final PDFName VERSION_17 = new PDFName("1.7");

    /**
     * Creates a new PDFCatalogDictionary setting the {@code Type} and {@code Version} in the dictionary.
     */
    public PDFCatalogDictionary() {
        put(TYPE, CATALOG);
        put(VERSION, VERSION_17);
    }
}
