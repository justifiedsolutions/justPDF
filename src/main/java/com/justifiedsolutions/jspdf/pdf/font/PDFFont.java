/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.font;

import com.justifiedsolutions.jspdf.pdf.object.PDFDictionary;
import com.justifiedsolutions.jspdf.pdf.object.PDFName;

/**
 * Models a Simple Font in a PDF document.
 *
 * @see "ISO 32000-1:2008, 9.6"
 */
public abstract class PDFFont extends PDFDictionary {
    static final PDFName TYPE = new PDFName("Type");
    static final PDFName FONT = new PDFName("Font");
    static final PDFName SUBTYPE = new PDFName("Subtype");
    static final PDFName BASE_FONT = new PDFName("BaseFont");

    PDFFont() {
        put(TYPE, FONT);
    }
}
