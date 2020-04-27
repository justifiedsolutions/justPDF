/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.doc;

import com.justifiedsolutions.jspdf.pdf.object.PDFDictionary;
import com.justifiedsolutions.jspdf.pdf.object.PDFName;

/**
 * Models the document catalog of a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.7.2"
 */
public class PDFCatalogDictionary extends PDFDictionary {
    public static final PDFName TYPE = new PDFName("Type");
    public static final PDFName VERSION = new PDFName("Version");
    public static final PDFName PAGES = new PDFName("Pages");

    public PDFCatalogDictionary() {
        put(TYPE, new PDFName("Catalog"));
        put(VERSION, new PDFName("1.7"));
    }
}
