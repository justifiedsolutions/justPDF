/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.doc;

import com.justifiedsolutions.jspdf.pdf.object.PDFDictionary;
import com.justifiedsolutions.jspdf.pdf.object.PDFName;

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
    public static final PDFName MOD_DATE = new PDFName("ModDate");
}
